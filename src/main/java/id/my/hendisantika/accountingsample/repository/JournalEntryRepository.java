package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.JournalEntry;
import id.my.hendisantika.accountingsample.model.enums.JournalEntryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 13.45
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByOrganizationId(Long organizationId);

    Optional<JournalEntry> findByIdAndOrganizationId(Long id, Long organizationId);

    List<JournalEntry> findByStatusAndOrganizationId(JournalEntryStatus status, Long organizationId);

    Optional<JournalEntry> findByJournalNumberAndOrganizationId(String journalNumber, Long organizationId);

    boolean existsByJournalNumberAndOrganizationId(String journalNumber, Long organizationId);
}
