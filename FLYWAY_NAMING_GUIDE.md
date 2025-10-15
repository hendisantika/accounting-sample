# Flyway Migration Naming Convention

## Format

```
V{number}_DDMMYYYY_HHMM__description.sql
```

### Components:

- **V** - Version prefix (required by Flyway)
- **{number}** - Sequential version number (1, 2, 3, ...)
- **_DDMMYYYY_HHMM** - Date and time of creation
    - DD: Day (01-31)
    - MM: Month (01-12)
    - YYYY: Year (e.g., 2025)
    - HH: Hour in 24-hour format (00-23)
    - MM: Minute (00-59)
- **__description** - Two underscores followed by descriptive name
- **.sql** - File extension

## Examples

### Good Examples:

```
V1_16102025_0900__create_organizations_and_users.sql
V2_16102025_0915__create_chart_of_accounts.sql
V9_17102025_1430__add_customer_credit_limit.sql
V10_18102025_0900__create_expense_categories.sql
V11_18102025_1000__add_invoice_templates.sql
```

### Bad Examples:

```
❌ V1__create_tables.sql                    (missing date/time)
❌ V2_2025-10-16__create_users.sql          (wrong date format)
❌ V3_16102025_0900_create_accounts.sql     (single underscore before description)
❌ create_customers.sql                      (missing version prefix)
❌ V4_16/10/2025_0900__create_items.sql     (slashes not allowed)
```

## Rules

1. **Sequential Numbering**: Versions must be sequential (V1, V2, V3...)
2. **Unique Timestamps**: Each migration should have a unique timestamp
3. **Two Underscores**: Always use TWO underscores before the description
4. **Descriptive Names**: Use clear, descriptive names
    - Good: `add_customer_email_index`
    - Bad: `update_table`
5. **Snake Case**: Use underscores in descriptions, not spaces or hyphens
6. **Lowercase**: Keep description in lowercase
7. **SQL Files**: Always use `.sql` extension

## Creating a New Migration

### Step 1: Determine Version Number

Check the last migration file:

```bash
ls -la src/main/resources/db/migration/
# Last file: V8_16102025_1045__create_inventory_and_audit.sql
# Next version: V9
```

### Step 2: Get Current Date & Time

```bash
date +"%d%m%Y_%H%M"
# Output: 16102025_1430
```

### Step 3: Create File

```bash
touch src/main/resources/db/migration/V9_16102025_1430__add_customer_tags.sql
```

### Step 4: Write SQL

```sql
-- Add customer tags table
CREATE TABLE customer_tags
(
    id          bigserial PRIMARY KEY,
    customer_id bigint      NOT NULL REFERENCES customers (id),
    tag_name    VARCHAR(50) NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_customer_tags_customer_id ON customer_tags (customer_id);
```

### Step 5: Test

```bash
mvn spring-boot:run
# Flyway will automatically execute the new migration
```

## Current Migrations

| Version | Date       | Time  | Description                        |
|---------|------------|-------|------------------------------------|
| V1      | 16/10/2025 | 09:00 | Create organizations and users     |
| V2      | 16/10/2025 | 09:15 | Create chart of accounts           |
| V3      | 16/10/2025 | 09:30 | Create customers and vendors       |
| V4      | 16/10/2025 | 09:45 | Create items and taxes             |
| V5      | 16/10/2025 | 10:00 | Create invoices and bills          |
| V6      | 16/10/2025 | 10:15 | Create payments                    |
| V7      | 16/10/2025 | 10:30 | Create journal entries and banking |
| V8      | 16/10/2025 | 10:45 | Create inventory and audit         |

## Migration Best Practices

### DO:

- ✅ Keep migrations small and focused
- ✅ Test migrations on a separate database first
- ✅ Use transactions (Flyway wraps each migration in a transaction by default)
- ✅ Add indexes for foreign keys
- ✅ Include rollback instructions in comments if needed
- ✅ Use meaningful descriptions

### DON'T:

- ❌ Modify existing migration files after they've been run
- ❌ Delete migration files
- ❌ Skip version numbers
- ❌ Use the same timestamp for multiple migrations
- ❌ Include application-specific logic in migrations
- ❌ Mix DDL and DML in the same migration (unless necessary)

## Troubleshooting

### Migration Failed

1. Check Flyway history:

```sql
SELECT *
FROM flyway_schema_history
ORDER BY installed_rank;
```

2. If migration failed, fix the SQL and:

```bash
# Repair the Flyway metadata
mvn flyway:repair

# Or manually delete the failed entry from flyway_schema_history
DELETE FROM flyway_schema_history WHERE version = '9';
```

### Out of Order Migrations

If you need to insert a migration between existing ones:

```bash
# If last migration is V8, but you need to add between V5 and V6
# Use: V5.1_16102025_1500__description.sql
V5.1_16102025_1500__add_missing_customer_field.sql
```

Enable out-of-order execution in `application.properties`:

```properties
spring.flyway.out-of-order=true
```

## Template

```sql
-- Migration: V{n}_{DDMMYYYY}_{HHMM}__{description}
-- Description: [Brief description of what this migration does]
-- Author: [Your name]
-- Date: DD/MM/YYYY

-- ================================================
-- Up Migration
-- ================================================

-- Your SQL statements here

-- ================================================
-- Indexes
-- ================================================

-- Create necessary indexes

-- ================================================
-- Comments (Optional)
-- ================================================

-- Rollback instructions:
-- To rollback this migration, execute:
-- [Rollback SQL statements]
```

## Quick Reference

```bash
# Check current migration status
mvn flyway:info

# Validate migrations
mvn flyway:validate

# Repair failed migration
mvn flyway:repair

# Run migrations
mvn spring-boot:run
```

---

**Remember:** Once a migration has been applied to production, NEVER modify it. Create a new migration instead!
