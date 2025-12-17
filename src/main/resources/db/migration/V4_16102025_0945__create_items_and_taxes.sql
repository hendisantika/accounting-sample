-- Tax rates table
CREATE TABLE tax_rates (
                           id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                           organization_id BIGINT    NOT NULL,
    tax_name VARCHAR(255) NOT NULL,
    tax_code VARCHAR(50) NOT NULL,
    tax_type VARCHAR(50) NOT NULL,
    tax_percentage DECIMAL(5,2) NOT NULL,
                           tax_account_id  BIGINT,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_compound BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           CONSTRAINT uk_tax_rates_org_code UNIQUE (organization_id, tax_code),
                           CONSTRAINT fk_tax_rates_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                           CONSTRAINT fk_tax_rates_account FOREIGN KEY (tax_account_id) REFERENCES accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Items table
CREATE TABLE items (
                       id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
                       organization_id      BIGINT    NOT NULL,
    item_code VARCHAR(50) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_type VARCHAR(50) NOT NULL DEFAULT 'GOODS',
    description TEXT,
    unit_of_measure VARCHAR(50),
    sales_price DECIMAL(19,4) DEFAULT 0.0000,
    purchase_price DECIMAL(19,4) DEFAULT 0.0000,
                       sales_account_id     BIGINT,
                       purchase_account_id  BIGINT,
                       inventory_account_id BIGINT,
                       sales_tax_id         BIGINT,
                       purchase_tax_id      BIGINT,
    sku VARCHAR(100),
    barcode VARCHAR(100),
    current_stock DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    reorder_level DECIMAL(19,4),
    image_url VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_inventoried BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT uk_items_org_code UNIQUE (organization_id, item_code),
                       CONSTRAINT fk_items_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                       CONSTRAINT fk_items_sales_account FOREIGN KEY (sales_account_id) REFERENCES accounts (id),
                       CONSTRAINT fk_items_purchase_account FOREIGN KEY (purchase_account_id) REFERENCES accounts (id),
                       CONSTRAINT fk_items_inventory_account FOREIGN KEY (inventory_account_id) REFERENCES accounts (id),
                       CONSTRAINT fk_items_sales_tax FOREIGN KEY (sales_tax_id) REFERENCES tax_rates (id),
                       CONSTRAINT fk_items_purchase_tax FOREIGN KEY (purchase_tax_id) REFERENCES tax_rates (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes
CREATE INDEX idx_tax_rates_organization_id ON tax_rates(organization_id);
CREATE INDEX idx_items_organization_id ON items(organization_id);
