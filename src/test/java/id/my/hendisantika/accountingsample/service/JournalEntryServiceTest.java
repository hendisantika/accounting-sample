package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.journal.JournalEntryLineRequest;
import id.my.hendisantika.accountingsample.dto.journal.JournalEntryRequest;
import id.my.hendisantika.accountingsample.dto.journal.JournalEntryResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.JournalEntry;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.enums.AccountType;
import id.my.hendisantika.accountingsample.model.enums.JournalEntryStatus;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.JournalEntryRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("JournalEntryService Tests")
class JournalEntryServiceTest {

    @Mock
    private JournalEntryRepository journalEntryRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private JournalEntryService journalEntryService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Account debitAccount;
    private Account creditAccount;
    private JournalEntry journalEntry;
    private JournalEntryRequest journalEntryRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Org")
                .build();
        organization.setId(1L);

        debitAccount = Account.builder()
                .name("Cash")
                .accountType(AccountType.ASSET)
                .currentBalance(BigDecimal.ZERO)
                .organization(organization)
                .build();
        debitAccount.setId(1L);

        creditAccount = Account.builder()
                .name("Revenue")
                .accountType(AccountType.REVENUE)
                .currentBalance(BigDecimal.ZERO)
                .organization(organization)
                .build();
        creditAccount.setId(2L);

        journalEntry = JournalEntry.builder()
                .organization(organization)
                .journalNumber("JE-001")
                .entryDate(LocalDate.now())
                .status(JournalEntryStatus.DRAFT)
                .description("Test Entry")
                .totalDebit(BigDecimal.valueOf(100))
                .totalCredit(BigDecimal.valueOf(100))
                .lines(new ArrayList<>())
                .build();
        journalEntry.setId(1L);

        JournalEntryLineRequest debitLine = new JournalEntryLineRequest();
        debitLine.setAccountId(1L);
        debitLine.setDebitAmount(BigDecimal.valueOf(100));
        debitLine.setCreditAmount(BigDecimal.ZERO);
        debitLine.setLineOrder(1);

        JournalEntryLineRequest creditLine = new JournalEntryLineRequest();
        creditLine.setAccountId(2L);
        creditLine.setDebitAmount(BigDecimal.ZERO);
        creditLine.setCreditAmount(BigDecimal.valueOf(100));
        creditLine.setLineOrder(2);

        journalEntryRequest = new JournalEntryRequest();
        journalEntryRequest.setJournalNumber("JE-002");
        journalEntryRequest.setEntryDate(LocalDate.now());
        journalEntryRequest.setDescription("Test Entry");
        journalEntryRequest.setLines(Arrays.asList(debitLine, creditLine));
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should create journal entry successfully")
    void createJournalEntry_Success() {
        when(journalEntryRepository.existsByJournalNumberAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(accountRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(debitAccount));
        when(accountRepository.findByIdAndOrganizationId(2L, 1L)).thenReturn(Optional.of(creditAccount));
        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(journalEntry);

        JournalEntryResponse response = journalEntryService.createJournalEntry(journalEntryRequest);
        assertThat(response).isNotNull();
        verify(journalEntryRepository).save(any(JournalEntry.class));
    }

    @Test
    @DisplayName("Should throw exception when journal entry not balanced")
    void createJournalEntry_NotBalanced_ThrowsException() {
        JournalEntryLineRequest unbalancedLine = new JournalEntryLineRequest();
        unbalancedLine.setAccountId(1L);
        unbalancedLine.setDebitAmount(BigDecimal.valueOf(200));
        unbalancedLine.setCreditAmount(BigDecimal.ZERO);

        journalEntryRequest.setLines(List.of(unbalancedLine));

        when(journalEntryRepository.existsByJournalNumberAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(accountRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(debitAccount));

        assertThatThrownBy(() -> journalEntryService.createJournalEntry(journalEntryRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Journal entry is not balanced");
    }

    @Test
    @DisplayName("Should throw exception when updating non-draft journal entry")
    void updateJournalEntry_NonDraft_ThrowsException() {
        journalEntry.setStatus(JournalEntryStatus.POSTED);
        when(journalEntryRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(journalEntry));

        assertThatThrownBy(() -> journalEntryService.updateJournalEntry(1L, journalEntryRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot update journal entry that is not in DRAFT status");
    }

    @Test
    @DisplayName("Should delete draft journal entry")
    void deleteJournalEntry_Draft_Success() {
        when(journalEntryRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(journalEntry));
        journalEntryService.deleteJournalEntry(1L);
        verify(journalEntryRepository).delete(journalEntry);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-draft journal entry")
    void deleteJournalEntry_NonDraft_ThrowsException() {
        journalEntry.setStatus(JournalEntryStatus.POSTED);
        when(journalEntryRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(journalEntry));

        assertThatThrownBy(() -> journalEntryService.deleteJournalEntry(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot delete journal entry that is not in DRAFT status");
    }
}
