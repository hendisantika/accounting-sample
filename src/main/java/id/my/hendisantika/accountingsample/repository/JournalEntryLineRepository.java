package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.JournalEntryLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface JournalEntryLineRepository extends JpaRepository<JournalEntryLine, Long> {

    List<JournalEntryLine> findByJournalEntryId(Long journalEntryId);
}
