package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Customer;
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
 * Time: 12.50
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByOrganizationId(Long organizationId);

    Optional<Customer> findByIdAndOrganizationId(Long id, Long organizationId);

    List<Customer> findByIsActiveAndOrganizationId(Boolean isActive, Long organizationId);

    List<Customer> findByNameContainingIgnoreCaseAndOrganizationId(String name, Long organizationId);

    Optional<Customer> findByEmailAndOrganizationId(String email, Long organizationId);

    boolean existsByEmailAndOrganizationId(String email, Long organizationId);
}
