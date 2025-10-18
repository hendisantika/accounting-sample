package id.my.hendisantika.accountingsample.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.accountingsample.dto.customer.CustomerRequest;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Customer Controller Integration Tests")
class CustomerControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private Organization testOrganization;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();

        // Ensure organization exists or create one
        testOrganization = organizationRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    Organization org = new Organization();
                    org.setName("Test Organization");
                    org.setEmail("test@example.com");
                    org.setPhone("1234567890");
                    org.setCurrencyCode("USD");
                    org.setTimeZone("UTC");
                    org.setFiscalYearStartMonth(1);
                    return organizationRepository.save(org);
                });
    }

    @Test
    @DisplayName("Should get all customers")
    void shouldGetAllCustomers() throws Exception {
        // Given
        createTestCustomer("John Doe", "john@example.com");
        createTestCustomer("Jane Smith", "jane@example.com");

        // When & Then
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Customers retrieved")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].name", notNullValue()))
                .andExpect(jsonPath("$.data[1].name", notNullValue()));
    }

    @Test
    @DisplayName("Should get active customers only")
    void shouldGetActiveCustomers() throws Exception {
        // Given
        Customer activeCustomer = createTestCustomer("Active Customer", "active@example.com");
        activeCustomer.setIsActive(true);
        customerRepository.save(activeCustomer);

        Customer inactiveCustomer = createTestCustomer("Inactive Customer", "inactive@example.com");
        inactiveCustomer.setIsActive(false);
        customerRepository.save(inactiveCustomer);

        // When & Then
        mockMvc.perform(get("/api/customers/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].name", is("Active Customer")));
    }

    @Test
    @DisplayName("Should search customers by name")
    void shouldSearchCustomersByName() throws Exception {
        // Given
        createTestCustomer("John Doe", "john@example.com");
        createTestCustomer("Jane Doe", "jane@example.com");
        createTestCustomer("Bob Smith", "bob@example.com");

        // When & Then
        mockMvc.perform(get("/api/customers/search")
                        .param("name", "Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get customer by ID")
    void shouldGetCustomerById() throws Exception {
        // Given
        Customer customer = createTestCustomer("Test Customer", "test@example.com");

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(customer.getId().intValue())))
                .andExpect(jsonPath("$.data.name", is("Test Customer")))
                .andExpect(jsonPath("$.data.email", is("test@example.com")));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should create new customer")
    void shouldCreateCustomer() throws Exception {
        // Given
        CustomerRequest request = new CustomerRequest();
        request.setName("New Customer");
        request.setCompanyName("New Company");
        request.setEmail("newcustomer@example.com");
        request.setPhone("1234567890");
        request.setMobile("0987654321");
        request.setWebsite("https://example.com");
        request.setTaxNumber("TAX123456");
        request.setPaymentTerms(30);
        request.setCreditLimit(new BigDecimal("10000.00"));
        request.setBillingAddressLine1("123 Main St");
        request.setBillingCity("New York");
        request.setBillingState("NY");
        request.setBillingPostalCode("10001");
        request.setBillingCountry("USA");
        request.setNotes("Test notes");
        request.setIsActive(true);

        // When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Customer created")))
                .andExpect(jsonPath("$.data.name", is("New Customer")))
                .andExpect(jsonPath("$.data.companyName", is("New Company")))
                .andExpect(jsonPath("$.data.email", is("newcustomer@example.com")))
                .andExpect(jsonPath("$.data.phone", is("1234567890")))
                .andExpect(jsonPath("$.data.id", notNullValue()));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should fail to create customer with invalid email")
    void shouldFailToCreateCustomerWithInvalidEmail() throws Exception {
        // Given
        CustomerRequest request = new CustomerRequest();
        request.setName("New Customer");
        request.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should fail to create customer without name")
    void shouldFailToCreateCustomerWithoutName() throws Exception {
        // Given
        CustomerRequest request = new CustomerRequest();
        request.setEmail("test@example.com");

        // When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ACCOUNTANT")
    @DisplayName("Should update customer")
    void shouldUpdateCustomer() throws Exception {
        // Given
        Customer existingCustomer = createTestCustomer("Old Name", "old@example.com");

        CustomerRequest updateRequest = new CustomerRequest();
        updateRequest.setName("Updated Name");
        updateRequest.setEmail("updated@example.com");
        updateRequest.setPhone("9999999999");
        updateRequest.setIsActive(true);

        // When & Then
        mockMvc.perform(put("/api/customers/{id}", existingCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Customer updated")))
                .andExpect(jsonPath("$.data.name", is("Updated Name")))
                .andExpect(jsonPath("$.data.email", is("updated@example.com")))
                .andExpect(jsonPath("$.data.phone", is("9999999999")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should delete customer")
    void shouldDeleteCustomer() throws Exception {
        // Given
        Customer customer = createTestCustomer("To Delete", "delete@example.com");
        Long customerId = customer.getId();

        // When & Then
        mockMvc.perform(delete("/api/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Customer deleted")));

        // Verify deletion
        mockMvc.perform(get("/api/customers/{id}", customerId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when customer not found")
    void shouldReturn404WhenCustomerNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/customers/{id}", 99999L))
                .andExpect(status().isNotFound());
    }

    // Helper method
    private Customer createTestCustomer(String name, String email) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone("1234567890");
        customer.setIsActive(true);
        customer.setOrganization(testOrganization);
        return customerRepository.save(customer);
    }
}
