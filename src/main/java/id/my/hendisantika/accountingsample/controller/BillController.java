package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.bill.BillRequest;
import id.my.hendisantika.accountingsample.dto.bill.BillResponse;
import id.my.hendisantika.accountingsample.model.enums.BillStatus;
import id.my.hendisantika.accountingsample.service.BillService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
 * Time: 13.35
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
@Tag(name = "Bill", description = "Bill/Purchase Order management endpoints")
public class BillController {

    private final BillService billService;

    @GetMapping
    @Operation(summary = "Get all bills")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getAllBills() {
        List<BillResponse> bills = billService.getAllBills();
        return ResponseEntity.ok(ApiResponse.success("Bills retrieved", bills));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get bills by status")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getBillsByStatus(@PathVariable BillStatus status) {
        List<BillResponse> bills = billService.getBillsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Bills retrieved", bills));
    }

    @GetMapping("/vendor/{vendorId}")
    @Operation(summary = "Get bills by vendor")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getBillsByVendor(@PathVariable Long vendorId) {
        List<BillResponse> bills = billService.getBillsByVendor(vendorId);
        return ResponseEntity.ok(ApiResponse.success("Bills retrieved", bills));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue bills")
    public ResponseEntity<ApiResponse<List<BillResponse>>> getOverdueBills() {
        List<BillResponse> bills = billService.getOverdueBills();
        return ResponseEntity.ok(ApiResponse.success("Overdue bills retrieved", bills));
    }

    @GetMapping("/search")
    @Operation(summary = "Search bills by bill number")
    public ResponseEntity<ApiResponse<List<BillResponse>>> searchBills(@RequestParam String billNumber) {
        List<BillResponse> bills = billService.searchBills(billNumber);
        return ResponseEntity.ok(ApiResponse.success("Bills retrieved", bills));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bill by ID")
    public ResponseEntity<ApiResponse<BillResponse>> getBillById(@PathVariable Long id) {
        BillResponse bill = billService.getBillById(id);
        return ResponseEntity.ok(ApiResponse.success("Bill retrieved", bill));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Create new bill")
    public ResponseEntity<ApiResponse<BillResponse>> createBill(@Valid @RequestBody BillRequest request) {
        BillResponse bill = billService.createBill(request);
        return ResponseEntity.ok(ApiResponse.success("Bill created", bill));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Update bill")
    public ResponseEntity<ApiResponse<BillResponse>> updateBill(
            @PathVariable Long id,
            @Valid @RequestBody BillRequest request) {
        BillResponse bill = billService.updateBill(id, request);
        return ResponseEntity.ok(ApiResponse.success("Bill updated", bill));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Update bill status")
    public ResponseEntity<ApiResponse<BillResponse>> updateBillStatus(
            @PathVariable Long id,
            @RequestParam BillStatus status) {
        BillResponse bill = billService.updateBillStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Bill status updated", bill));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Delete bill")
    public ResponseEntity<ApiResponse<Void>> deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
        return ResponseEntity.ok(ApiResponse.success("Bill deleted", null));
    }
}
