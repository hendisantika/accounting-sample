package id.my.hendisantika.accountingsample.dto.account;

import id.my.hendisantika.accountingsample.model.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 12.15
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String code;
    private String name;
    private AccountType accountType;
    private Long parentId;
    private String parentName;
    private String description;
    private BigDecimal currentBalance;
    private Boolean isActive;
    private Boolean isSystem;
    private Boolean taxApplicable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
