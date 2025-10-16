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

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String code;

    @NotBlank
    @Column(nullable = false)
    private String sku;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(length = 50)
    private String unit;

    @Column(name = "sale_price", precision = 15, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "purchase_price", precision = 15, scale = 2)
    private BigDecimal purchasePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_tax_id")
    private Tax salesTax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_tax_id")
    private Tax purchaseTax;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_account_id")
    private Account salesAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_account_id")
    private Account purchaseAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_account_id")
    private Account inventoryAccount;

    @Column(name = "track_inventory", nullable = false)
    @Builder.Default
    private Boolean trackInventory = false;

    @Column(name = "current_stock", nullable = false, precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal currentStock = BigDecimal.ZERO;

    @Column(name = "reorder_level", precision = 15, scale = 2)
    private BigDecimal reorderLevel;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;
}
