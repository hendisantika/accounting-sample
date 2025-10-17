package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.payment.PaymentRequest;
import id.my.hendisantika.accountingsample.dto.payment.PaymentResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Payment;
import id.my.hendisantika.accountingsample.model.Vendor;
import id.my.hendisantika.accountingsample.model.enums.PaymentMethod;
import id.my.hendisantika.accountingsample.model.enums.PaymentType;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.BillRepository;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.InvoiceRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.PaymentRepository;
import id.my.hendisantika.accountingsample.repository.VendorRepository;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PaymentService Tests")
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private VendorRepository vendorRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private BillRepository billRepository;

    @InjectMocks
    private PaymentService paymentService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Customer customer;
    private Vendor vendor;
    private Account account;
    private Payment payment;
    private PaymentRequest paymentRequest;

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

        vendor = Vendor.builder()
                .name("Test Vendor")
                .organization(organization)
                .build();
        vendor.setId(1L);

        account = Account.builder()
                .name("Cash")
                .organization(organization)
                .build();
        account.setId(1L);

        payment = Payment.builder()
                .organization(organization)
                .paymentType(PaymentType.PAYMENT_RECEIVED)
                .paymentNumber("PAY-001")
                .amount(BigDecimal.valueOf(100))
                .customer(customer)
                .account(account)
                .build();
        payment.setId(1L);

        paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentType(PaymentType.PAYMENT_RECEIVED);
        paymentRequest.setAmount(BigDecimal.valueOf(100));
        paymentRequest.setPaymentDate(LocalDate.now());
        paymentRequest.setPaymentMethod(PaymentMethod.CASH);
        paymentRequest.setCustomerId(1L);
        paymentRequest.setAccountId(1L);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should create payment successfully")
    void createPayment_Success() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(customerRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(customer));
        when(accountRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(account));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentRepository.count()).thenReturn(0L);

        PaymentResponse response = paymentService.createPayment(paymentRequest);
        assertThat(response).isNotNull();
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    @DisplayName("Should throw exception when payment_received has vendor")
    void createPayment_ReceivedWithVendor_ThrowsException() {
        paymentRequest.setVendorId(1L);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));

        assertThatThrownBy(() -> paymentService.createPayment(paymentRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Vendor cannot be set for payment received");
    }

    @Test
    @DisplayName("Should throw exception when payment_made has customer")
    void createPayment_MadeWithCustomer_ThrowsException() {
        paymentRequest.setPaymentType(PaymentType.PAYMENT_MADE);
        paymentRequest.setVendorId(1L); // Set a vendor so it passes the first check
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));

        assertThatThrownBy(() -> paymentService.createPayment(paymentRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Customer cannot be set for payment made");
    }
}
