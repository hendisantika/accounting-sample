# Accounting Sample - Implementation Progress

## Project Overview

A comprehensive Spring Boot accounting application similar to Accurate & Zoho Books, implementing full-featured
accounting software with multi-tenancy support.

**Last Updated:** October 16, 2025

---

## Technology Stack

### Backend

- **Framework:** Spring Boot 3.5.6
- **Language:** Java 25
- **Database:** PostgreSQL 17
- **Cache:** Redis 7
- **Migration:** Flyway (custom naming: V{number}_DDMMYYYY_HHMM__description.sql)
- **Security:** Spring Security + JWT (Bearer tokens)
- **Documentation:** SpringDoc OpenAPI (Swagger)

### Frontend (Planned)

- **Template Engine:** Thymeleaf with Layout Dialect
- **CSS Framework:** Bootstrap 5.3.3
- **JavaScript:** jQuery 3.7.1
- **Charts:** Chart.js 4.4.1
- **Icons:** Font Awesome 6.5.2

### Additional Libraries

- **PDF Generation:** iText7 8.0.5
- **Excel Export:** Apache POI 5.3.0
- **CSV Export:** OpenCSV 5.9
- **Object Mapping:** MapStruct 1.6.3
- **Dynamic Queries:** QueryDSL 5.1.0

---

## Completed Features (10/23)

### ✅ 1. Project Setup & Configuration

- Maven project structure
- Spring Boot dependencies configured
- Docker Compose for PostgreSQL and Redis
- Application properties configured
- Flyway migration setup with custom naming convention

### ✅ 2. Database Schema

Created 8 comprehensive Flyway migrations:

1. **V1_16102025_0900** - Organizations and Users tables
2. **V2_16102025_0915** - Chart of Accounts
3. **V3_16102025_0930** - Customers and Vendors
4. **V4_16102025_0945** - Items/Products and Taxes
5. **V5_16102025_1000** - Invoices and Bills
6. **V6_16102025_1015** - Payments (AR & AP)
7. **V7_16102025_1030** - Journal Entries and Banking
8. **V8_16102025_1045** - Inventory and Audit Trail

**Total: 23 database tables** with proper indexes, foreign keys, and constraints.

### ✅ 3. Security & Authentication

**Components:**

- `JwtTokenProvider` - Token generation and validation (HMAC-SHA)
- `JwtAuthenticationFilter` - Request filtering for JWT validation
- `CustomUserDetailsService` - User loading from database
- `SecurityConfig` - Stateless session security configuration

**API Endpoints:**

- `POST /api/auth/register` - Register new organization and owner
- `POST /api/auth/login` - User login with JWT tokens
- `POST /api/auth/refresh` - Refresh access token

**Features:**

- JWT access tokens (configurable expiration)
- Refresh tokens for seamless authentication
- Password encryption with BCrypt
- Role-based access control (OWNER, ADMIN, ACCOUNTANT, USER, VIEWER)

### ✅ 4. Multi-Tenancy Infrastructure

**Components:**

- `TenantContext` - ThreadLocal storage for current organization ID
- `TenantFilter` - Automatic tenant ID extraction from authenticated user
- `TenantAware` - Interface for tenant-aware entities
- `SecurityUtils` - Utility class for accessing current user/organization

**Features:**

- Complete data isolation between organizations
- Automatic tenant filtering on all queries
- Thread-safe context management

### ✅ 5. User & Organization Management

**Entities:**

- `Organization` - Multi-tenant organization entity
- `User` - Implements UserDetails for Spring Security

**API Endpoints:**

Organization:

- `GET /api/organizations/current` - Get current organization details
- `PUT /api/organizations/current` - Update organization (OWNER/ADMIN only)

User:

- `GET /api/users` - Get all users in organization
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/me` - Get current user profile
- `POST /api/users` - Create new user (OWNER/ADMIN only)
- `PUT /api/users/{id}` - Update user (OWNER/ADMIN only)
- `PUT /api/users/me` - Update own profile
- `POST /api/users/me/change-password` - Change password
- `DELETE /api/users/{id}` - Delete user (OWNER/ADMIN only)

**Features:**

- Complete user management with role-based permissions
- Organization profile management
- Password change functionality
- Email verification tracking
- Last login tracking

### ✅ 6. Chart of Accounts Module

**Entity:**

- `Account` - Double-entry accounting accounts with hierarchy

**Account Types:**

- ASSET
- LIABILITY
- EQUITY
- REVENUE
- EXPENSE
- COST_OF_GOODS_SOLD

**API Endpoints:**

- `GET /api/accounts` - Get all accounts
- `GET /api/accounts/type/{accountType}` - Get accounts by type
- `GET /api/accounts/root` - Get root accounts (no parents)
- `GET /api/accounts/parent/{parentId}` - Get child accounts
- `GET /api/accounts/{id}` - Get account by ID
- `GET /api/accounts/code/{code}` - Get account by code
- `POST /api/accounts` - Create account (OWNER/ADMIN/ACCOUNTANT)
- `PUT /api/accounts/{id}` - Update account (OWNER/ADMIN/ACCOUNTANT)
- `DELETE /api/accounts/{id}` - Delete account (OWNER/ADMIN only)

**Features:**

- Hierarchical account structure (parent-child relationships)
- System accounts protection (cannot be modified/deleted)
- Unique account codes per organization
- Current balance tracking
- Tax applicability flag
- Active/Inactive status

### ✅ 7. Customer Management Module

**Entity:**

- `Customer` - Customer/client management for AR

**API Endpoints:**

- `GET /api/customers` - Get all customers
- `GET /api/customers/active` - Get active customers only
- `GET /api/customers/search?name={name}` - Search customers by name
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create customer (OWNER/ADMIN/ACCOUNTANT/USER)
- `PUT /api/customers/{id}` - Update customer (OWNER/ADMIN/ACCOUNTANT/USER)
- `DELETE /api/customers/{id}` - Delete customer (OWNER/ADMIN only)

**Features:**

- Complete customer contact information
- Separate billing and shipping addresses
- Payment terms configuration
- Credit limit tracking
- Outstanding balance tracking
- Tax number storage
- Active/Inactive status
- Customer notes

### ✅ 8. Exception Handling

**Components:**

- `GlobalExceptionHandler` - Centralized exception handling
- `ResourceNotFoundException` - 404 errors
- `BusinessException` - Business logic violations

**Features:**

- Consistent error responses
- Validation error handling
- Security exception handling
- Custom business rule exceptions

### ✅ 9. Base Infrastructure

**Components:**

- `BaseEntity` - Abstract base with ID, createdAt, updatedAt
- `ApiResponse<T>` - Standardized API response wrapper
- Standard file header template applied to all Java files

### ✅ 10. Build & Configuration

**Status:**

- ✅ Project compiles successfully
- ✅ All dependencies resolved
- ✅ 48 Java source files
- ✅ No compilation errors
- ✅ Ready for testing

---

## Pending Features (13/23)

### 📋 11. Vendor/Supplier Management Module

- Vendor entity and repository
- Vendor DTOs (request/response)
- Vendor service with business logic
- Vendor REST controller
- **Similar structure to Customer module**

### 📋 12. Product/Service/Item Catalog

- Item entity (products, services, goods)
- Item categories
- Unit of measure support
- Inventory tracking flag
- Pricing management
- Tax configuration per item

### 📋 13. Tax Management

- Tax entity and rates
- Tax groups
- Tax calculations
- Tax reports

### 📋 14. Invoice Module with PDF Generation

- Invoice entity with line items
- Invoice status workflow (DRAFT → SENT → PAID)
- PDF generation using iText7
- Email invoice functionality
- Invoice numbering
- Due date calculations
- Partial payment support

### 📋 15. Bills/Purchase Orders Module

- Bill entity for vendor invoices
- Purchase order management
- Bill approval workflow
- Integration with accounts payable

### 📋 16. Payment Processing

- Payment entity for both AR and AP
- Payment allocation to invoices/bills
- Multiple payment methods support
- Payment reconciliation

### 📋 17. Journal Entry System

- Manual journal entries
- Automated entries from transactions
- Entry approval workflow
- Double-entry validation
- Posting to general ledger

### 📋 18. Banking Module

- Bank account management
- Bank transactions
- Bank reconciliation
- Statement import

### 📋 19. Inventory Management

- Stock tracking
- Stock adjustments
- Stock valuation (FIFO/LIFO/Average)
- Low stock alerts
- Stock transfer between locations

### 📋 20. Financial Reports

- Balance Sheet
- Profit & Loss Statement
- Cash Flow Statement
- Trial Balance
- General Ledger Report
- Aged Receivables
- Aged Payables
- Tax reports

### 📋 21. Thymeleaf Templates & UI

- Layout templates
- Dashboard
- Entity CRUD pages
- Report viewing
- Responsive design with Bootstrap

### 📋 22. Export Functionality

- PDF export for reports
- Excel export with Apache POI
- CSV export with OpenCSV
- Print formatting

### 📋 23. Seed Data & Demo

- Default chart of accounts
- Sample customers and vendors
- Sample products
- Demo transactions
- Admin user setup script

---

## API Documentation

Once the application is running, access:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **API Docs:** http://localhost:8080/api-docs

---

## Database Connection

**PostgreSQL (via Docker Compose):**

```
Host: localhost
Port: 5432
Database: accounting_db
Username: postgres
Password: postgres
```

**Redis (via Docker Compose):**

```
Host: localhost
Port: 6379
```

---

## Running the Application

### 1. Start Database Services

```bash
docker-compose up -d
```

### 2. Build the Application

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

### 4. Access the Application

```
REST API: http://localhost:8080/api
Swagger UI: http://localhost:8080/swagger-ui.html
```

---

## Testing the API

### 1. Register a New Organization

```bash
POST /api/auth/register
{
  "organizationName": "Acme Corp",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@acme.com",
  "password": "password123",
  "country": "USA"
}
```

### 2. Login

```bash
POST /api/auth/login
{
  "email": "john@acme.com",
  "password": "password123"
}
```

Response includes `accessToken` - use it in Authorization header as `Bearer {token}`

### 3. Create Chart of Accounts

```bash
POST /api/accounts
Authorization: Bearer {your-token}
{
  "code": "1000",
  "name": "Cash",
  "accountType": "ASSET",
  "description": "Cash in hand"
}
```

### 4. Create a Customer

```bash
POST /api/customers
Authorization: Bearer {your-token}
{
  "name": "ABC Company",
  "email": "contact@abc.com",
  "phone": "+1234567890",
  "paymentTerms": 30,
  "billingCity": "New York",
  "billingCountry": "USA"
}
```

---

## Architecture Highlights

### 1. Multi-Tenancy

- Row-level security through organization_id
- Automatic filtering via TenantFilter
- Thread-safe context management
- Complete data isolation

### 2. Security

- Stateless JWT authentication
- Role-based access control (RBAC)
- Method-level security annotations
- Password encryption with BCrypt

### 3. Double-Entry Bookkeeping

- All transactions create balanced journal entries
- Debit = Credit validation
- Account balance tracking
- Audit trail for all financial transactions

### 4. API Design

- RESTful conventions
- Consistent response format
- Comprehensive validation
- Detailed error messages

### 5. Code Quality

- Lombok for boilerplate reduction
- MapStruct for DTO mapping (ready)
- QueryDSL for dynamic queries (ready)
- Consistent file header standard

---

## Next Steps

1. **Complete Core Transaction Modules:**
    - Vendor management
    - Item catalog
    - Invoices with PDF
    - Bills/POs
    - Payments

2. **Implement Financial Reports:**
    - Balance Sheet
    - P&L Statement
    - Cash Flow
    - Trial Balance

3. **Build Thymeleaf UI:**
    - Dashboard
    - Entity management pages
    - Report viewing
    - Responsive design

4. **Add Export Features:**
    - PDF generation
    - Excel export
    - CSV export

5. **Create Seed Data:**
    - Default chart of accounts
    - Sample data for testing
    - Demo environment setup

---

## Code Statistics

- **Java Files:** 48
- **Entities:** 5 (Organization, User, Account, Customer, + BaseEntity)
- **Repositories:** 4
- **Services:** 5
- **Controllers:** 5
- **DTOs:** 14+
- **Enums:** 4
- **Flyway Migrations:** 8
- **Database Tables:** 23

---

## Contributing

To continue development:

1. Follow the existing code structure
2. Add file headers to all new Java files
3. Use the DTO pattern for request/response
4. Implement tenant-aware filtering
5. Add proper validation annotations
6. Write API documentation with Swagger
7. Test with different roles and organizations

---

## License

This is a sample project for educational purposes.
