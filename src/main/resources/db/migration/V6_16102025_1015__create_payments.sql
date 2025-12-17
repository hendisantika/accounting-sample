-- Unified payments table
CREATE TABLE payments (
                          id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                          organization_id BIGINT    NOT NULL,
    payment_type VARCHAR(50) NOT NULL,
                          customer_id     BIGINT,
                          vendor_id       BIGINT,
                          invoice_id      BIGINT,
                          bill_id         BIGINT,
    payment_number VARCHAR(50) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    reference_number VARCHAR(50),
    amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
                          account_id      BIGINT    NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT uk_payments_org_number UNIQUE (organization_id, payment_number),
                          CONSTRAINT fk_payments_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                          CONSTRAINT fk_payments_customer FOREIGN KEY (customer_id) REFERENCES customers (id),
                          CONSTRAINT fk_payments_vendor FOREIGN KEY (vendor_id) REFERENCES vendors (id),
                          CONSTRAINT fk_payments_invoice FOREIGN KEY (invoice_id) REFERENCES invoices (id),
                          CONSTRAINT fk_payments_bill FOREIGN KEY (bill_id) REFERENCES bills (id),
                          CONSTRAINT fk_payments_account FOREIGN KEY (account_id) REFERENCES accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes
CREATE INDEX idx_payments_organization_id ON payments(organization_id);
CREATE INDEX idx_payments_customer_id ON payments(customer_id);
CREATE INDEX idx_payments_vendor_id ON payments(vendor_id);
CREATE INDEX idx_payments_invoice_id ON payments(invoice_id);
CREATE INDEX idx_payments_bill_id ON payments(bill_id);
CREATE INDEX idx_payments_date ON payments(payment_date);
