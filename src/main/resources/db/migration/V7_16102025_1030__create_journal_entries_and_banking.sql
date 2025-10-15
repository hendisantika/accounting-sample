-- Journal entries table
CREATE TABLE journal_entries (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    journal_number VARCHAR(50) NOT NULL,
    journal_date DATE NOT NULL,
    reference_number VARCHAR(50),
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    source_type VARCHAR(50),
    source_id BIGINT,
    created_by_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_journal_entries_org_number UNIQUE (organization_id, journal_number)
);

-- Journal entry lines table
CREATE TABLE journal_entry_lines (
    id BIGSERIAL PRIMARY KEY,
    journal_entry_id BIGINT NOT NULL REFERENCES journal_entries(id) ON DELETE CASCADE,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    description TEXT,
    debit_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    credit_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    contact_type VARCHAR(50),
    contact_id BIGINT
);

-- Bank accounts table
CREATE TABLE bank_accounts (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    account_name VARCHAR(255) NOT NULL,
    account_number VARCHAR(50),
    bank_name VARCHAR(255),
    branch_name VARCHAR(255),
    swift_code VARCHAR(50),
    routing_number VARCHAR(50),
    currency_code VARCHAR(3) DEFAULT 'USD',
    opening_balance DECIMAL(19,4) DEFAULT 0.0000,
    current_balance DECIMAL(19,4) DEFAULT 0.0000,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Bank transactions table
CREATE TABLE bank_transactions (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    bank_account_id BIGINT NOT NULL REFERENCES bank_accounts(id),
    transaction_date DATE NOT NULL,
    description TEXT,
    reference_number VARCHAR(50),
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    status VARCHAR(50) NOT NULL DEFAULT 'UNRECONCILED',
    is_reconciled BOOLEAN NOT NULL DEFAULT false,
    reconciled_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Indexes
CREATE INDEX idx_journal_entries_organization_id ON journal_entries(organization_id);
CREATE INDEX idx_journal_entries_date ON journal_entries(journal_date);
CREATE INDEX idx_journal_entry_lines_journal_id ON journal_entry_lines(journal_entry_id);
CREATE INDEX idx_journal_entry_lines_account_id ON journal_entry_lines(account_id);
CREATE INDEX idx_bank_accounts_organization_id ON bank_accounts(organization_id);
CREATE INDEX idx_bank_transactions_bank_account_id ON bank_transactions(bank_account_id);
CREATE INDEX idx_bank_transactions_date ON bank_transactions(transaction_date);
