package id.my.hendisantika.accountingsample.dto.tax;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
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
 * Time: 13.20
 * To change this template use File | Settings | File Templates.
 */
@Data
public class TaxRequest {

    @NotBlank(message = "Tax name is required")
    private String name;

    @NotBlank(message = "Tax code is required")
    private String code;

    @NotNull(message = "Tax rate is required")
    @DecimalMin(value = "0.0", message = "Tax rate must be greater than or equal to 0")
    private BigDecimal rate;

    private String description;

    private Boolean isActive;

    private Boolean isCompound;
}
