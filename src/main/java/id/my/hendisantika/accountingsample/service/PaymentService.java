package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.payment.PaymentRequest;
import id.my.hendisantika.accountingsample.dto.payment.PaymentResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.Bill;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Invoice;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Payment;
import id.my.hendisantika.accountingsample.model.Vendor;
import id.my.hendisantika.accountingsample.model.enums.PaymentType;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.BillRepository;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.InvoiceRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.PaymentRepository;
import id.my.hendisantika.accountingsample.repository.VendorRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 13.40
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrganizationRepository organizationRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final AccountRepository accountRepository;
    private final InvoiceRepository invoiceRepository;
    private final BillRepository billRepository;

    public List<PaymentResponse> getAllPayments() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return paymentRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByType(PaymentType paymentType) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return paymentRepository.findByPaymentTypeAndOrganizationId(paymentType, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByCustomer(Long customerId) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        // Verify customer belongs to organization
        customerRepository.findByIdAndOrganizationId(customerId, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return paymentRepository.findByCustomerIdAndOrganizationId(customerId, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentResponse> getPaymentsByVendor(Long vendorId) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        // Verify vendor belongs to organization
        vendorRepository.findByIdAndOrganizationId(vendorId, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        return paymentRepository.findByVendorIdAndOrganizationId(vendorId, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PaymentResponse getPaymentById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Payment payment = paymentRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return mapToResponse(payment);
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Validate payment type and related entities
        validatePaymentRequest(request, orgId);

        Account account = accountRepository.findByIdAndOrganizationId(request.getAccountId(), orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        Payment payment = Payment.builder()
                .organization(organization)
                .paymentType(request.getPaymentType())
                .paymentNumber(generatePaymentNumber(request.getPaymentType()))
                .paymentDate(request.getPaymentDate())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .referenceNumber(request.getReferenceNumber())
                .notes(request.getNotes())
                .account(account)
                .build();

        // Set customer or vendor based on payment type
        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findByIdAndOrganizationId(request.getCustomerId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            payment.setCustomer(customer);
        }

        if (request.getVendorId() != null) {
            Vendor vendor = vendorRepository.findByIdAndOrganizationId(request.getVendorId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
            payment.setVendor(vendor);
        }

        // Set invoice or bill if provided
        if (request.getInvoiceId() != null) {
            Invoice invoice = invoiceRepository.findByIdAndOrganizationId(request.getInvoiceId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
            payment.setInvoice(invoice);
        }

        if (request.getBillId() != null) {
            Bill bill = billRepository.findByIdAndOrganizationId(request.getBillId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
            payment.setBill(bill);
        }

        payment = paymentRepository.save(payment);
        return mapToResponse(payment);
    }

    @Transactional
    public PaymentResponse recordInvoicePayment(PaymentRequest request) {
        if (request.getInvoiceId() == null) {
            throw new BusinessException("Invoice ID is required for invoice payment");
        }

        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Invoice invoice = invoiceRepository.findByIdAndOrganizationId(request.getInvoiceId(), orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        // Validate payment amount
        if (request.getAmount().compareTo(invoice.getBalance()) > 0) {
            throw new BusinessException("Payment amount cannot exceed invoice balance");
        }

        // Set customer from invoice if not provided
        if (request.getCustomerId() == null) {
            request.setCustomerId(invoice.getCustomer().getId());
        }

        // Set payment type to PAYMENT_RECEIVED
        request.setPaymentType(PaymentType.PAYMENT_RECEIVED);

        // Create payment
        PaymentResponse payment = createPayment(request);

        // Update invoice balances
        invoice.setPaidAmount(invoice.getPaidAmount().add(request.getAmount()));
        invoice.setBalance(invoice.getBalance().subtract(request.getAmount()));
        invoiceRepository.save(invoice);

        // Update customer outstanding balance
        Customer customer = invoice.getCustomer();
        customer.setOutstandingBalance(customer.getOutstandingBalance().subtract(request.getAmount()));
        customerRepository.save(customer);

        return payment;
    }

    @Transactional
    public PaymentResponse recordBillPayment(PaymentRequest request) {
        if (request.getBillId() == null) {
            throw new BusinessException("Bill ID is required for bill payment");
        }

        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Bill bill = billRepository.findByIdAndOrganizationId(request.getBillId(), orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));

        // Validate payment amount
        if (request.getAmount().compareTo(bill.getBalance()) > 0) {
            throw new BusinessException("Payment amount cannot exceed bill balance");
        }

        // Set vendor from bill if not provided
        if (request.getVendorId() == null) {
            request.setVendorId(bill.getVendor().getId());
        }

        // Set payment type to PAYMENT_MADE
        request.setPaymentType(PaymentType.PAYMENT_MADE);

        // Create payment
        PaymentResponse payment = createPayment(request);

        // Update bill balances
        bill.setPaidAmount(bill.getPaidAmount().add(request.getAmount()));
        bill.setBalance(bill.getBalance().subtract(request.getAmount()));
        billRepository.save(bill);

        // Update vendor outstanding balance
        Vendor vendor = bill.getVendor();
        vendor.setOutstandingBalance(vendor.getOutstandingBalance().subtract(request.getAmount()));
        vendorRepository.save(vendor);

        return payment;
    }

    @Transactional
    public PaymentResponse updatePayment(Long id, PaymentRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Payment payment = paymentRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        // Validate payment request
        validatePaymentRequest(request, orgId);

        // Update fields
        payment.setPaymentType(request.getPaymentType());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setNotes(request.getNotes());

        if (request.getAccountId() != null) {
            Account account = accountRepository.findByIdAndOrganizationId(request.getAccountId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
            payment.setAccount(account);
        }

        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findByIdAndOrganizationId(request.getCustomerId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
            payment.setCustomer(customer);
        } else {
            payment.setCustomer(null);
        }

        if (request.getVendorId() != null) {
            Vendor vendor = vendorRepository.findByIdAndOrganizationId(request.getVendorId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
            payment.setVendor(vendor);
        } else {
            payment.setVendor(null);
        }

        if (request.getInvoiceId() != null) {
            Invoice invoice = invoiceRepository.findByIdAndOrganizationId(request.getInvoiceId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
            payment.setInvoice(invoice);
        } else {
            payment.setInvoice(null);
        }

        if (request.getBillId() != null) {
            Bill bill = billRepository.findByIdAndOrganizationId(request.getBillId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
            payment.setBill(bill);
        } else {
            payment.setBill(null);
        }

        payment = paymentRepository.save(payment);
        return mapToResponse(payment);
    }

    @Transactional
    public void deletePayment(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Payment payment = paymentRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        // In production, you might want to:
        // 1. Check if payment is linked to invoice/bill and revert balances
        // 2. Implement soft delete instead of hard delete
        // 3. Add audit trail

        paymentRepository.delete(payment);
    }

    private void validatePaymentRequest(PaymentRequest request, Long orgId) {
        // Validate payment type matches entity type
        if (request.getPaymentType() == PaymentType.PAYMENT_RECEIVED) {
            if (request.getCustomerId() == null && request.getInvoiceId() == null) {
                throw new BusinessException("Customer or Invoice is required for payment received");
            }
            if (request.getVendorId() != null) {
                throw new BusinessException("Vendor cannot be set for payment received");
            }
        } else if (request.getPaymentType() == PaymentType.PAYMENT_MADE) {
            if (request.getVendorId() == null && request.getBillId() == null) {
                throw new BusinessException("Vendor or Bill is required for payment made");
            }
            if (request.getCustomerId() != null) {
                throw new BusinessException("Customer cannot be set for payment made");
            }
        }
    }

    private String generatePaymentNumber(PaymentType paymentType) {
        // Generate unique payment number
        // Format: PAY-RECV-YYYYMMDD-XXXX or PAY-MADE-YYYYMMDD-XXXX
        String prefix = paymentType == PaymentType.PAYMENT_RECEIVED ? "PAY-RECV" : "PAY-MADE";
        String datePart = LocalDate.now().toString().replace("-", "");
        long count = paymentRepository.count() + 1;
        return String.format("%s-%s-%04d", prefix, datePart, count);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse.PaymentResponseBuilder builder = PaymentResponse.builder()
                .id(payment.getId())
                .paymentType(payment.getPaymentType())
                .paymentNumber(payment.getPaymentNumber())
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .referenceNumber(payment.getReferenceNumber())
                .notes(payment.getNotes())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt());

        if (payment.getCustomer() != null) {
            builder.customerId(payment.getCustomer().getId())
                    .customerName(payment.getCustomer().getName());
        }

        if (payment.getVendor() != null) {
            builder.vendorId(payment.getVendor().getId())
                    .vendorName(payment.getVendor().getName());
        }

        if (payment.getInvoice() != null) {
            builder.invoiceId(payment.getInvoice().getId())
                    .invoiceNumber(payment.getInvoice().getInvoiceNumber());
        }

        if (payment.getBill() != null) {
            builder.billId(payment.getBill().getId())
                    .billNumber(payment.getBill().getBillNumber());
        }

        if (payment.getAccount() != null) {
            builder.accountId(payment.getAccount().getId())
                    .accountName(payment.getAccount().getName());
        }

        return builder.build();
    }
}
