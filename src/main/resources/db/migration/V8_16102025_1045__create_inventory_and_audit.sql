-- Warehouses table
CREATE TABLE warehouses (
                            id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                            organization_id BIGINT    NOT NULL,
    warehouse_code VARCHAR(50) NOT NULL,
    warehouse_name VARCHAR(255) NOT NULL,
    address_line1 VARCHAR(255),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT uk_warehouses_org_code UNIQUE (organization_id, warehouse_code),
                            CONSTRAINT fk_warehouses_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inventory items table
CREATE TABLE inventory_items (
                                 id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 organization_id BIGINT    NOT NULL,
                                 item_id         BIGINT    NOT NULL,
                                 warehouse_id    BIGINT    NOT NULL,
    quantity_on_hand DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    quantity_available DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    quantity_committed DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    average_cost DECIMAL(19,4) DEFAULT 0.0000,
                                 updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 CONSTRAINT uk_inventory_items_item_warehouse UNIQUE (item_id, warehouse_id),
                                 CONSTRAINT fk_inventory_items_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                                 CONSTRAINT fk_inventory_items_item FOREIGN KEY (item_id) REFERENCES items (id),
                                 CONSTRAINT fk_inventory_items_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inventory adjustments table
CREATE TABLE inventory_adjustments (
                                       id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       organization_id BIGINT    NOT NULL,
    adjustment_number VARCHAR(50) NOT NULL,
    adjustment_date DATE NOT NULL,
                                       warehouse_id    BIGINT    NOT NULL,
                                       item_id         BIGINT    NOT NULL,
    adjustment_type VARCHAR(50) NOT NULL,
    quantity_before DECIMAL(19,4) NOT NULL,
    quantity_adjusted DECIMAL(19,4) NOT NULL,
    quantity_after DECIMAL(19,4) NOT NULL,
    reason TEXT,
                                       created_by_id   BIGINT,
                                       created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                       CONSTRAINT fk_inventory_adjustments_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                                       CONSTRAINT fk_inventory_adjustments_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses (id),
                                       CONSTRAINT fk_inventory_adjustments_item FOREIGN KEY (item_id) REFERENCES items (id),
                                       CONSTRAINT fk_inventory_adjustments_created_by FOREIGN KEY (created_by_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Audit logs table
CREATE TABLE audit_logs (
                            id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                            organization_id BIGINT    NOT NULL,
                            user_id         BIGINT,
    entity_type VARCHAR(100) NOT NULL,
    entity_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    old_values TEXT,
    new_values TEXT,
    ip_address VARCHAR(50),
    user_agent VARCHAR(255),
                            created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_audit_logs_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                            CONSTRAINT fk_audit_logs_user FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes
CREATE INDEX idx_inventory_items_item_id ON inventory_items(item_id);
CREATE INDEX idx_inventory_items_warehouse_id ON inventory_items(warehouse_id);
CREATE INDEX idx_audit_logs_organization_id ON audit_logs(organization_id);
CREATE INDEX idx_audit_logs_entity ON audit_logs(entity_type, entity_id);
CREATE INDEX idx_audit_logs_user_id ON audit_logs(user_id);
CREATE INDEX idx_audit_logs_created_at ON audit_logs(created_at);
