-- Journal entries table
CREATE TABLE journal_entries (
                                 id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 organization_id BIGINT    NOT NULL,
    journal_number VARCHAR(50) NOT NULL,
    journal_date DATE NOT NULL,
    reference_number VARCHAR(50),
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'DRAFT',
    source_type VARCHAR(50),
    source_id BIGINT,
                                 created_by_id   BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 CONSTRAINT uk_journal_entries_org_number UNIQUE (organization_id, journal_number),
                                 CONSTRAINT fk_journal_entries_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                                 CONSTRAINT fk_journal_entries_created_by FOREIGN KEY (created_by_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Journal entry lines table
CREATE TABLE journal_entry_lines (
                                     id               BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     journal_entry_id BIGINT NOT NULL,
                                     account_id       BIGINT NOT NULL,
    description TEXT,
    debit_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    credit_amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    contact_type VARCHAR(50),
                                     contact_id       BIGINT,
                                     CONSTRAINT fk_journal_entry_lines_journal FOREIGN KEY (journal_entry_id) REFERENCES journal_entries (id) ON DELETE CASCADE,
                                     CONSTRAINT fk_journal_entry_lines_account FOREIGN KEY (account_id) REFERENCES accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Bank accounts table
CREATE TABLE bank_accounts (
                               id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                               organization_id BIGINT    NOT NULL,
                               account_id      BIGINT    NOT NULL,
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
                               updated_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               CONSTRAINT fk_bank_accounts_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                               CONSTRAINT fk_bank_accounts_account FOREIGN KEY (account_id) REFERENCES accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Bank transactions table
CREATE TABLE bank_transactions (
                                   id              BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   organization_id BIGINT    NOT NULL,
                                   bank_account_id BIGINT    NOT NULL,
    transaction_date DATE NOT NULL,
    description TEXT,
    reference_number VARCHAR(50),
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(19,4) NOT NULL DEFAULT 0.0000,
    status VARCHAR(50) NOT NULL DEFAULT 'UNRECONCILED',
    is_reconciled BOOLEAN NOT NULL DEFAULT false,
                                   reconciled_at   TIMESTAMP NULL,
                                   created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   CONSTRAINT fk_bank_transactions_organization FOREIGN KEY (organization_id) REFERENCES organizations (id) ON DELETE CASCADE,
                                   CONSTRAINT fk_bank_transactions_bank_account FOREIGN KEY (bank_account_id) REFERENCES bank_accounts (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Indexes
CREATE INDEX idx_journal_entries_organization_id ON journal_entries(organization_id);
CREATE INDEX idx_journal_entries_date ON journal_entries(journal_date);
CREATE INDEX idx_journal_entry_lines_journal_id ON journal_entry_lines(journal_entry_id);
CREATE INDEX idx_journal_entry_lines_account_id ON journal_entry_lines(account_id);
CREATE INDEX idx_bank_accounts_organization_id ON bank_accounts(organization_id);
CREATE INDEX idx_bank_transactions_bank_account_id ON bank_transactions(bank_account_id);
CREATE INDEX idx_bank_transactions_date ON bank_transactions(transaction_date);
