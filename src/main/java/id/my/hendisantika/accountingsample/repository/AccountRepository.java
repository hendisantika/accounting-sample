package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
 * Time: 12.20
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByOrganizationId(Long organizationId);

    Optional<Account> findByIdAndOrganizationId(Long id, Long organizationId);

    Optional<Account> findByCodeAndOrganizationId(String code, Long organizationId);

    List<Account> findByAccountTypeAndOrganizationId(AccountType accountType, Long organizationId);

    List<Account> findByParentIdAndOrganizationId(Long parentId, Long organizationId);

    List<Account> findByIsActiveAndOrganizationId(Boolean isActive, Long organizationId);

    boolean existsByCodeAndOrganizationId(String code, Long organizationId);

    @Query("SELECT a FROM Account a WHERE a.organization.id = :organizationId AND a.parent IS NULL")
    List<Account> findRootAccountsByOrganizationId(Long organizationId);
}
