package id.my.hendisantika.accountingsample.dto.journal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 13.45
 * To change this template use File | Settings | File Templates.
 */
@Data
public class JournalEntryLineRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    private String description;

    @NotNull(message = "Debit amount is required")
    private BigDecimal debitAmount;

    @NotNull(message = "Credit amount is required")
    private BigDecimal creditAmount;

    @NotNull(message = "Line order is required")
    private Integer lineOrder;
}
