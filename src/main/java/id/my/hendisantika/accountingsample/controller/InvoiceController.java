package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceRequest;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceResponse;
import id.my.hendisantika.accountingsample.model.enums.InvoiceStatus;
import id.my.hendisantika.accountingsample.service.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 13.30
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoice", description = "Invoice management endpoints")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    @Operation(summary = "Get all invoices")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAllInvoices() {
        List<InvoiceResponse> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(ApiResponse.success("Invoices retrieved", invoices));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get invoices by customer")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getInvoicesByCustomer(@PathVariable Long customerId) {
        List<InvoiceResponse> invoices = invoiceService.getInvoicesByCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.success("Customer invoices retrieved", invoices));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get invoices by status")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getInvoicesByStatus(@PathVariable InvoiceStatus status) {
        List<InvoiceResponse> invoices = invoiceService.getInvoicesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Invoices retrieved by status", invoices));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get invoices by date range")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getInvoicesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<InvoiceResponse> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Invoices retrieved by date range", invoices));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice by ID")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable Long id) {
        InvoiceResponse invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(ApiResponse.success("Invoice retrieved", invoice));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Create new invoice")
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        InvoiceResponse invoice = invoiceService.createInvoice(request);
        return ResponseEntity.ok(ApiResponse.success("Invoice created", invoice));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Update invoice")
    public ResponseEntity<ApiResponse<InvoiceResponse>> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceRequest request) {
        InvoiceResponse invoice = invoiceService.updateInvoice(id, request);
        return ResponseEntity.ok(ApiResponse.success("Invoice updated", invoice));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Update invoice status")
    public ResponseEntity<ApiResponse<InvoiceResponse>> updateInvoiceStatus(
            @PathVariable Long id,
            @RequestParam InvoiceStatus status) {
        InvoiceResponse invoice = invoiceService.updateInvoiceStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Invoice status updated", invoice));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Delete invoice")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok(ApiResponse.success("Invoice deleted", null));
    }
}
