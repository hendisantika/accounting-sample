package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.tax.TaxRequest;
import id.my.hendisantika.accountingsample.dto.tax.TaxResponse;
import id.my.hendisantika.accountingsample.service.TaxService;
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
 * Time: 13.20
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/taxes")
@RequiredArgsConstructor
@Tag(name = "Tax", description = "Tax management endpoints")
public class TaxController {

    private final TaxService taxService;

    @GetMapping
    @Operation(summary = "Get all taxes")
    public ResponseEntity<ApiResponse<List<TaxResponse>>> getAllTaxes() {
        List<TaxResponse> taxes = taxService.getAllTaxes();
        return ResponseEntity.ok(ApiResponse.success("Taxes retrieved", taxes));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active taxes")
    public ResponseEntity<ApiResponse<List<TaxResponse>>> getActiveTaxes() {
        List<TaxResponse> taxes = taxService.getActiveTaxes();
        return ResponseEntity.ok(ApiResponse.success("Active taxes retrieved", taxes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tax by ID")
    public ResponseEntity<ApiResponse<TaxResponse>> getTaxById(@PathVariable Long id) {
        TaxResponse tax = taxService.getTaxById(id);
        return ResponseEntity.ok(ApiResponse.success("Tax retrieved", tax));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get tax by code")
    public ResponseEntity<ApiResponse<TaxResponse>> getTaxByCode(@PathVariable String code) {
        TaxResponse tax = taxService.getTaxByCode(code);
        return ResponseEntity.ok(ApiResponse.success("Tax retrieved", tax));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Create new tax")
    public ResponseEntity<ApiResponse<TaxResponse>> createTax(@Valid @RequestBody TaxRequest request) {
        TaxResponse tax = taxService.createTax(request);
        return ResponseEntity.ok(ApiResponse.success("Tax created", tax));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Update tax")
    public ResponseEntity<ApiResponse<TaxResponse>> updateTax(
            @PathVariable Long id,
            @Valid @RequestBody TaxRequest request) {
        TaxResponse tax = taxService.updateTax(id, request);
        return ResponseEntity.ok(ApiResponse.success("Tax updated", tax));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Delete tax")
    public ResponseEntity<ApiResponse<Void>> deleteTax(@PathVariable Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.ok(ApiResponse.success("Tax deleted", null));
    }
}
