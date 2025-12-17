-- Invoices table
CREATE TABLE invoices (
                          id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                          organization_id BIGINT    NOT NULL,
                          customer_id     BIGINT    NOT NULL,
    invoice_number VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50),
    invoice_date DATE NOT NULL,
    due_date DATE NOT NULL,
    payment_terms INT,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    currency_code VARCHAR(3) NOT NULL DEFAULT 'USD',
    exchange_rate DECIMAL(19,6) DEFAULT 1.000000,
    subtotal DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    tax_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    discount_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    total_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    paid_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    balance_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    notes TEXT,
    terms_and_conditions TEXT,
    billing_address TEXT,
    shipping_address TEXT,
                          created_by_id   BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT uk_invoices_org_number UNIQUE (organization_id, invoice_number),
                          CONSTRAINT fk_invoices_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                          CONSTRAINT fk_invoices_customer FOREIGN KEY (customer_id) REFERENCES customers (id),
                          CONSTRAINT fk_invoices_created_by FOREIGN KEY (created_by_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Invoice items table
CREATE TABLE invoice_items (
                               id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                               invoice_id BIGINT    NOT NULL,
                               item_id    BIGINT,
    description TEXT NOT NULL,
    quantity DECIMAL(19,4) NOT NULL DEFAULT 1.0000,
    unit_price DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    discount_percent DECIMAL(5,2) DEFAULT 0.00,
    discount_amount DECIMAL(19,4) DEFAULT 0.0000,
                               tax_id     BIGINT,
    tax_amount DECIMAL(19,4) DEFAULT 0.0000,
    line_total DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
                               account_id BIGINT,
                               line_order INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               CONSTRAINT fk_invoice_items_invoice FOREIGN KEY (invoice_id) REFERENCES invoices (id) ON DELETE CASCADE,
                               CONSTRAINT fk_invoice_items_item FOREIGN KEY (item_id) REFERENCES items (id),
                               CONSTRAINT fk_invoice_items_tax FOREIGN KEY (tax_id) REFERENCES tax_rates (id),
                               CONSTRAINT fk_invoice_items_account FOREIGN KEY (account_id) REFERENCES accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Bills table
CREATE TABLE bills (
                       id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                       organization_id BIGINT    NOT NULL,
                       vendor_id       BIGINT    NOT NULL,
    bill_number VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50),
    bill_date DATE NOT NULL,
    due_date DATE NOT NULL,
    payment_terms INT,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    currency_code VARCHAR(3) NOT NULL DEFAULT 'USD',
    exchange_rate DECIMAL(19,6) DEFAULT 1.000000,
    subtotal DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    tax_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    discount_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    total_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    paid_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    balance_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    notes TEXT,
                       created_by_id   BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT uk_bills_org_number UNIQUE (organization_id, bill_number),
                       CONSTRAINT fk_bills_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                       CONSTRAINT fk_bills_vendor FOREIGN KEY (vendor_id) REFERENCES vendors (id),
                       CONSTRAINT fk_bills_created_by FOREIGN KEY (created_by_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Bill items table
CREATE TABLE bill_items (
                            id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                            bill_id    BIGINT    NOT NULL,
                            item_id    BIGINT,
    description TEXT NOT NULL,
    quantity DECIMAL(19,4) NOT NULL DEFAULT 1.0000,
    unit_price DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    discount_percent DECIMAL(5,2) DEFAULT 0.00,
    discount_amount DECIMAL(19,4) DEFAULT 0.0000,
                            tax_id     BIGINT,
    tax_amount DECIMAL(19,4) DEFAULT 0.0000,
    line_total DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
                            account_id BIGINT,
                            line_order INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            CONSTRAINT fk_bill_items_bill FOREIGN KEY (bill_id) REFERENCES bills (id) ON DELETE CASCADE,
                            CONSTRAINT fk_bill_items_item FOREIGN KEY (item_id) REFERENCES items (id),
                            CONSTRAINT fk_bill_items_tax FOREIGN KEY (tax_id) REFERENCES tax_rates (id),
                            CONSTRAINT fk_bill_items_account FOREIGN KEY (account_id) REFERENCES accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes
CREATE INDEX idx_invoices_organization_id ON invoices(organization_id);
CREATE INDEX idx_invoices_customer_id ON invoices(customer_id);
CREATE INDEX idx_invoices_status ON invoices(status);
CREATE INDEX idx_invoices_date ON invoices(invoice_date);
CREATE INDEX idx_invoice_items_invoice_id ON invoice_items(invoice_id);
CREATE INDEX idx_bills_organization_id ON bills(organization_id);
CREATE INDEX idx_bills_vendor_id ON bills(vendor_id);
CREATE INDEX idx_bills_status ON bills(status);
CREATE INDEX idx_bill_items_bill_id ON bill_items(bill_id);
