package id.my.hendisantika.accountingsample.multitenancy;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 11.00
 * To change this template use File | Settings | File Templates.
 */
public class TenantContext {

    private static final ThreadLocal<Long> currentTenant = new ThreadLocal<>();

    public static Long getTenantId() {
        return currentTenant.get();
    }

    public static void setTenantId(Long tenantId) {
        currentTenant.set(tenantId);
    }

    public static void clear() {
        currentTenant.remove();
    }
}
