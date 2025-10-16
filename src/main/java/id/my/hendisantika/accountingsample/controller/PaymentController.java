package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.payment.PaymentRequest;
import id.my.hendisantika.accountingsample.dto.payment.PaymentResponse;
import id.my.hendisantika.accountingsample.model.enums.PaymentType;
import id.my.hendisantika.accountingsample.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment management endpoints")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    @Operation(summary = "Get all payments")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved", payments));
    }

    @GetMapping("/type/{paymentType}")
    @Operation(summary = "Get payments by type")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByType(
            @PathVariable PaymentType paymentType) {
        List<PaymentResponse> payments = paymentService.getPaymentsByType(paymentType);
        return ResponseEntity.ok(ApiResponse.success("Payments retrieved", payments));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get payments by customer")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByCustomer(
            @PathVariable Long customerId) {
        List<PaymentResponse> payments = paymentService.getPaymentsByCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer payments retrieved", payments));
    }

    @GetMapping("/vendor/{vendorId}")
    @Operation(summary = "Get payments by vendor")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByVendor(
            @PathVariable Long vendorId) {
        List<PaymentResponse> payments = paymentService.getPaymentsByVendor(vendorId);
        return ResponseEntity.ok(ApiResponse.success("Vendor payments retrieved", payments));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment by ID")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id) {
        PaymentResponse payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success("Payment retrieved", payment));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Create new payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @Valid @RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.createPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Payment created", payment));
    }

    @PostMapping("/invoice")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Record invoice payment - updates invoice and customer balances")
    public ResponseEntity<ApiResponse<PaymentResponse>> recordInvoicePayment(
            @Valid @RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.recordInvoicePayment(request);
        return ResponseEntity.ok(ApiResponse.success("Invoice payment recorded", payment));
    }

    @PostMapping("/bill")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Record bill payment - updates bill and vendor balances")
    public ResponseEntity<ApiResponse<PaymentResponse>> recordBillPayment(
            @Valid @RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.recordBillPayment(request);
        return ResponseEntity.ok(ApiResponse.success("Bill payment recorded", payment));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Update payment")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.updatePayment(id, request);
        return ResponseEntity.ok(ApiResponse.success("Payment updated", payment));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Delete payment")
    public ResponseEntity<ApiResponse<Void>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(ApiResponse.success("Payment deleted", null));
    }
}
