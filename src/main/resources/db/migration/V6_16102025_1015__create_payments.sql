-- Unified payments table
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    payment_type VARCHAR(50) NOT NULL,
    customer_id BIGINT REFERENCES customers(id),
    vendor_id BIGINT REFERENCES vendors(id),
    invoice_id BIGINT REFERENCES invoices(id),
    bill_id BIGINT REFERENCES bills(id),
    payment_number VARCHAR(50) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50),
    amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_payments_org_number UNIQUE (organization_id, payment_number)
);

-- Indexes
CREATE INDEX idx_payments_organization_id ON payments(organization_id);
CREATE INDEX idx_payments_customer_id ON payments(customer_id);
CREATE INDEX idx_payments_vendor_id ON payments(vendor_id);
CREATE INDEX idx_payments_invoice_id ON payments(invoice_id);
CREATE INDEX idx_payments_bill_id ON payments(bill_id);
CREATE INDEX idx_payments_date ON payments(payment_date);
