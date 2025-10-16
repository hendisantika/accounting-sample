package id.my.hendisantika.accountingsample.dto.item;

import id.my.hendisantika.accountingsample.model.enums.ItemType;
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
 * Time: 13.25
 * To change this template use File | Settings | File Templates.
 */
@Data
public class ItemRequest {

    @NotBlank(message = "Item name is required")
    private String name;

    @NotBlank(message = "Item code is required")
    private String code;

    @NotBlank(message = "SKU is required")
    private String sku;

    private String description;

    @NotNull(message = "Item type is required")
    private ItemType itemType;

    private String unit;

    private BigDecimal salePrice;

    private BigDecimal purchasePrice;

    private Long salesTaxId;

    private Long purchaseTaxId;

    private Long salesAccountId;

    private Long purchaseAccountId;

    private Long inventoryAccountId;

    private Boolean trackInventory;

    private BigDecimal currentStock;

    private BigDecimal reorderLevel;

    private Boolean isActive;
}
