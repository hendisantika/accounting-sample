# Accounting System - Full-Featured Accounting Application

A comprehensive Spring Boot accounting application similar to Accurate and Zoho Books, featuring a Thymeleaf-based UI
and complete double-entry bookkeeping system.

## Tech Stack

### Backend

- **Java 25** with **Spring Boot 3.5.6**
- **PostgreSQL 17** - Primary database
- **Redis 7** - Caching layer
- **Flyway** - Database migrations
- **Spring Security** - Authentication & Authorization
- **JWT** - Token-based authentication
- **MapStruct** - Object mapping
- **QueryDSL** - Type-safe queries
- **iText7** - PDF generation
- **Apache POI** - Excel export
- **SpringDoc OpenAPI** - API documentation

### Frontend

- **Thymeleaf** - Server-side templating
- **Bootstrap 5.3** - UI framework
- **jQuery 3.7** - JavaScript library
- **Font Awesome 6.5** - Icons
- **Chart.js 4.4** - Data visualization
- **DataTables** - Advanced table features

## What's Implemented ✅

### 1. Core Infrastructure

- ✅ Complete project setup with all dependencies
- ✅ Docker Compose with PostgreSQL and Redis
- ✅ Flyway database migrations (8 migration files)
- ✅ Base entity classes and common utilities
- ✅ Global exception handling
- ✅ API response wrapper

### 2. Database Schema

Complete database schema with the following tables:

- Organizations & Users (multi-tenancy)
- Chart of Accounts
- Customers & Vendors
- Items/Products/Services & Tax Rates
- Invoices & Invoice Items
- Bills & Bill Items
- Payments (Received & Made)
- Journal Entries & Lines
- Bank Accounts & Transactions
- Warehouses & Inventory
- Inventory Adjustments
- Audit Logs

### 3. Security & Authentication

- ✅ JWT authentication implementation
- ✅ Custom UserDetailsService
- ✅ JWT token provider with refresh tokens
- ✅ JWT authentication filter
- ✅ Spring Security configuration
- ✅ BCrypt password encoding
- ✅ Role-based access control (OWNER, ADMIN, ACCOUNTANT, USER, VIEWER)

### 4. Authentication APIs

- ✅ POST `/api/auth/register` - Register new organization and user
- ✅ POST `/api/auth/login` - User login
- ✅ POST `/api/auth/refresh` - Refresh access token

### 5. Core Models

- ✅ Organization entity
- ✅ User entity (implements UserDetails)
- ✅ Base entity with audit fields
- ✅ Enums (UserRole, AccountType, InvoiceStatus, PaymentMethod)

## Project Structure

```
accounting-sample/
├── src/main/
│   ├── java/id/my/hendisantika/accountingsample/
│   │   ├── config/          # Spring configurations
│   │   │   └── SecurityConfig.java
│   │   ├── controller/      # REST & MVC controllers
│   │   │   └── AuthController.java
│   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── auth/        # Authentication DTOs
│   │   │   └── ApiResponse.java
│   │   ├── exception/       # Custom exceptions
│   │   │   ├── BusinessException.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── model/           # JPA entities
│   │   │   ├── enums/       # Enumerations
│   │   │   ├── BaseEntity.java
│   │   │   ├── Organization.java
│   │   │   └── User.java
│   │   ├── repository/      # JPA repositories
│   │   │   ├── OrganizationRepository.java
│   │   │   └── UserRepository.java
│   │   ├── security/        # Security components
│   │   │   ├── JwtTokenProvider.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   └── CustomUserDetailsService.java
│   │   ├── service/         # Business logic
│   │   │   └── AuthService.java
│   │   └── util/            # Utility classes
│   └── resources/
│       ├── db/migration/    # Flyway SQL migrations
│       │   ├── V1__create_organizations_and_users.sql
│       │   ├── V2__create_chart_of_accounts.sql
│       │   ├── V3__create_customers_and_vendors.sql
│       │   ├── V4__create_items_and_taxes.sql
│       │   ├── V5__create_invoices_and_bills.sql
│       │   ├── V6__create_payments.sql
│       │   ├── V7__create_journal_entries_and_banking.sql
│       │   └── V8__create_inventory_and_audit.sql
│       ├── static/          # CSS, JS, images
│       ├── templates/       # Thymeleaf templates (to be created)
│       └── application.properties
└── compose.yaml             # Docker services
```

## Getting Started

### Prerequisites

- Java 25 (or Java 21+)
- Maven 3.9+
- Docker & Docker Compose

### Installation & Running

1. **Clone the repository:**
   ```bash
   cd /Users/hendisantika/IdeaProjects/accounting-sample
   ```

2. **Start database services:**
   ```bash
   docker-compose up -d
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
    - API Base URL: `http://localhost:8080/api`
    - Swagger UI: `http://localhost:8080/api/swagger-ui.html`
    - API Docs: `http://localhost:8080/api/api-docs`

### Database Connection

- **PostgreSQL:**
    - Host: `localhost:5432`
    - Database: `accounting_db`
    - Username: `postgres`
    - Password: `postgres`

- **Redis:**
    - Host: `localhost:6379`

## API Usage

### Register New Organization

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "organizationName": "My Company",
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "password123",
    "phone": "+1234567890",
    "country": "USA"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Authenticated Request

```bash
curl -X GET http://localhost:8080/api/customers \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

## Next Steps - Implementation Guide

### Module Implementation Pattern

Each module should follow this structure:

1. **Entity** (already exists via Flyway migrations)
2. **Repository** (JpaRepository interface)
3. **DTOs** (Request & Response objects)
4. **Mapper** (MapStruct interface)
5. **Service** (Business logic)
6. **Controller** (REST endpoints)
7. **Thymeleaf Views** (HTML templates)

### Recommended Implementation Order

#### Phase 1: Core Modules

1. **Chart of Accounts** - Foundation for all transactions
2. **Customers** - Required for invoicing
3. **Vendors** - Required for bills
4. **Items/Products** - Required for line items
5. **Tax Rates** - Required for calculations

#### Phase 2: Transaction Modules

6. **Invoices** - Sales transactions
7. **Bills** - Purchase transactions
8. **Payments Received** - Customer payments
9. **Payments Made** - Vendor payments
10. **Journal Entries** - Manual entries

#### Phase 3: Advanced Features

11. **Banking & Reconciliation**
12. **Inventory Management**
13. **Financial Reports**
14. **Dashboard & Analytics**

## Key Features to Implement

### Accounting Features

- [ ] Multi-tenancy (organization-based data isolation)
- [ ] Chart of Accounts management
- [ ] Customer & Vendor management
- [ ] Product/Service catalog
- [ ] Sales Invoices with line items
- [ ] Purchase Bills
- [ ] Payment tracking (AR & AP)
- [ ] Automated journal entries
- [ ] Bank reconciliation
- [ ] Inventory tracking
- [ ] Tax calculations
- [ ] Multi-currency support
- [ ] Financial reports (Balance Sheet, P&L, Cash Flow)
- [ ] Audit trail

### UI Features (Thymeleaf)

- [ ] Responsive dashboard
- [ ] CRUD interfaces for all entities
- [ ] Interactive data tables
- [ ] Charts and visualizations
- [ ] PDF invoice generation
- [ ] Excel/CSV export
- [ ] Search and filters
- [ ] Form validation
- [ ] Modal dialogs

## Configuration

Key configuration in `application.properties`:

- Server port: `8080`
- Context path: `/api`
- Database connection
- Flyway settings
- JWT configuration
- File upload settings
- Mail settings
- Caching configuration

## Security

- JWT-based authentication
- Session-less (stateless)
- Role-based access control
- Password encryption with BCrypt
- CSRF protection disabled for API
- CORS configuration (to be added)

## Database Migrations

Flyway automatically runs migrations on application startup. Migrations are versioned and executed in order.

### Naming Convention

Format: `V{number}_DDMMYYYY_HHMM__description.sql`

Example: `V9_16102025_1100__add_customer_tags.sql`

### To Create a New Migration:

1. Create file with proper naming: `src/main/resources/db/migration/V9_16102025_1100__add_customer_tags.sql`
2. Write SQL DDL/DML statements
3. Restart application

### Existing Migrations:

- `V1_16102025_0900__create_organizations_and_users.sql`
- `V2_16102025_0915__create_chart_of_accounts.sql`
- `V3_16102025_0930__create_customers_and_vendors.sql`
- `V4_16102025_0945__create_items_and_taxes.sql`
- `V5_16102025_1000__create_invoices_and_bills.sql`
- `V6_16102025_1015__create_payments.sql`
- `V7_16102025_1030__create_journal_entries_and_banking.sql`
- `V8_16102025_1045__create_inventory_and_audit.sql`

## Development Tips

1. **Multi-tenancy:** Always filter by `organizationId`
2. **Validation:** Use `@Valid` with DTOs
3. **Exceptions:** Use custom exceptions (BusinessException, ResourceNotFoundException)
4. **Pagination:** Use Spring Data's `Pageable`
5. **Audit:** Use `@CreatedDate`, `@LastModifiedDate`
6. **Testing:** Write tests for services and controllers

## Contributing

To add a new module:

1. Create entity models
2. Create repository
3. Create DTOs and mappers
4. Implement service layer
5. Create REST controller
6. Create Thymeleaf templates
7. Add tests

## License

[Your License]

## Author

[Your Name]

## Support

For issues and questions, please open an issue in the repository.
