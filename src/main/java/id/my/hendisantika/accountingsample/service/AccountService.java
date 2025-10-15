package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.account.AccountRequest;
import id.my.hendisantika.accountingsample.dto.account.AccountResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.enums.AccountType;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
 * Time: 12.25
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final OrganizationRepository organizationRepository;

    public List<AccountResponse> getAllAccounts() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return accountRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AccountResponse> getAccountsByType(AccountType accountType) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return accountRepository.findByAccountTypeAndOrganizationId(accountType, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AccountResponse> getRootAccounts() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return accountRepository.findRootAccountsByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<AccountResponse> getChildAccounts(Long parentId) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return accountRepository.findByParentIdAndOrganizationId(parentId, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AccountResponse getAccountById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Account account = accountRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToResponse(account);
    }

    public AccountResponse getAccountByCode(String code) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Account account = accountRepository.findByCodeAndOrganizationId(code, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToResponse(account);
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        // Check if account code already exists
        if (accountRepository.existsByCodeAndOrganizationId(request.getCode(), orgId)) {
            throw new BusinessException("Account code already exists");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Account parent = null;
        if (request.getParentId() != null) {
            parent = accountRepository.findByIdAndOrganizationId(request.getParentId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Parent account not found"));
        }

        Account account = Account.builder()
                .organization(organization)
                .code(request.getCode())
                .name(request.getName())
                .accountType(request.getAccountType())
                .parent(parent)
                .description(request.getDescription())
                .currentBalance(BigDecimal.ZERO)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .isSystem(false)
                .taxApplicable(request.getTaxApplicable())
                .build();

        account = accountRepository.save(account);
        return mapToResponse(account);
    }

    @Transactional
    public AccountResponse updateAccount(Long id, AccountRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Account account = accountRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // Prevent modifying system accounts
        if (account.getIsSystem()) {
            throw new BusinessException("Cannot modify system accounts");
        }

        // Check if new code already exists (if code is being changed)
        if (!account.getCode().equals(request.getCode()) &&
                accountRepository.existsByCodeAndOrganizationId(request.getCode(), orgId)) {
            throw new BusinessException("Account code already exists");
        }

        // Update parent if provided
        if (request.getParentId() != null && !request.getParentId().equals(id)) {
            Account parent = accountRepository.findByIdAndOrganizationId(request.getParentId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Parent account not found"));
            account.setParent(parent);
        } else if (request.getParentId() == null) {
            account.setParent(null);
        }

        // Update fields
        account.setCode(request.getCode());
        account.setName(request.getName());
        account.setAccountType(request.getAccountType());
        account.setDescription(request.getDescription());
        if (request.getIsActive() != null) account.setIsActive(request.getIsActive());
        account.setTaxApplicable(request.getTaxApplicable());

        account = accountRepository.save(account);
        return mapToResponse(account);
    }

    @Transactional
    public void deleteAccount(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Account account = accountRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // Prevent deleting system accounts
        if (account.getIsSystem()) {
            throw new BusinessException("Cannot delete system accounts");
        }

        // Check if account has children
        List<Account> children = accountRepository.findByParentIdAndOrganizationId(id, orgId);
        if (!children.isEmpty()) {
            throw new BusinessException("Cannot delete account with child accounts");
        }

        // Check if account has transactions (balance != 0)
        if (account.getCurrentBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessException("Cannot delete account with non-zero balance");
        }

        accountRepository.delete(account);
    }

    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .code(account.getCode())
                .name(account.getName())
                .accountType(account.getAccountType())
                .parentId(account.getParent() != null ? account.getParent().getId() : null)
                .parentName(account.getParent() != null ? account.getParent().getName() : null)
                .description(account.getDescription())
                .currentBalance(account.getCurrentBalance())
                .isActive(account.getIsActive())
                .isSystem(account.getIsSystem())
                .taxApplicable(account.getTaxApplicable())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
