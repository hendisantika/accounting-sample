package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.customer.CustomerRequest;
import id.my.hendisantika.accountingsample.dto.customer.CustomerResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Email: hendisantika@yahoo.co.id
 * Date: 17/10/25
 * Time: 11.00
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CustomerService Tests")
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private CustomerService customerService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Customer customer1;
    private Customer customer2;
    private CustomerRequest customerRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);

        organization = Organization.builder()
                .name("Test Org")
                .email("org@example.com")
                .country("US")
                .isActive(true)
                .build();
        organization.setId(1L);

        customer1 = Customer.builder()
                .organization(organization)
                .name("John Doe")
                .companyName("Acme Corp")
                .email("john@acme.com")
                .phone("+1234567890")
                .mobile("+1234567891")
                .isActive(true)
                .outstandingBalance(BigDecimal.ZERO)
                .paymentTerms(30)
                .creditLimit(BigDecimal.valueOf(10000))
                .build();
        customer1.setId(1L);

        customer2 = Customer.builder()
                .organization(organization)
                .name("Jane Smith")
                .companyName("Tech Inc")
                .email("jane@tech.com")
                .phone("+0987654321")
                .isActive(false)
                .outstandingBalance(BigDecimal.valueOf(500))
                .build();
        customer2.setId(2L);

        customerRequest = new CustomerRequest();
        customerRequest.setName("New Customer");
        customerRequest.setCompanyName("New Company");
        customerRequest.setEmail("new@company.com");
        customerRequest.setPhone("+1111111111");
        customerRequest.setIsActive(true);
        customerRequest.setPaymentTerms(30);
        customerRequest.setCreditLimit(BigDecimal.valueOf(5000));

        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should get all customers for organization")
    void getAllCustomers_Success() {
        // Given
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findByOrganizationId(1L)).thenReturn(customers);

        // When
        List<CustomerResponse> responses = customerService.getAllCustomers();

        // Then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("John Doe");
        assertThat(responses.get(1).getName()).isEqualTo("Jane Smith");

        verify(customerRepository).findByOrganizationId(1L);
    }

    @Test
    @DisplayName("Should get only active customers")
    void getActiveCustomers_Success() {
        // Given
        List<Customer> activeCustomers = Collections.singletonList(customer1);
        when(customerRepository.findByIsActiveAndOrganizationId(true, 1L)).thenReturn(activeCustomers);

        // When
        List<CustomerResponse> responses = customerService.getActiveCustomers();

        // Then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo("John Doe");
        assertThat(responses.get(0).getIsActive()).isTrue();

        verify(customerRepository).findByIsActiveAndOrganizationId(true, 1L);
    }

    @Test
    @DisplayName("Should search customers by name")
    void searchCustomers_Success() {
        // Given
        List<Customer> customers = Collections.singletonList(customer1);
        when(customerRepository.findByNameContainingIgnoreCaseAndOrganizationId("john", 1L))
                .thenReturn(customers);

        // When
        List<CustomerResponse> responses = customerService.searchCustomers("john");

        // Then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getName()).isEqualTo("John Doe");

        verify(customerRepository).findByNameContainingIgnoreCaseAndOrganizationId("john", 1L);
    }

    @Test
    @DisplayName("Should get customer by ID")
    void getCustomerById_Success() {
        // Given
        when(customerRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(customer1));

        // When
        CustomerResponse response = customerService.getCustomerById(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("John Doe");
        assertThat(response.getCompanyName()).isEqualTo("Acme Corp");
        assertThat(response.getEmail()).isEqualTo("john@acme.com");

        verify(customerRepository).findByIdAndOrganizationId(1L, 1L);
    }

    @Test
    @DisplayName("Should throw exception when customer not found by ID")
    void getCustomerById_NotFound_ThrowsException() {
        // Given
        when(customerRepository.findByIdAndOrganizationId(999L, 1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> customerService.getCustomerById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found");

        verify(customerRepository).findByIdAndOrganizationId(999L, 1L);
    }

    @Test
    @DisplayName("Should create new customer")
    void createCustomer_Success() {
        // Given
        when(customerRepository.existsByEmailAndOrganizationId(anyString(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        // When
        CustomerResponse response = customerService.createCustomer(customerRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("John Doe");

        verify(customerRepository).existsByEmailAndOrganizationId("new@company.com", 1L);
        verify(organizationRepository).findById(1L);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists during creation")
    void createCustomer_EmailExists_ThrowsException() {
        // Given
        when(customerRepository.existsByEmailAndOrganizationId(anyString(), any())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> customerService.createCustomer(customerRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Customer with this email already exists");

        verify(customerRepository).existsByEmailAndOrganizationId("new@company.com", 1L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should create customer with null email")
    void createCustomer_NullEmail_Success() {
        // Given
        customerRequest.setEmail(null);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        // When
        CustomerResponse response = customerService.createCustomer(customerRequest);

        // Then
        assertThat(response).isNotNull();
        verify(customerRepository, never()).existsByEmailAndOrganizationId(anyString(), any());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw exception when organization not found during customer creation")
    void createCustomer_OrganizationNotFound_ThrowsException() {
        // Given
        when(customerRepository.existsByEmailAndOrganizationId(anyString(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> customerService.createCustomer(customerRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Organization not found");

        verify(organizationRepository).findById(1L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update customer")
    void updateCustomer_Success() {
        // Given
        customerRequest.setName("Updated Name");
        customerRequest.setEmail("different@email.com");
        when(customerRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.existsByEmailAndOrganizationId(anyString(), any())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        // When
        CustomerResponse response = customerService.updateCustomer(1L, customerRequest);

        // Then
        assertThat(response).isNotNull();
        verify(customerRepository).findByIdAndOrganizationId(1L, 1L);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should throw exception when updating to existing email")
    void updateCustomer_EmailExists_ThrowsException() {
        // Given
        customerRequest.setEmail("different@email.com");
        when(customerRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.existsByEmailAndOrganizationId("different@email.com", 1L)).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> customerService.updateCustomer(1L, customerRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Customer with this email already exists");

        verify(customerRepository).findByIdAndOrganizationId(1L, 1L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should allow updating customer with same email")
    void updateCustomer_SameEmail_Success() {
        // Given
        customerRequest.setEmail("john@acme.com");
        customerRequest.setName("Updated Name");
        when(customerRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        // When
        CustomerResponse response = customerService.updateCustomer(1L, customerRequest);

        // Then
        assertThat(response).isNotNull();
        verify(customerRepository).findByIdAndOrganizationId(1L, 1L);
        verify(customerRepository, never()).existsByEmailAndOrganizationId(anyString(), any());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    @DisplayName("Should delete customer with zero balance")
    void deleteCustomer_Success() {
        // Given
        when(customerRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(customer1));

        // When
        customerService.deleteCustomer(1L);

        // Then
        verify(customerRepository).findByIdAndOrganizationId(1L, 1L);
        verify(customerRepository).delete(customer1);
    }

    @Test
    @DisplayName("Should throw exception when deleting customer with outstanding balance")
    void deleteCustomer_OutstandingBalance_ThrowsException() {
        // Given
        when(customerRepository.findByIdAndOrganizationId(2L, 1L)).thenReturn(Optional.of(customer2));

        // When & Then
        assertThatThrownBy(() -> customerService.deleteCustomer(2L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot delete customer with outstanding balance");

        verify(customerRepository).findByIdAndOrganizationId(2L, 1L);
        verify(customerRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent customer")
    void deleteCustomer_NotFound_ThrowsException() {
        // Given
        when(customerRepository.findByIdAndOrganizationId(999L, 1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> customerService.deleteCustomer(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found");

        verify(customerRepository).findByIdAndOrganizationId(999L, 1L);
        verify(customerRepository, never()).delete(any());
    }
}
