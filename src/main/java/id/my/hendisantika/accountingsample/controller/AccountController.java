package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.account.AccountRequest;
import id.my.hendisantika.accountingsample.dto.account.AccountResponse;
import id.my.hendisantika.accountingsample.model.enums.AccountType;
import id.my.hendisantika.accountingsample.service.AccountService;
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
 * Time: 12.30
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Chart of Accounts", description = "Chart of accounts management endpoints")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "Get all accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts() {
        List<AccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(ApiResponse.success("Accounts retrieved", accounts));
    }

    @GetMapping("/type/{accountType}")
    @Operation(summary = "Get accounts by type")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccountsByType(
            @PathVariable AccountType accountType) {
        List<AccountResponse> accounts = accountService.getAccountsByType(accountType);
        return ResponseEntity.ok(ApiResponse.success("Accounts retrieved", accounts));
    }

    @GetMapping("/root")
    @Operation(summary = "Get root accounts (accounts without parents)")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getRootAccounts() {
        List<AccountResponse> accounts = accountService.getRootAccounts();
        return ResponseEntity.ok(ApiResponse.success("Root accounts retrieved", accounts));
    }

    @GetMapping("/parent/{parentId}")
    @Operation(summary = "Get child accounts of a parent account")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getChildAccounts(@PathVariable Long parentId) {
        List<AccountResponse> accounts = accountService.getChildAccounts(parentId);
        return ResponseEntity.ok(ApiResponse.success("Child accounts retrieved", accounts));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(@PathVariable Long id) {
        AccountResponse account = accountService.getAccountById(id);
        return ResponseEntity.ok(ApiResponse.success("Account retrieved", account));
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get account by code")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountByCode(@PathVariable String code) {
        AccountResponse account = accountService.getAccountByCode(code);
        return ResponseEntity.ok(ApiResponse.success("Account retrieved", account));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Create new account")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest request) {
        AccountResponse account = accountService.createAccount(request);
        return ResponseEntity.ok(ApiResponse.success("Account created", account));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Update account")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequest request) {
        AccountResponse account = accountService.updateAccount(id, request);
        return ResponseEntity.ok(ApiResponse.success("Account updated", account));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Delete account")
    public ResponseEntity<ApiResponse<Void>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(ApiResponse.success("Account deleted", null));
    }
}
