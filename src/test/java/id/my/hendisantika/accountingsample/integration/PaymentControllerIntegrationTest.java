package id.my.hendisantika.accountingsample.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.my.hendisantika.accountingsample.dto.payment.PaymentRequest;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Invoice;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Payment;
import id.my.hendisantika.accountingsample.model.Vendor;
import id.my.hendisantika.accountingsample.model.enums.AccountType;
import id.my.hendisantika.accountingsample.model.enums.InvoiceStatus;
import id.my.hendisantika.accountingsample.model.enums.PaymentMethod;
import id.my.hendisantika.accountingsample.model.enums.PaymentType;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.InvoiceRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.PaymentRepository;
import id.my.hendisantika.accountingsample.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Payment Controller Integration Tests")
class PaymentControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    private Organization testOrganization;
    private Customer testCustomer;
    private Vendor testVendor;
    private Account testBankAccount;
    private Invoice testInvoice;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
        invoiceRepository.deleteAll();
        customerRepository.deleteAll();
        vendorRepository.deleteAll();

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

        // Create test vendor
        testVendor = new Vendor();
        testVendor.setName("Test Vendor");
        testVendor.setEmail("vendor@example.com");
        testVendor.setPhone("1234567890");
        testVendor.setIsActive(true);
        testVendor.setOrganization(testOrganization);
        testVendor = vendorRepository.save(testVendor);

        // Ensure bank account exists
        testBankAccount = accountRepository.findByAccountTypeAndOrganizationId(AccountType.ASSET, testOrganization.getId())
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Account account = new Account();
                    account.setCode("1000");
                    account.setName("Bank Account");
                    account.setAccountType(AccountType.ASSET);
                    account.setOrganization(testOrganization);
                    account.setIsActive(true);
                    return accountRepository.save(account);
                });

        // Create test invoice
        testInvoice = new Invoice();
        testInvoice.setCustomer(testCustomer);
        testInvoice.setOrganization(testOrganization);
        testInvoice.setInvoiceNumber("INV-TEST-001");
        testInvoice.setInvoiceDate(LocalDate.now());
        testInvoice.setDueDate(LocalDate.now().plusDays(30));
        testInvoice.setStatus(InvoiceStatus.SENT);
        testInvoice.setSubtotal(new BigDecimal("1000.00"));
        testInvoice.setTaxAmount(new BigDecimal("100.00"));
        testInvoice.setTotalAmount(new BigDecimal("1100.00"));
        testInvoice.setPaidAmount(BigDecimal.ZERO);
        testInvoice = invoiceRepository.save(testInvoice);
    }

    @Test
    @DisplayName("Should get all payments")
    void shouldGetAllPayments() throws Exception {
        // Given
        createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("500.00"));
        createTestPayment(PaymentType.PAYMENT_MADE, new BigDecimal("300.00"));

        // When & Then
        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Payments retrieved")))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get payments by type")
    void shouldGetPaymentsByType() throws Exception {
        // Given
        createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("500.00"));
        createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("600.00"));
        createTestPayment(PaymentType.PAYMENT_MADE, new BigDecimal("300.00"));

        // When & Then
        mockMvc.perform(get("/api/payments/type/{paymentType}", PaymentType.PAYMENT_RECEIVED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get payments by customer")
    void shouldGetPaymentsByCustomer() throws Exception {
        // Given
        createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("500.00"));
        createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("600.00"));

        // When & Then
        mockMvc.perform(get("/api/payments/customer/{customerId}", testCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    @DisplayName("Should get payments by vendor")
    void shouldGetPaymentsByVendor() throws Exception {
        // Given
        Payment payment = new Payment();
        payment.setPaymentType(PaymentType.PAYMENT_MADE);
        payment.setVendor(testVendor);
        payment.setOrganization(testOrganization);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(new BigDecimal("500.00"));
        payment.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
        payment.setAccount(testBankAccount);
        paymentRepository.save(payment);

        // When & Then
        mockMvc.perform(get("/api/payments/vendor/{vendorId}", testVendor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", hasSize(1)));
    }

    @Test
    @DisplayName("Should get payment by ID")
    void shouldGetPaymentById() throws Exception {
        // Given
        Payment payment = createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("500.00"));

        // When & Then
        mockMvc.perform(get("/api/payments/{id}", payment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data.id", is(payment.getId().intValue())))
                .andExpect(jsonPath("$.data.amount", is(500.00)));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should create new payment")
    void shouldCreatePayment() throws Exception {
        // Given
        PaymentRequest request = PaymentRequest.builder()
                .paymentType(PaymentType.PAYMENT_RECEIVED)
                .customerId(testCustomer.getId())
                .paymentDate(LocalDate.now())
                .amount(new BigDecimal("500.00"))
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .referenceNumber("REF-001")
                .notes("Test payment")
                .accountId(testBankAccount.getId())
                .build();

        // When & Then
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Payment created")))
                .andExpect(jsonPath("$.data.id", notNullValue()))
                .andExpect(jsonPath("$.data.amount", is(500.00)))
                .andExpect(jsonPath("$.data.paymentType", is(PaymentType.PAYMENT_RECEIVED.toString())));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should fail to create payment without payment type")
    void shouldFailToCreatePaymentWithoutPaymentType() throws Exception {
        // Given
        PaymentRequest request = PaymentRequest.builder()
                .customerId(testCustomer.getId())
                .paymentDate(LocalDate.now())
                .amount(new BigDecimal("500.00"))
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .accountId(testBankAccount.getId())
                .build();

        // When & Then
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should fail to create payment with negative amount")
    void shouldFailToCreatePaymentWithNegativeAmount() throws Exception {
        // Given
        PaymentRequest request = PaymentRequest.builder()
                .paymentType(PaymentType.PAYMENT_RECEIVED)
                .customerId(testCustomer.getId())
                .paymentDate(LocalDate.now())
                .amount(new BigDecimal("-500.00"))
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .accountId(testBankAccount.getId())
                .build();

        // When & Then
        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Should record invoice payment")
    void shouldRecordInvoicePayment() throws Exception {
        // Given
        PaymentRequest request = PaymentRequest.builder()
                .paymentType(PaymentType.PAYMENT_RECEIVED)
                .customerId(testCustomer.getId())
                .invoiceId(testInvoice.getId())
                .paymentDate(LocalDate.now())
                .amount(new BigDecimal("500.00"))
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .referenceNumber("REF-002")
                .notes("Invoice payment")
                .accountId(testBankAccount.getId())
                .build();

        // When & Then
        mockMvc.perform(post("/api/payments/invoice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Invoice payment recorded")))
                .andExpect(jsonPath("$.data.id", notNullValue()));
    }

    @Test
    @WithMockUser(roles = "ACCOUNTANT")
    @DisplayName("Should update payment")
    void shouldUpdatePayment() throws Exception {
        // Given
        Payment existingPayment = createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("500.00"));

        PaymentRequest updateRequest = PaymentRequest.builder()
                .paymentType(PaymentType.PAYMENT_RECEIVED)
                .customerId(testCustomer.getId())
                .paymentDate(LocalDate.now())
                .amount(new BigDecimal("750.00"))
                .paymentMethod(PaymentMethod.CASH)
                .referenceNumber("REF-UPDATED")
                .notes("Updated payment")
                .accountId(testBankAccount.getId())
                .build();

        // When & Then
        mockMvc.perform(put("/api/payments/{id}", existingPayment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Payment updated")))
                .andExpect(jsonPath("$.data.amount", is(750.00)))
                .andExpect(jsonPath("$.data.notes", is("Updated payment")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should delete payment")
    void shouldDeletePayment() throws Exception {
        // Given
        Payment payment = createTestPayment(PaymentType.PAYMENT_RECEIVED, new BigDecimal("500.00"));
        Long paymentId = payment.getId();

        // When & Then
        mockMvc.perform(delete("/api/payments/{id}", paymentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Payment deleted")));

        // Verify deletion
        mockMvc.perform(get("/api/payments/{id}", paymentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when payment not found")
    void shouldReturn404WhenPaymentNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/payments/{id}", 99999L))
                .andExpect(status().isNotFound());
    }

    // Helper method
    private Payment createTestPayment(PaymentType paymentType, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setPaymentType(paymentType);
        payment.setCustomer(testCustomer);
        payment.setOrganization(testOrganization);
        payment.setPaymentDate(LocalDate.now());
        payment.setAmount(amount);
        payment.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
        payment.setReferenceNumber("REF-" + System.currentTimeMillis());
        payment.setNotes("Test payment");
        payment.setAccount(testBankAccount);
        return paymentRepository.save(payment);
    }
}
