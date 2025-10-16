package id.my.hendisantika.accountingsample.model;

import id.my.hendisantika.accountingsample.model.enums.ItemType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "item_code", nullable = false)
    private String code;

    @NotBlank
    @Column(name = "item_name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "unit_of_measure", length = 50)
    private String unit;

    @Column(name = "sales_price", precision = 19, scale = 4)
    private BigDecimal salePrice;

    @Column(name = "purchase_price", precision = 19, scale = 4)
    private BigDecimal purchasePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_account_id")
    private Account salesAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_account_id")
    private Account purchaseAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_account_id")
    private Account inventoryAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_tax_id")
    private Tax salesTax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_tax_id")
    private Tax purchaseTax;

    @NotBlank
    @Column(nullable = false)
    private String sku;

    private String barcode;

    @Column(name = "current_stock", nullable = false, precision = 19, scale = 4)
    @Builder.Default
    private BigDecimal currentStock = BigDecimal.ZERO;

    @Column(name = "reorder_level", precision = 19, scale = 4)
    private BigDecimal reorderLevel;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "is_inventoried", nullable = false)
    @Builder.Default
    private Boolean trackInventory = false;
}
