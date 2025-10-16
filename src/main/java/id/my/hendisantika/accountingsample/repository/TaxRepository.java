package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Tax;
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
 * Time: 13.20
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

    List<Tax> findByOrganizationId(Long organizationId);

    Optional<Tax> findByIdAndOrganizationId(Long id, Long organizationId);

    List<Tax> findByIsActiveAndOrganizationId(Boolean isActive, Long organizationId);

    Optional<Tax> findByCodeAndOrganizationId(String code, Long organizationId);

    boolean existsByCodeAndOrganizationId(String code, Long organizationId);
}
