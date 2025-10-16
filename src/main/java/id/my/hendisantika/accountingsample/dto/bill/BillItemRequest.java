package id.my.hendisantika.accountingsample.dto.bill;

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
 * Time: 13.35
 * To change this template use File | Settings | File Templates.
 */
@Data
public class BillItemRequest {

    @NotNull(message = "Item ID is required")
    private Long itemId;

    private String description;

    @NotNull(message = "Quantity is required")
    private BigDecimal quantity;

    @NotNull(message = "Unit price is required")
    private BigDecimal unitPrice;

    private BigDecimal discount;

    private BigDecimal taxRate;

    private Integer lineOrder;
}
