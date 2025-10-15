package id.my.hendisantika.accountingsample.dto.account;

import id.my.hendisantika.accountingsample.model.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 12.10
 * To change this template use File | Settings | File Templates.
 */
@Data
public class AccountRequest {

    @NotBlank(message = "Account code is required")
    private String code;

    @NotBlank(message = "Account name is required")
    private String name;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    private Long parentId;
    private String description;
    private Boolean isActive;
    private Boolean taxApplicable;
}
