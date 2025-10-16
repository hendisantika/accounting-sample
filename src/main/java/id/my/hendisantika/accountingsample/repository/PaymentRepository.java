package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Payment;
import id.my.hendisantika.accountingsample.model.enums.PaymentType;
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
 * Time: 13.40
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrganizationId(Long organizationId);

    Optional<Payment> findByIdAndOrganizationId(Long id, Long organizationId);

    List<Payment> findByPaymentTypeAndOrganizationId(PaymentType paymentType, Long organizationId);

    List<Payment> findByCustomerIdAndOrganizationId(Long customerId, Long organizationId);

    List<Payment> findByVendorIdAndOrganizationId(Long vendorId, Long organizationId);

    List<Payment> findByInvoiceIdAndOrganizationId(Long invoiceId, Long organizationId);

    List<Payment> findByBillIdAndOrganizationId(Long billId, Long organizationId);
}
