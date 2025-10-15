-- Tax rates table
CREATE TABLE tax_rates (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    tax_name VARCHAR(255) NOT NULL,
    tax_code VARCHAR(50) NOT NULL,
    tax_type VARCHAR(50) NOT NULL,
    tax_percentage DECIMAL(5,2) NOT NULL,
    tax_account_id BIGINT REFERENCES accounts(id),
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_compound BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_tax_rates_org_code UNIQUE (organization_id, tax_code)
);

-- Items table
CREATE TABLE items (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    item_code VARCHAR(50) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_type VARCHAR(50) NOT NULL DEFAULT 'GOODS',
    description TEXT,
    unit_of_measure VARCHAR(50),
    sales_price DECIMAL(19,4) DEFAULT 0.0000,
    purchase_price DECIMAL(19,4) DEFAULT 0.0000,
    sales_account_id BIGINT REFERENCES accounts(id),
    purchase_account_id BIGINT REFERENCES accounts(id),
    inventory_account_id BIGINT REFERENCES accounts(id),
    tax_id BIGINT REFERENCES tax_rates(id),
    sku VARCHAR(100),
    barcode VARCHAR(100),
    reorder_level DECIMAL(19,4),
    image_url VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_inventoried BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_items_org_code UNIQUE (organization_id, item_code)
);

-- Indexes
CREATE INDEX idx_tax_rates_organization_id ON tax_rates(organization_id);
CREATE INDEX idx_items_organization_id ON items(organization_id);
