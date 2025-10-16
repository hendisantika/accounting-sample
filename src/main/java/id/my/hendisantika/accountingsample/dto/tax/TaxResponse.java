package id.my.hendisantika.accountingsample.dto.tax;

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
 * Time: 13.20
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxResponse {

    private Long id;
    private String name;
    private String code;
    private BigDecimal rate;
    private String description;
    private Boolean isActive;
    private Boolean isCompound;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
