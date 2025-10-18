package id.my.hendisantika.accountingsample.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceItemRequest;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceRequest;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Invoice;
import id.my.hendisantika.accountingsample.model.Item;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.enums.AccountType;
import id.my.hendisantika.accountingsample.model.enums.InvoiceStatus;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.InvoiceRepository;
import id.my.hendisantika.accountingsample.repository.ItemRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Invoice Controller Integration Tests")
class InvoiceControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Organization testOrganization;
    private Customer testCustomer;
    private Item testItem;
    private Account testRevenueAccount;

    @BeforeEach
    void setUp() {
        invoiceRepository.deleteAll();
        customerRepository.deleteAll();
        itemRepository.deleteAll();

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

        // Create test customer
        testCustomer = new Customer();
        testCustomer.setName("Test Customer");
        testCustomer.setEmail("customer@example.com");
        testCustomer.setPhone("1234567890");
        testCustomer.setIsActive(true);
        testCustomer.setOrganization(testOrganization);
        testCustomer = customerRepository.save(testCustomer);

        // Ensure revenue account exists
        testRevenueAccount = accountRepository.findByAccountTypeAndOrganizationId(AccountType.REVENUE, testOrganization.getId())
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Account account = new Account();
                    account.setCode("4000");
                    account.setName("Sales Revenue");
                    account.setAccountType(AccountType.REVENUE);
                    account.setOrganization(testOrganization);
                    account.setIsActive(true);
                    return accountRepository.save(account);
                });

        // Create test item
        testItem = new Item();
        testItem.setName("Test Product");
        testItem.setCode("TEST-001");
        testItem.setSku("TEST-001");
        testItem.setItemType(id.my.hendisantika.accountingsample.model.enums.ItemType.PRODUCT);
        testItem.setDescription("Test product description");
        testItem.setSalePrice(new BigDecimal("100.00"));
        testItem.setSalesAccount(testRevenueAccount);
        testItem.setOrganization(testOrganization);
        testItem.setIsActive(true);
        testItem = itemRepository.save(testItem);
    }

    @Test
    @DisplayName("Should get all invoices")
    void shouldGetAllInvoices() throws Exception {
        // Given
        createTestInvoice(InvoiceStatus.DRAFT);
        createTestInvoice(InvoiceStatus.SENT);

        // When & Then
        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Invoices retrieved")))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get invoices by customer")
    void shouldGetInvoicesByCustomer() throws Exception {
        // Given
        createTestInvoice(InvoiceStatus.DRAFT);
        createTestInvoice(InvoiceStatus.SENT);

        // When & Then
        mockMvc.perform(get("/api/invoices/customer/{customerId}", testCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get invoices by status")
    void shouldGetInvoicesByStatus() throws Exception {
        // Given
        createTestInvoice(InvoiceStatus.DRAFT);
        createTestInvoice(InvoiceStatus.DRAFT);
        createTestInvoice(InvoiceStatus.SENT);

        // When & Then
        mockMvc.perform(get("/api/invoices/status/{status}", InvoiceStatus.DRAFT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get invoices by date range")
    void shouldGetInvoicesByDateRange() throws Exception {
        // Given
        LocalDate today = LocalDate.now();
        createTestInvoice(InvoiceStatus.DRAFT, today);
        createTestInvoice(InvoiceStatus.SENT, today.minusDays(5));

        // When & Then
        mockMvc.perform(get("/api/invoices/date-range")
                        .param("startDate", today.minusDays(10).toString())
                        .param("endDate", today.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get invoice by ID")
    void shouldGetInvoiceById() throws Exception {
        // Given
        Invoice invoice = createTestInvoice(InvoiceStatus.DRAFT);

        // When & Then
        mockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(invoice.getId().intValue())))
                .andExpect(jsonPath("$.data.status", is(InvoiceStatus.DRAFT.toString())));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should create new invoice")
    void shouldCreateInvoice() throws Exception {
        // Given
        InvoiceRequest request = new InvoiceRequest();
        request.setCustomerId(testCustomer.getId());
        request.setInvoiceDate(LocalDate.now());
        request.setDueDate(LocalDate.now().plusDays(30));
        request.setStatus(InvoiceStatus.DRAFT);
        request.setNotes("Test invoice notes");
        request.setTermsAndConditions("Net 30 days");

        // Create invoice items
        List<InvoiceItemRequest> items = new ArrayList<>();
        InvoiceItemRequest item = new InvoiceItemRequest();
        item.setItemId(testItem.getId());
        item.setDescription("Test product");
        item.setQuantity(new BigDecimal("2"));
        item.setUnitPrice(new BigDecimal("100.00"));
        item.setDiscount(new BigDecimal("0"));
        item.setTaxRate(new BigDecimal("10"));
        item.setLineOrder(1);
        items.add(item);
        request.setItems(items);

        // When & Then
        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Invoice created")))
                .andExpect(jsonPath("$.data.id", notNullValue()))
                .andExpect(jsonPath("$.data.status", is(InvoiceStatus.DRAFT.toString())))
                .andExpect(jsonPath("$.data.totalAmount", greaterThan(0)));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should fail to create invoice without customer")
    void shouldFailToCreateInvoiceWithoutCustomer() throws Exception {
        // Given
        InvoiceRequest request = new InvoiceRequest();
        request.setInvoiceDate(LocalDate.now());
        request.setDueDate(LocalDate.now().plusDays(30));

        // When & Then
        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should fail to create invoice without invoice date")
    void shouldFailToCreateInvoiceWithoutInvoiceDate() throws Exception {
        // Given
        InvoiceRequest request = new InvoiceRequest();
        request.setCustomerId(testCustomer.getId());
        request.setDueDate(LocalDate.now().plusDays(30));

        // When & Then
        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ACCOUNTANT")
    @DisplayName("Should update invoice")
    void shouldUpdateInvoice() throws Exception {
        // Given
        Invoice existingInvoice = createTestInvoice(InvoiceStatus.DRAFT);

        InvoiceRequest updateRequest = new InvoiceRequest();
        updateRequest.setCustomerId(testCustomer.getId());
        updateRequest.setInvoiceDate(LocalDate.now());
        updateRequest.setDueDate(LocalDate.now().plusDays(60));
        updateRequest.setStatus(InvoiceStatus.DRAFT);
        updateRequest.setNotes("Updated notes");

        List<InvoiceItemRequest> items = new ArrayList<>();
        InvoiceItemRequest item = new InvoiceItemRequest();
        item.setItemId(testItem.getId());
        item.setDescription("Updated product");
        item.setQuantity(new BigDecimal("5"));
        item.setUnitPrice(new BigDecimal("150.00"));
        item.setDiscount(new BigDecimal("0"));
        item.setTaxRate(new BigDecimal("10"));
        item.setLineOrder(1);
        items.add(item);
        updateRequest.setItems(items);

        // When & Then
        mockMvc.perform(put("/api/invoices/{id}", existingInvoice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Invoice updated")))
                .andExpect(jsonPath("$.data.notes", is("Updated notes")));
    }

    @Test
    @WithMockUser(roles = "ACCOUNTANT")
    @DisplayName("Should update invoice status")
    void shouldUpdateInvoiceStatus() throws Exception {
        // Given
        Invoice invoice = createTestInvoice(InvoiceStatus.DRAFT);

        // When & Then
        mockMvc.perform(put("/api/invoices/{id}/status", invoice.getId())
                        .param("status", InvoiceStatus.SENT.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Invoice status updated")))
                .andExpect(jsonPath("$.data.status", is(InvoiceStatus.SENT.toString())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should delete invoice")
    void shouldDeleteInvoice() throws Exception {
        // Given
        Invoice invoice = createTestInvoice(InvoiceStatus.DRAFT);
        Long invoiceId = invoice.getId();

        // When & Then
        mockMvc.perform(delete("/api/invoices/{id}", invoiceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Invoice deleted")));

        // Verify deletion
        mockMvc.perform(get("/api/invoices/{id}", invoiceId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when invoice not found")
    void shouldReturn404WhenInvoiceNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/invoices/{id}", 99999L))
                .andExpect(status().isNotFound());
    }

    // Helper methods
    private Invoice createTestInvoice(InvoiceStatus status) {
        return createTestInvoice(status, LocalDate.now());
    }

    private Invoice createTestInvoice(InvoiceStatus status, LocalDate invoiceDate) {
        Invoice invoice = new Invoice();
        invoice.setCustomer(testCustomer);
        invoice.setOrganization(testOrganization);
        invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
        invoice.setInvoiceDate(invoiceDate);
        invoice.setDueDate(invoiceDate.plusDays(30));
        invoice.setStatus(status);
        invoice.setSubtotal(new BigDecimal("200.00"));
        invoice.setTaxAmount(new BigDecimal("20.00"));
        invoice.setTotalAmount(new BigDecimal("220.00"));
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setNotes("Test invoice");
        return invoiceRepository.save(invoice);
    }
}
