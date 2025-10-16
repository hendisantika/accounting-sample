-- Invoices table
CREATE TABLE invoices (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
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
    created_by_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_invoices_org_number UNIQUE (organization_id, invoice_number)
);

-- Invoice items table
CREATE TABLE invoice_items (
    id BIGSERIAL PRIMARY KEY,
    invoice_id BIGINT NOT NULL REFERENCES invoices(id) ON DELETE CASCADE,
    item_id BIGINT REFERENCES items(id),
    description TEXT NOT NULL,
    quantity DECIMAL(19,4) NOT NULL DEFAULT 1.0000,
    unit_price DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    discount_percent DECIMAL(5,2) DEFAULT 0.00,
    discount_amount DECIMAL(19,4) DEFAULT 0.0000,
    tax_id BIGINT REFERENCES tax_rates(id),
    tax_amount DECIMAL(19,4) DEFAULT 0.0000,
    line_total DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    account_id BIGINT REFERENCES accounts(id),
    line_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Bills table
CREATE TABLE bills (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    vendor_id BIGINT NOT NULL REFERENCES vendors(id),
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
    created_by_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_bills_org_number UNIQUE (organization_id, bill_number)
);

-- Bill items table
CREATE TABLE bill_items (
    id BIGSERIAL PRIMARY KEY,
    bill_id BIGINT NOT NULL REFERENCES bills(id) ON DELETE CASCADE,
    item_id BIGINT REFERENCES items(id),
    description TEXT NOT NULL,
    quantity DECIMAL(19,4) NOT NULL DEFAULT 1.0000,
    unit_price DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    discount_percent DECIMAL(5,2) DEFAULT 0.00,
    discount_amount DECIMAL(19,4) DEFAULT 0.0000,
    tax_id BIGINT REFERENCES tax_rates(id),
    tax_amount DECIMAL(19,4) DEFAULT 0.0000,
    line_total DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    account_id BIGINT REFERENCES accounts(id),
    line_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

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
