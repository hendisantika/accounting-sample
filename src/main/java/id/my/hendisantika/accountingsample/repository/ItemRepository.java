package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Item;
import id.my.hendisantika.accountingsample.model.enums.ItemType;
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
 * Time: 13.25
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOrganizationId(Long organizationId);

    Optional<Item> findByIdAndOrganizationId(Long id, Long organizationId);

    List<Item> findByItemTypeAndOrganizationId(ItemType itemType, Long organizationId);

    List<Item> findByIsActiveAndOrganizationId(Boolean isActive, Long organizationId);

    List<Item> findByNameContainingIgnoreCaseAndOrganizationId(String name, Long organizationId);

    boolean existsByCodeAndOrganizationId(String code, Long organizationId);
}
