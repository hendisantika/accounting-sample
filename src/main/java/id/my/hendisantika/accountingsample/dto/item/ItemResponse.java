package id.my.hendisantika.accountingsample.dto.item;

import id.my.hendisantika.accountingsample.model.enums.ItemType;
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
 * Time: 13.25
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;
    private String name;
    private String code;
    private String sku;
    private String description;
    private ItemType itemType;
    private String unit;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
    private Long salesTaxId;
    private String salesTaxName;
    private Long purchaseTaxId;
    private String purchaseTaxName;
    private Long salesAccountId;
    private String salesAccountName;
    private Long purchaseAccountId;
    private String purchaseAccountName;
    private Long inventoryAccountId;
    private String inventoryAccountName;
    private Boolean trackInventory;
    private BigDecimal currentStock;
    private BigDecimal reorderLevel;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
