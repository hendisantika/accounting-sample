package id.my.hendisantika.accountingsample.multitenancy;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 11.10
 * To change this template use File | Settings | File Templates.
 */
public interface TenantAware {
    Long getOrganizationId();

    void setOrganizationId(Long organizationId);
}
