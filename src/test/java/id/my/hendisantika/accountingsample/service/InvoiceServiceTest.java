package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.invoice.InvoiceItemRequest;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceRequest;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Invoice;
import id.my.hendisantika.accountingsample.model.Item;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.enums.InvoiceStatus;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.InvoiceItemRepository;
import id.my.hendisantika.accountingsample.repository.InvoiceRepository;
import id.my.hendisantika.accountingsample.repository.ItemRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Email: hendisantika@yahoo.co.id
 * Date: 17/10/25
 * Time: 12.00
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("InvoiceService Tests")
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private InvoiceItemRepository invoiceItemRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Customer customer;
    private Item item;
    private Invoice invoice;
    private InvoiceRequest invoiceRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Org")
                .build();
        organization.setId(1L);

        customer = Customer.builder()
                .name("Test Customer")
                .organization(organization)
                .build();
        customer.setId(1L);

        item = Item.builder()
                .name("Test Item")
                .description("Item Desc")
                .organization(organization)
                .build();
        item.setId(1L);

        invoice = Invoice.builder()
                .organization(organization)
                .customer(customer)
                .invoiceNumber("INV-001")
                .invoiceDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(30))
                .status(InvoiceStatus.DRAFT)
                .items(new ArrayList<>())
                .subtotal(BigDecimal.valueOf(100))
                .taxAmount(BigDecimal.valueOf(10))
                .totalAmount(BigDecimal.valueOf(110))
                .paidAmount(BigDecimal.ZERO)
                .balance(BigDecimal.valueOf(110))
                .build();
        invoice.setId(1L);

        invoiceRequest = new InvoiceRequest();
        invoiceRequest.setCustomerId(1L);
        invoiceRequest.setInvoiceNumber("INV-002");
        invoiceRequest.setInvoiceDate(LocalDate.now());
        invoiceRequest.setDueDate(LocalDate.now().plusDays(30));
        invoiceRequest.setStatus(InvoiceStatus.DRAFT);

        InvoiceItemRequest itemRequest = new InvoiceItemRequest();
        itemRequest.setItemId(1L);
        itemRequest.setQuantity(BigDecimal.valueOf(2));
        itemRequest.setUnitPrice(BigDecimal.valueOf(50));
        itemRequest.setLineOrder(1);
        invoiceRequest.setItems(List.of(itemRequest));
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should create invoice successfully")
    void createInvoice_Success() {
        // Given
        when(invoiceRepository.existsByInvoiceNumberAndOrganizationId(anyString(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(customerRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(customer));
        when(itemRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(item));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        // When
        InvoiceResponse response = invoiceService.createInvoice(invoiceRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getInvoiceNumber()).isEqualTo("INV-001");
        verify(invoiceRepository).save(any(Invoice.class));
    }

    @Test
    @DisplayName("Should throw exception when invoice number already exists")
    void createInvoice_DuplicateNumber_ThrowsException() {
        // Given
        when(invoiceRepository.existsByInvoiceNumberAndOrganizationId(anyString(), any())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> invoiceService.createInvoice(invoiceRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Invoice number already exists");
    }

    @Test
    @DisplayName("Should get all invoices")
    void getAllInvoices_Success() {
        // Given
        when(invoiceRepository.findByOrganizationId(1L)).thenReturn(Collections.singletonList(invoice));

        // When
        List<InvoiceResponse> responses = invoiceService.getAllInvoices();

        // Then
        assertThat(responses).hasSize(1);
        verify(invoiceRepository).findByOrganizationId(1L);
    }

    @Test
    @DisplayName("Should get invoice by ID")
    void getInvoiceById_Success() {
        // Given
        when(invoiceRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(invoice));

        // When
        InvoiceResponse response = invoiceService.getInvoiceById(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-draft invoice")
    void deleteInvoice_NonDraft_ThrowsException() {
        // Given
        invoice.setStatus(InvoiceStatus.SENT);
        when(invoiceRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(invoice));

        // When & Then
        assertThatThrownBy(() -> invoiceService.deleteInvoice(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Can only delete draft or cancelled invoices");
    }

    @Test
    @DisplayName("Should update invoice status successfully")
    void updateInvoiceStatus_Success() {
        // Given
        when(invoiceRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(invoice));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        // When
        InvoiceResponse response = invoiceService.updateInvoiceStatus(1L, InvoiceStatus.SENT);

        // Then
        assertThat(response).isNotNull();
        verify(invoiceRepository).save(any(Invoice.class));
    }
}
