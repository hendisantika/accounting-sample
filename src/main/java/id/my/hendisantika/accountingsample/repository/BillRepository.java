package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Bill;
import id.my.hendisantika.accountingsample.model.enums.BillStatus;
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
 * Time: 13.35
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByOrganizationId(Long organizationId);

    Optional<Bill> findByIdAndOrganizationId(Long id, Long organizationId);

    List<Bill> findByVendorIdAndOrganizationId(Long vendorId, Long organizationId);

    List<Bill> findByStatusAndOrganizationId(BillStatus status, Long organizationId);

    List<Bill> findByBillNumberContainingIgnoreCaseAndOrganizationId(String billNumber, Long organizationId);

    List<Bill> findByDueDateBeforeAndStatusInAndOrganizationId(
            LocalDate date, List<BillStatus> statuses, Long organizationId);

    boolean existsByBillNumberAndOrganizationId(String billNumber, Long organizationId);
}
