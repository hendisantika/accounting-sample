-- Payments received table
CREATE TABLE payments_received (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    customer_id BIGINT NOT NULL REFERENCES customers(id),
    payment_number VARCHAR(50) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50),
    amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    currency_code VARCHAR(3) DEFAULT 'USD',
    exchange_rate DECIMAL(19,6) DEFAULT 1.000000,
    deposit_to_account_id BIGINT NOT NULL REFERENCES accounts(id),
    notes TEXT,
    created_by_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_payments_received_org_number UNIQUE (organization_id, payment_number)
);

-- Payment received invoices mapping
CREATE TABLE payment_received_invoices (
    id BIGSERIAL PRIMARY KEY,
    payment_received_id BIGINT NOT NULL REFERENCES payments_received(id) ON DELETE CASCADE,
    invoice_id BIGINT NOT NULL REFERENCES invoices(id),
    amount_applied DECIMAL(19,4) NOT NULL DEFAULT 0.0000
);

-- Payments made table
CREATE TABLE payments_made (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    vendor_id BIGINT NOT NULL REFERENCES vendors(id),
    payment_number VARCHAR(50) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50),
    amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    currency_code VARCHAR(3) DEFAULT 'USD',
    exchange_rate DECIMAL(19,6) DEFAULT 1.000000,
    paid_from_account_id BIGINT NOT NULL REFERENCES accounts(id),
    notes TEXT,
    created_by_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_payments_made_org_number UNIQUE (organization_id, payment_number)
);

-- Payment made bills mapping
CREATE TABLE payment_made_bills (
    id BIGSERIAL PRIMARY KEY,
    payment_made_id BIGINT NOT NULL REFERENCES payments_made(id) ON DELETE CASCADE,
    bill_id BIGINT NOT NULL REFERENCES bills(id),
    amount_applied DECIMAL(19,4) NOT NULL DEFAULT 0.0000
);

-- Indexes
CREATE INDEX idx_payments_received_organization_id ON payments_received(organization_id);
CREATE INDEX idx_payments_received_customer_id ON payments_received(customer_id);
CREATE INDEX idx_payments_made_organization_id ON payments_made(organization_id);
CREATE INDEX idx_payments_made_vendor_id ON payments_made(vendor_id);
