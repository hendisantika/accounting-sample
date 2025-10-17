package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.account.AccountRequest;
import id.my.hendisantika.accountingsample.dto.account.AccountResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.enums.AccountType;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountService Tests")
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private AccountService accountService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Account account;
    private AccountRequest accountRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Org")
                .build();
        organization.setId(1L);

        account = Account.builder()
                .organization(organization)
                .code("1000")
                .name("Cash")
                .accountType(AccountType.ASSET)
                .currentBalance(BigDecimal.ZERO)
                .isActive(true)
                .isSystem(false)
                .build();
        account.setId(1L);

        accountRequest = new AccountRequest();
        accountRequest.setCode("1001");
        accountRequest.setName("Bank Account");
        accountRequest.setAccountType(AccountType.ASSET);
        accountRequest.setIsActive(true);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should get all accounts")
    void getAllAccounts_Success() {
        when(accountRepository.findByOrganizationId(1L)).thenReturn(Collections.singletonList(account));
        List<AccountResponse> responses = accountService.getAllAccounts();
        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("Should create account successfully")
    void createAccount_Success() {
        when(accountRepository.existsByCodeAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        AccountResponse response = accountService.createAccount(accountRequest);
        assertThat(response).isNotNull();
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw exception when account code already exists")
    void createAccount_DuplicateCode_ThrowsException() {
        when(accountRepository.existsByCodeAndOrganizationId(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> accountService.createAccount(accountRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Account code already exists");
    }

    @Test
    @DisplayName("Should throw exception when updating system account")
    void updateAccount_SystemAccount_ThrowsException() {
        account.setIsSystem(true);
        when(accountRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.updateAccount(1L, accountRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot modify system accounts");
    }

    @Test
    @DisplayName("Should throw exception when deleting account with children")
    void deleteAccount_WithChildren_ThrowsException() {
        Account childAccount = Account.builder()
                .parent(account)
                .build();
        childAccount.setId(2L);

        when(accountRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(account));
        when(accountRepository.findByParentIdAndOrganizationId(1L, 1L)).thenReturn(List.of(childAccount));

        assertThatThrownBy(() -> accountService.deleteAccount(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot delete account with child accounts");
    }

    @Test
    @DisplayName("Should throw exception when deleting account with non-zero balance")
    void deleteAccount_NonZeroBalance_ThrowsException() {
        account.setCurrentBalance(BigDecimal.valueOf(100));
        when(accountRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(account));
        when(accountRepository.findByParentIdAndOrganizationId(1L, 1L)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> accountService.deleteAccount(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot delete account with non-zero balance");
    }
}
