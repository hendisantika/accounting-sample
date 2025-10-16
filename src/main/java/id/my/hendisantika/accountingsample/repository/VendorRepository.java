package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Vendor;
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
 * Time: 13.15
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    List<Vendor> findByOrganizationId(Long organizationId);

    Optional<Vendor> findByIdAndOrganizationId(Long id, Long organizationId);

    List<Vendor> findByIsActiveAndOrganizationId(Boolean isActive, Long organizationId);

    List<Vendor> findByNameContainingIgnoreCaseAndOrganizationId(String name, Long organizationId);

    Optional<Vendor> findByEmailAndOrganizationId(String email, Long organizationId);

    boolean existsByEmailAndOrganizationId(String email, Long organizationId);
}
