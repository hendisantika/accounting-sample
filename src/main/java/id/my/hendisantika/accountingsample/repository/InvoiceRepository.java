package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Invoice;
import id.my.hendisantika.accountingsample.model.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
 * Time: 13.30
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByOrganizationId(Long organizationId);

    Optional<Invoice> findByIdAndOrganizationId(Long id, Long organizationId);

    List<Invoice> findByCustomerIdAndOrganizationId(Long customerId, Long organizationId);

    List<Invoice> findByStatusAndOrganizationId(InvoiceStatus status, Long organizationId);

    List<Invoice> findByInvoiceDateBetweenAndOrganizationId(LocalDate startDate, LocalDate endDate, Long organizationId);

    boolean existsByInvoiceNumberAndOrganizationId(String invoiceNumber, Long organizationId);
}
