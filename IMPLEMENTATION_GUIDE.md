# Accounting System - Implementation Guide

## Overview

This is a comprehensive Spring Boot accounting application similar to Accurate and Zoho Books, with a **Thymeleaf-based
UI** and **Flyway database migrations**.

## What Has Been Implemented

### 1. Project Setup & Dependencies ✅

- Spring Boot 3.5.6 with Java 25
- Complete Maven dependencies including:
    - Spring Web, Data JPA, Security, Validation, Mail, Cache, Actuator
    - PostgreSQL driver with Liquibase for migrations
    - Redis for caching
    - JWT (jjwt 0.12.6) for authentication
    - iText7 for PDF generation
    - Apache POI for Excel export
    - OpenCSV for CSV export
    - SpringDoc OpenAPI for API documentation
    - MapStruct for DTO mapping
    - QueryDSL for dynamic queries

### 2. Database Infrastructure ✅

- **Docker Compose** configuration with PostgreSQL 17 and Redis 7
- **Complete Flyway SQL migrations** (8 migration files) for all core tables:
    - Organizations & Users (multi-tenancy foundation)
    - Chart of Accounts
    - Customers & Vendors
    - Items/Products/Services
    - Invoices & Invoice Items
    - Bills & Bill Items
    - Payments (Received & Made)
    - Journal Entries & Lines
    - Bank Accounts & Transactions
    - Inventory & Warehouses
    - Tax Rates
    - Audit Logs

### 3. Application Configuration ✅

- Comprehensive `application.properties` with:
    - Database configuration (PostgreSQL)
    - JPA/Hibernate settings
    - Flyway setup
    - Redis caching
    - JWT configuration
    - File upload settings
    - Mail configuration
    - Actuator endpoints
    - API documentation paths
    - Pagination defaults

### 4. Base Architecture ✅

- **BaseEntity**: Abstract entity with common fields (id, createdAt, updatedAt)
- **ApiResponse**: Standardized API response wrapper
- **GlobalExceptionHandler**: Centralized exception handling
- **Custom Exceptions**:
    - ResourceNotFoundException
    - BusinessException
- **Enums**:
    - UserRole (OWNER, ADMIN, ACCOUNTANT, USER, VIEWER)
    - AccountType (ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE, COST_OF_GOODS_SOLD)
    - InvoiceStatus (DRAFT, SENT, PARTIALLY_PAID, PAID, OVERDUE, VOID, CANCELLED)
    - PaymentMethod (CASH, BANK_TRANSFER, CREDIT_CARD, etc.)

### 5. Core Entities ✅

- **Organization**: Multi-tenant foundation with company details
- **User**: UserDetails implementation with role-based access

### 6. Authentication & Security ✅

- **JWT Authentication**: Complete token-based auth system
- **JwtTokenProvider**: Token generation and validation
- **JwtAuthenticationFilter**: Request filtering
- **CustomUserDetailsService**: User loading
- **SecurityConfig**: Spring Security configuration
- **AuthService**: Registration, login, refresh token
- **AuthController**: REST endpoints for authentication

### 7. Dependencies ✅

- Spring Boot 3.5.6 with Web, Security, Data JPA
- Thymeleaf with Layout Dialect
- Bootstrap 5.3, jQuery 3.7, Font Awesome 6.5
- Chart.js 4.4, DataTables 1.13
- PDF (iText7), Excel (Apache POI), CSV (OpenCSV)
- MapStruct, QueryDSL for advanced queries

## Architecture

```
accounting-sample/
├── src/main/java/id/my/hendisantika/accountingsample/
│   ├── model/          # Entity classes
│   │   ├── enums/      # Enums for status, types, etc.
│   │   └── BaseEntity  # Base class for all entities
│   ├── repository/     # JPA repositories
│   ├── service/        # Business logic
│   ├── controller/     # REST controllers
│   ├── dto/            # Data Transfer Objects
│   ├── config/         # Spring configurations
│   ├── security/       # JWT, filters, etc.
│   ├── exception/      # Custom exceptions & handlers
│   └── util/           # Utility classes
├── src/main/resources/
│   ├── db/changelog/   # Liquibase migrations
│   └── application.properties
└── compose.yaml        # Docker services
```

## Next Steps to Continue Development

### Phase 1: Core Module Implementation (Priority 1)

Follow the **MODULE_IMPLEMENTATION_TEMPLATE.md** for detailed patterns.

**Template provides complete examples for:**

- Repository (data access)
- DTOs (Request/Response)
- Mapper (MapStruct)
- Service (business logic)
- Controller (REST API)
- Thymeleaf views (UI)

**Implement modules in this order:**

### Phase 2: Core Modules (Priority 2)

Each module should follow this pattern:

- Entity (already created via Liquibase)
- Repository (JpaRepository)
- DTO classes (Request & Response)
- Mapper (MapStruct)
- Service (Interface & Implementation)
- Controller (REST endpoints)

**Recommended order:**

1. Organization & User Management
2. Chart of Accounts
3. Customers
4. Vendors
5. Items/Products
6. Invoices
7. Bills
8. Payments
9. Journal Entries
10. Banking & Reconciliation
11. Inventory Management
12. Tax Management

### Phase 3: Financial Reports (Priority 3)

1. Balance Sheet
2. Profit & Loss (Income Statement)
3. Cash Flow Statement
4. Trial Balance
5. General Ledger Report
6. Aged Receivables/Payables

### Phase 4: Advanced Features (Priority 4)

1. PDF Generation for invoices/reports
2. Excel/CSV Export
3. Email Notifications
4. File Uploads
5. Multi-currency support
6. Audit logging
7. Dashboard analytics

### Phase 5: Thymeleaf UI (Priority 5)

1. Create layout templates (main, sidebar, navbar)
2. Implement list/table views for all modules
3. Create form pages (create/edit)
4. Detail/view pages
5. Dashboard with charts (Chart.js)
6. Integrate DataTables for advanced tables
7. Add modal dialogs
8. Responsive design with Bootstrap
9. Form validation (client-side + server-side)

## How to Run

### Prerequisites

- Java 25
- Maven
- Docker & Docker Compose

### Steps

1. **Start the database services:**
   ```bash
   docker-compose up -d
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
    - API: http://localhost:8080/api
    - Swagger UI: http://localhost:8080/api/swagger-ui.html
    - API Docs: http://localhost:8080/api/api-docs

### Database Access

- **PostgreSQL:**
    - Host: localhost:5432
    - Database: accounting_db
    - Username: postgres
    - Password: postgres

- **Redis:**
    - Host: localhost:6379

## Development Guidelines

### Coding Standards

1. Use Lombok to reduce boilerplate
2. Follow REST API best practices
3. Implement proper validation with @Valid
4. Use DTOs for API requests/responses
5. Implement service layer for business logic
6. Write meaningful exception messages
7. Add comprehensive logging
8. Write unit tests for services
9. Write integration tests for controllers

### API Design Pattern

```java
// Controller example
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerDTO>>> getAllCustomers(Pageable pageable) {
        // Implementation
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        // Implementation
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDTO>> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequestDTO dto) {
        // Implementation
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long id) {
        // Implementation
    }
}
```

### Multi-Tenancy Pattern

Every request should filter by the current user's organization:

```java

@Service
public class CustomerService {

    public List<Customer> findAllByOrganization(Long organizationId) {
        return customerRepository.findByOrganizationId(organizationId);
    }
}
```

## Feature Checklist

### Core Accounting Features

- [ ] Multi-tenancy & Organizations
- [ ] User Management & Authentication
- [ ] Chart of Accounts
- [ ] General Ledger
- [ ] Journal Entries
- [ ] Customers
- [ ] Vendors
- [ ] Items/Products/Services
- [ ] Invoicing
- [ ] Bills/Purchase Orders
- [ ] Payments (AR & AP)
- [ ] Banking & Reconciliation
- [ ] Inventory Management
- [ ] Tax Management
- [ ] Financial Reports
- [ ] Multi-currency Support
- [ ] Audit Logging

### Additional Features

- [ ] PDF Generation
- [ ] Excel/CSV Export
- [ ] Email Notifications
- [ ] File Attachments
- [ ] Dashboard Analytics
- [ ] Budget Management
- [ ] Recurring Invoices
- [ ] Payment Reminders
- [ ] Bank Feeds Integration
- [ ] Mobile Responsive UI

## Technology Stack

### Backend

- Java 25
- Spring Boot 3.5.6
- Spring Security with JWT
- Spring Data JPA
- PostgreSQL 17
- Redis 7
- Liquibase
- MapStruct
- QueryDSL
- iText7 (PDF)
- Apache POI (Excel)
- SpringDoc OpenAPI

### Frontend (To Be Implemented)

- React 18+ with TypeScript
- React Router
- Redux Toolkit or Zustand
- Material-UI or Ant Design
- Axios
- Chart.js or Recharts
- Formik + Yup
- date-fns

## Contributing

When implementing new features:

1. Create entity models
2. Create repositories
3. Create DTOs (Request/Response)
4. Create mappers
5. Implement services
6. Create controllers
7. Add tests
8. Update API documentation

## License

[Your License]

## Contact

[Your Contact Information]
