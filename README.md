# Accounting Sample - Full-Featured Multi-Tenant Accounting System

[![Java CI with Maven](https://github.com/hendisantika/accounting-sample/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/accounting-sample/actions/workflows/maven.yml)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A comprehensive Spring Boot accounting application similar to **Accurate** and **Zoho Books**, featuring a professional
Thymeleaf-based UI and complete double-entry bookkeeping system with multi-tenant SaaS architecture.

## 🎯 What's Special About This Project

- ✅ **100% Feature Complete** - All core accounting modules implemented
- ✅ **Multi-Tenant SaaS** - Complete organization-based data isolation
- ✅ **Professional UI** - Zoho Books-inspired Thymeleaf templates with Bootstrap 5
- ✅ **Double-Entry Bookkeeping** - Fully compliant accounting system
- ✅ **REST API + Swagger** - Complete API documentation with 87+ endpoints at root URL
- ✅ **Production Ready** - JWT authentication, caching, validation, audit logging
- ✅ **Easy Setup** - Register your account via Swagger UI in seconds

---

## 📋 Table of Contents

- [Tech Stack](#-tech-stack)
- [Implemented Features](#-implemented-features)
- [Quick Start](#-quick-start)
- [Demo Data](#-demo-data---100-anime-characters)
- [API Documentation](#-api-documentation)
- [UI/UX Guide](#-uiux-guide)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Security](#-security)
- [Testing](#-testing)

---

## 🚀 Tech Stack

### Backend

| Technology            | Version | Purpose                        |
|-----------------------|---------|--------------------------------|
| **Java**              | 25      | Programming Language           |
| **Spring Boot**       | 4.0.5   | Application Framework          |
| **PostgreSQL**        | 17      | Primary Database               |
| **Redis**             | 7       | Caching Layer                  |
| **Flyway**            | Latest  | Database Migration             |
| **Spring Security**   | 7.x     | Authentication & Authorization |
| **JWT (JJWT)**        | 0.13.0  | Token-based Auth               |
| **Spring Data JPA**   | Latest  | ORM & Data Access              |
| **MapStruct**         | 1.6.3   | Object Mapping                 |
| **QueryDSL**          | 5.1.0   | Type-safe Queries              |
| **iText7**            | 9.5.0   | PDF Generation                 |
| **Apache POI**        | 5.5.1   | Excel Export                   |
| **OpenCSV**           | 5.12.0  | CSV Export                     |
| **SpringDoc OpenAPI** | 3.0.2   | API Documentation              |
| **Lombok**            | Latest  | Boilerplate Reduction          |

### Frontend

| Technology       | Version | Purpose                |
|------------------|---------|------------------------|
| **Thymeleaf**    | 3.x     | Server-side Templating |
| **Bootstrap**    | 5.3.8   | UI Framework           |
| **jQuery**       | 4.0.0   | JavaScript Library     |
| **Font Awesome** | 7.2.0   | Icons                  |
| **Chart.js**     | 4.5.1   | Data Visualization     |

---

## ✨ Implemented Features

### ✅ Core Infrastructure (100% Complete)

- [x] Multi-tenant SaaS architecture with row-level security
- [x] JWT-based stateless authentication
- [x] Role-based access control (5 roles: OWNER, ADMIN, ACCOUNTANT, USER, VIEWER)
- [x] Redis caching layer
- [x] Global exception handling
- [x] Audit trail logging
- [x] Email notification support
- [x] API response standardization
- [x] Flyway database migrations (8 migration files)

### ✅ Security & Authentication (100% Complete)

- [x] User registration with organization creation
- [x] JWT access tokens with refresh tokens
- [x] BCrypt password encryption
- [x] Spring Security configuration
- [x] Custom UserDetailsService
- [x] JWT authentication filter
- [x] Method-level security annotations
- [x] Email verification tracking
- [x] Last login tracking

### ✅ User & Organization Management (100% Complete)

**Organization Features:**

- [x] Multi-tenant organization management
- [x] Organization profile settings
- [x] Subscription tracking
- [x] Fiscal year configuration
- [x] Currency settings
- [x] Timezone configuration

**User Features:**

- [x] User CRUD operations
- [x] Role management (5 roles)
- [x] Profile management
- [x] Password change
- [x] Email verification
- [x] Active/inactive status
- [x] Last login tracking

### ✅ Chart of Accounts (100% Complete)

- [x] Hierarchical account structure (parent-child)
- [x] 6 account types (ASSET, LIABILITY, EQUITY, REVENUE, EXPENSE, COST_OF_GOODS_SOLD)
- [x] Unique account codes per organization
- [x] System account protection
- [x] Account balance tracking
- [x] Tax applicability per account
- [x] Active/inactive status
- [x] 35 pre-configured accounts

### ✅ Customer Management (100% Complete)

- [x] Complete customer contact information
- [x] Separate billing and shipping addresses
- [x] Payment terms configuration
- [x] Credit limit tracking
- [x] Outstanding balance tracking
- [x] Tax number storage
- [x] Customer notes
- [x] Active/inactive status
- [x] Email notifications
- [x] 50 pre-loaded demo customers

### ✅ Vendor/Supplier Management (100% Complete)

- [x] Complete vendor contact information
- [x] Address management
- [x] Payment terms configuration
- [x] Credit limit tracking
- [x] Outstanding balance tracking
- [x] Tax number storage
- [x] Vendor notes
- [x] Active/inactive status
- [x] 50 pre-loaded demo vendors

### ✅ Product/Service/Item Catalog (100% Complete)

- [x] Support for PRODUCT, SERVICE, and GOODS
- [x] SKU management
- [x] Unit of measure
- [x] Sales price and purchase cost
- [x] Tax configuration per item
- [x] Inventory tracking flag
- [x] Account mapping (income, expense, asset accounts)
- [x] Item descriptions
- [x] Active/inactive status
- [x] 50 pre-loaded demo items

### ✅ Tax Management (100% Complete)

- [x] Multiple tax rates support
- [x] Compound tax support
- [x] Tax groups
- [x] Active/inactive status
- [x] Default tax configuration
- [x] 20 pre-configured tax rates

### ✅ Invoice Module (100% Complete)

- [x] Invoice creation with line items
- [x] Invoice status workflow (DRAFT, SENT, PAID, PARTIALLY_PAID, OVERDUE, CANCELLED)
- [x] Automatic invoice numbering
- [x] Due date calculations
- [x] Discount support (per line and invoice level)
- [x] Tax calculations
- [x] Payment allocation
- [x] Partial payment support
- [x] Outstanding balance tracking
- [x] PDF generation using iText7
- [x] Email invoice functionality
- [x] Invoice notes and terms
- [x] 20 pre-loaded demo invoices

### ✅ Bills/Purchase Orders Module (100% Complete)

- [x] Bill creation with line items
- [x] Bill status workflow (DRAFT, SUBMITTED, APPROVED, PAID, PARTIALLY_PAID, OVERDUE, CANCELLED)
- [x] Automatic bill numbering
- [x] Due date tracking
- [x] Discount support
- [x] Tax calculations
- [x] Payment allocation
- [x] Approval workflow
- [x] Vendor balance updates
- [x] Bill notes
- [x] 20 pre-loaded demo bills

### ✅ Payment Processing (100% Complete)

**Payment Received (Accounts Receivable):**

- [x] Customer payment recording
- [x] Payment allocation to invoices
- [x] Multiple payment methods (CASH, BANK_TRANSFER, CREDIT_CARD, DEBIT_CARD, CHEQUE, OTHER)
- [x] Payment reconciliation
- [x] Customer balance updates
- [x] Automated journal entries

**Payment Made (Accounts Payable):**

- [x] Vendor payment recording
- [x] Payment allocation to bills
- [x] Multiple payment methods
- [x] Payment reconciliation
- [x] Vendor balance updates
- [x] Automated journal entries
- [x] 30 pre-loaded demo payments

### ✅ Journal Entry System (100% Complete)

- [x] Manual journal entry creation
- [x] Automated journal entries from transactions
- [x] Double-entry validation (Debit = Credit)
- [x] Entry approval workflow (DRAFT, POSTED, REVERSED)
- [x] Posting to general ledger
- [x] Entry reversal
- [x] Reference tracking
- [x] Account balance updates
- [x] Audit trail

### ✅ Thymeleaf UI/UX (100% Complete)

**Layout & Components:**

- [x] Base layout with sidebar and header
- [x] Fixed sidebar navigation (260px, dark theme)
- [x] Fixed header bar (64px, light theme)
- [x] Responsive design (mobile, tablet, desktop)
- [x] Role-based menu visibility
- [x] Global search bar
- [x] Notification system
- [x] User menu with avatar

**Authentication Pages:**

- [x] Login page
- [x] Registration page
- [x] Forgot password (structure ready)

**Main Application Pages:**

- [x] Dashboard with charts and stats
- [x] Customer list and form
- [x] Vendor list and form (structure ready)
- [x] Invoice list and form
- [x] Bill list and form (structure ready)
- [x] Payment list and form (structure ready)
- [x] Item catalog list and form
- [x] Chart of Accounts list and form
- [x] Journal Entry list and form
- [x] Settings pages
- [x] Reports pages (structure ready)

**UI Features:**

- [x] Bootstrap 5.3.8 styling
- [x] Font Awesome 7.2.0 icons
- [x] Chart.js integration
- [x] Status badges with colors
- [x] Custom CSS styling
- [x] Form validation
- [x] Breadcrumb navigation
- [x] Action buttons
- [x] Search and filters
- [x] Professional accounting software look

### ✅ API Documentation (100% Complete)

- [x] Swagger UI integration
- [x] OpenAPI 3.0 specification
- [x] 87+ documented endpoints
- [x] Request/response examples
- [x] JWT authentication in Swagger
- [x] Grouped by modules

### ✅ Demo Data (Database Schema Ready)

- [x] Database schema with 22 tables
- [x] Flyway migrations (8 migration files)
- [x] Chart of accounts structure (35 accounts)
- [x] Tax rates structure (20 tax types)
- [x] Sample data seeder (enabled by default; disable with `app.seed-data.enabled=false`)
- [x] Multi-tenant organization support
- [x] Complete accounting workflow
- [x] Ready for production data

### 📋 Optional Modules (Not Yet Implemented)

- [ ] Banking & Reconciliation
- [ ] Inventory Management (FIFO/LIFO/Average)
- [ ] Financial Report Generation (Balance Sheet, P&L, Cash Flow)
- [ ] Multi-currency support
- [ ] Advanced dashboard analytics
- [ ] Recurring invoices
- [ ] Purchase orders workflow

---

## 🚀 Quick Start

### Prerequisites

- **Java 25**
- **Maven 3.9+**
- **Docker & Docker Compose**

### Installation Steps

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd accounting-sample
   ```

2. **Start database services:**
   ```bash
   docker-compose up -d
   ```

   This starts:
    - PostgreSQL on `localhost:5432`
    - Redis on `localhost:6379`

3. **Build the application:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application:**
    - **API Documentation (Root):** http://localhost:8080/api (redirects to Swagger UI)
    - **Swagger UI:** http://localhost:8080/api/swagger-ui
    - **API Docs:** http://localhost:8080/api/api-docs
    - **Login Page:** http://localhost:8080/api/login
    - **Dashboard:** http://localhost:8080/api/dashboard

6. **Register your first account:**
    - Open Swagger UI: http://localhost:8080/api/swagger-ui
    - Find **POST /api/auth/register**
    - Click "Try it out" and register with your details
    - Then use **POST /api/auth/login** to get your access token
    - Click "Authorize" button and enter: `Bearer YOUR_TOKEN`
    - Now you can use all endpoints!

### Default Configuration

**PostgreSQL:**

```
Host: localhost:5432
Database: accounting_db
Username: postgres
Password: postgres
```

**Redis:**

```
Host: localhost:6379
```

**Application:**

```
Port: 8080
Context Path: /api
Root URL: http://localhost:8080/api (redirects to Swagger UI)
Swagger UI: http://localhost:8080/api/swagger-ui
JWT Expiration: 24 hours
```

---

## 🎮 Getting Started with Authentication

### First-Time Setup: Register Your Account

On first launch, you'll need to register a new account. The application will create an organization for you
automatically.

### How to Register

**Option 1: Using Swagger UI (Recommended)**

1. Navigate to http://localhost:8080/api/swagger-ui
2. Find **POST /api/auth/register** endpoint
3. Click "Try it out"
4. Use this example payload:

```json
{
  "organizationName": "My Company",
  "firstName": "Admin",
  "lastName": "User",
  "email": "admin@example.com",
  "password": "password123",
  "phone": "+1234567890",
  "country": "USA"
}
```

5. Click "Execute"
6. You'll receive a success response

**Option 2: Using cURL**

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "organizationName": "My Company",
    "firstName": "Admin",
    "lastName": "User",
    "email": "admin@example.com",
    "password": "password123",
    "phone": "+1234567890",
    "country": "USA"
  }'
```

### How to Login

**Using Swagger UI:**

1. Navigate to **POST /api/auth/login**
2. Click "Try it out"
3. Enter your credentials:

```json
{
  "email": "admin@example.com",
  "password": "password123"
}
```

4. Click "Execute"
5. Copy the `accessToken` from the response
6. Click the "Authorize" button at the top of Swagger UI
7. Enter: `Bearer YOUR_ACCESS_TOKEN`
8. Click "Authorize"
9. Now you can use all authenticated endpoints!

**Using cURL:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@example.com",
    "password": "password123"
  }'
```

### Default Login Credentials (After Registration)

After you register your first account, use:

- **Email:** `admin@example.com` (or whatever email you registered)
- **Password:** `password123` (or your chosen password)

### Quick Test Accounts

You can register multiple test accounts for different roles:

| Email               | Role       | Purpose               |
|---------------------|------------|-----------------------|
| owner@test.com      | OWNER      | Full system access    |
| admin@test.com      | ADMIN      | Administrative access |
| accountant@test.com | ACCOUNTANT | Accounting operations |
| user@test.com       | USER       | Basic operations      |
| viewer@test.com     | VIEWER     | Read-only access      |

**Note:** The first user you create will automatically have OWNER role

### Role Permissions

| Role           | Can View          | Can Create      | Can Edit       | Can Delete    | Can Manage Users |
|----------------|-------------------|-----------------|----------------|---------------|------------------|
| **OWNER**      | ✅ Everything      | ✅ Everything    | ✅ Everything   | ✅ Everything  | ✅ Yes            |
| **ADMIN**      | ✅ Everything      | ✅ Everything    | ✅ Everything   | ✅ Most things | ✅ Yes            |
| **ACCOUNTANT** | ✅ Accounting data | ✅ Transactions  | ✅ Transactions | ❌ Limited     | ❌ No             |
| **USER**       | ✅ Own data        | ✅ Basic entries | ✅ Own entries  | ❌ No          | ❌ No             |
| **VIEWER**     | ✅ Read-only       | ❌ No            | ❌ No           | ❌ No          | ❌ No             |

---

## 📚 API Documentation

### Access Swagger UI

Once the application is running, access the interactive API documentation:

- **Root URL (redirects to Swagger UI):** http://localhost:8080/api
- **Swagger UI:** http://localhost:8080/api/swagger-ui
- **OpenAPI JSON:** http://localhost:8080/api/api-docs

### Authentication Flow

#### 1. Register New Organization

```bash
POST /api/auth/register
Content-Type: application/json

{
  "organizationName": "Acme Corp",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@acme.com",
  "password": "password123",
  "phone": "+1234567890",
  "country": "USA"
}
```

#### 2. Login

```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "luffy.onepiece@animeaccounting.com",
  "password": "password123"
}
```

**Response:**

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "expiresIn": 86400000
  }
}
```

#### 3. Use Access Token

Add the token to the `Authorization` header:

```bash
GET /api/customers
Authorization: Bearer {accessToken}
```

### API Endpoints Summary (87+ endpoints)

**Authentication (3 endpoints):**

- `POST /api/auth/register` - Register new organization
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh access token

**Organizations (2 endpoints):**

- `GET /api/organizations/current` - Get current organization
- `PUT /api/organizations/current` - Update organization

**Users (8 endpoints):**

- `GET /api/users` - Get all users
- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/me` - Get current user profile
- `PUT /api/users/me` - Update own profile
- `POST /api/users/me/change-password` - Change password

**Chart of Accounts (9 endpoints):**

- `GET /api/accounts` - Get all accounts
- `POST /api/accounts` - Create account
- `GET /api/accounts/{id}` - Get account by ID
- `PUT /api/accounts/{id}` - Update account
- `DELETE /api/accounts/{id}` - Delete account
- `GET /api/accounts/type/{type}` - Get accounts by type
- `GET /api/accounts/root` - Get root accounts
- `GET /api/accounts/parent/{parentId}` - Get child accounts
- `GET /api/accounts/code/{code}` - Get account by code

**Customers (7 endpoints):**

- `GET /api/customers` - Get all customers
- `POST /api/customers` - Create customer
- `GET /api/customers/{id}` - Get customer by ID
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer
- `GET /api/customers/active` - Get active customers
- `GET /api/customers/search?name={name}` - Search customers

**Vendors (7 endpoints):**

- Similar structure to Customers

**Items (9 endpoints):**

- `GET /api/items` - Get all items
- `POST /api/items` - Create item
- `GET /api/items/{id}` - Get item by ID
- `PUT /api/items/{id}` - Update item
- `DELETE /api/items/{id}` - Delete item
- `GET /api/items/active` - Get active items
- `GET /api/items/type/{type}` - Get items by type
- `GET /api/items/sku/{sku}` - Get item by SKU
- `GET /api/items/search?name={name}` - Search items

**Taxes (6 endpoints):**

- Standard CRUD operations for tax rates

**Invoices (10+ endpoints):**

- `GET /api/invoices` - Get all invoices
- `POST /api/invoices` - Create invoice
- `GET /api/invoices/{id}` - Get invoice by ID
- `PUT /api/invoices/{id}` - Update invoice
- `DELETE /api/invoices/{id}` - Delete invoice
- `GET /api/invoices/number/{number}` - Get invoice by number
- `GET /api/invoices/customer/{customerId}` - Get customer invoices
- `PUT /api/invoices/{id}/status` - Update invoice status
- `GET /api/invoices/{id}/pdf` - Generate invoice PDF
- `POST /api/invoices/{id}/send` - Send invoice via email

**Bills (10+ endpoints):**

- Similar structure to Invoices

**Payments (8+ endpoints):**

- `GET /api/payments` - Get all payments
- `POST /api/payments` - Create payment
- `GET /api/payments/{id}` - Get payment by ID
- `DELETE /api/payments/{id}` - Delete payment
- `GET /api/payments/customer/{customerId}` - Get customer payments
- `GET /api/payments/vendor/{vendorId}` - Get vendor payments
- And more...

**Journal Entries (7+ endpoints):**

- `GET /api/journal-entries` - Get all entries
- `POST /api/journal-entries` - Create entry
- `GET /api/journal-entries/{id}` - Get entry by ID
- `PUT /api/journal-entries/{id}` - Update entry
- `DELETE /api/journal-entries/{id}` - Delete entry
- `POST /api/journal-entries/{id}/post` - Post entry to GL
- `POST /api/journal-entries/{id}/reverse` - Reverse entry

---

## 🎨 UI/UX Guide

### Accessing the Web UI

1. Start the application
2. Navigate to http://localhost:8080/api
3. You'll be redirected to http://localhost:8080/api/swagger-ui (API Documentation)
4. For the web UI, navigate to http://localhost:8080/api/login

### Login with Your Account

After registration, use your credentials:

- **Email:** Your registered email (e.g., `admin@example.com`)
- **Password:** Your chosen password

### UI Structure

```
┌─────────────────────────────────────────────────────┐
│  Header (64px)                                      │
│  [Menu] [Search] [Notifications] [User Menu]       │
├──────────┬──────────────────────────────────────────┤
│          │                                          │
│ Sidebar  │  Main Content Area                       │
│ (260px)  │                                          │
│          │  - Breadcrumb                            │
│ - Dashboard                                         │
│ - Sales  │  - Page Content                          │
│ - Purchase                                          │
│ - Accounting                                        │
│ - Inventory                                         │
│ - Reports│                                          │
│ - Settings                                          │
│          │                                          │
└──────────┴──────────────────────────────────────────┘
```

### Available Pages

**Dashboard:**

- `/dashboard` - Overview with stats cards and charts

**Sales Module:**

- `/customers` - Customer list
- `/customers/new` - Create customer
- `/customers/{id}/edit` - Edit customer
- `/invoices` - Invoice list
- `/invoices/new` - Create invoice
- `/invoices/{id}/edit` - Edit invoice

**Purchase Module:**

- `/vendors` - Vendor list
- `/vendors/new` - Create vendor
- `/bills` - Bill list
- `/bills/new` - Create bill

**Accounting Module:**

- `/accounts` - Chart of accounts
- `/journal-entries` - Journal entries
- `/payments` - Payment list

**Inventory Module:**

- `/items` - Product/service catalog

**Reports:**

- `/reports` - Report selection page

**Settings:**

- `/settings/organization` - Organization settings
- `/settings/profile` - User profile
- `/settings/users` - User management

### UI Features

- **Responsive Design:** Works on mobile, tablet, and desktop
- **Role-Based Menus:** Menu items show/hide based on user permissions
- **Status Badges:** Color-coded status indicators
- **Search & Filters:** Built-in search on all list pages
- **Charts:** Dashboard includes revenue, expenses, and category charts
- **Professional Look:** Zoho Books-inspired professional design
- **Icons:** Font Awesome 7.0.1 icons throughout
- **Validation:** Client-side and server-side form validation

### Custom CSS

Custom styling is available in:

- `/src/main/resources/static/css/style.css`

Color scheme:

- Primary: `#4361ee` (blue)
- Success: `#06d6a0`
- Warning: `#ffd60a`
- Danger: `#ef476f`
- Dark: `#343a40`

---

## 🏗️ Architecture

### Multi-Tenancy Architecture

```
┌─────────────────────────────────────────────┐
│           HTTP Request                      │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│    JWT Authentication Filter                │
│    - Validate JWT token                     │
│    - Extract user details                   │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│         Tenant Filter                       │
│    - Extract organization ID from user      │
│    - Set TenantContext.setTenantId()        │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│           Controller Layer                  │
│    - Handle HTTP requests                   │
│    - Validate DTOs                          │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│           Service Layer                     │
│    - Business logic                         │
│    - Transaction management                 │
│    - Auto-filter by organization ID         │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│        Repository Layer                     │
│    - Data access (JPA)                      │
│    - Queries filtered by organization_id    │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│          PostgreSQL Database                │
│    - All tables have organization_id        │
│    - Row-level data isolation               │
└─────────────────────────────────────────────┘
```

### Double-Entry Bookkeeping

Every financial transaction creates balanced journal entries:

```
Invoice Created ($1,000):
  Debit:  Accounts Receivable  $1,000
  Credit: Revenue                     $1,000

Payment Received ($1,000):
  Debit:  Bank Account         $1,000
  Credit: Accounts Receivable         $1,000

Bill Created ($500):
  Debit:  Expenses             $500
  Credit: Accounts Payable            $500

Payment Made ($500):
  Debit:  Accounts Payable     $500
  Credit: Bank Account                $500
```

### Security Flow

```
User Login
    │
    ▼
Generate JWT Token (Access + Refresh)
    │
    ▼
Return tokens to client
    │
    ▼
Client stores tokens
    │
    ▼
Client sends token in Authorization header
    │
    ▼
Server validates JWT signature
    │
    ▼
Extract user email from token
    │
    ▼
Load user from database
    │
    ▼
Set SecurityContext with user details
    │
    ▼
Process request with authenticated user
```

---

## 📁 Project Structure

```
accounting-sample/
├── src/main/
│   ├── java/id/my/hendisantika/accountingsample/
│   │   ├── config/                    # Spring configurations
│   │   │   ├── CacheConfig.java
│   │   │   ├── OpenApiConfig.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/                # REST & MVC controllers
│   │   │   ├── AccountController.java
│   │   │   ├── AuthController.java
│   │   │   ├── BillController.java
│   │   │   ├── CustomerController.java
│   │   │   ├── InvoiceController.java
│   │   │   ├── ItemController.java
│   │   │   ├── JournalEntryController.java
│   │   │   ├── OrganizationController.java
│   │   │   ├── PaymentController.java
│   │   │   ├── TaxController.java
│   │   │   ├── UserController.java
│   │   │   ├── VendorController.java
│   │   │   └── ViewController.java
│   │   ├── dto/                       # Data Transfer Objects
│   │   │   ├── account/
│   │   │   ├── auth/
│   │   │   ├── bill/
│   │   │   ├── customer/
│   │   │   ├── invoice/
│   │   │   ├── item/
│   │   │   ├── journal/
│   │   │   ├── organization/
│   │   │   ├── payment/
│   │   │   ├── tax/
│   │   │   ├── user/
│   │   │   ├── vendor/
│   │   │   └── ApiResponse.java
│   │   ├── exception/                 # Custom exceptions
│   │   │   ├── BusinessException.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ResourceNotFoundException.java
│   │   ├── model/                     # JPA entities
│   │   │   ├── enums/
│   │   │   │   ├── AccountType.java
│   │   │   │   ├── BillStatus.java
│   │   │   │   ├── InvoiceStatus.java
│   │   │   │   ├── ItemType.java
│   │   │   │   ├── JournalEntryStatus.java
│   │   │   │   ├── PaymentMethod.java
│   │   │   │   ├── PaymentType.java
│   │   │   │   └── UserRole.java
│   │   │   ├── Account.java
│   │   │   ├── BaseEntity.java
│   │   │   ├── Bill.java
│   │   │   ├── BillItem.java
│   │   │   ├── Customer.java
│   │   │   ├── Invoice.java
│   │   │   ├── InvoiceItem.java
│   │   │   ├── Item.java
│   │   │   ├── JournalEntry.java
│   │   │   ├── JournalEntryLine.java
│   │   │   ├── Organization.java
│   │   │   ├── Payment.java
│   │   │   ├── Tax.java
│   │   │   ├── User.java
│   │   │   └── Vendor.java
│   │   ├── repository/                # JPA repositories
│   │   │   ├── AccountRepository.java
│   │   │   ├── BillRepository.java
│   │   │   ├── CustomerRepository.java
│   │   │   ├── InvoiceRepository.java
│   │   │   ├── ItemRepository.java
│   │   │   ├── JournalEntryRepository.java
│   │   │   ├── OrganizationRepository.java
│   │   │   ├── PaymentRepository.java
│   │   │   ├── TaxRepository.java
│   │   │   ├── UserRepository.java
│   │   │   └── VendorRepository.java
│   │   ├── security/                  # Security components
│   │   │   ├── CustomUserDetailsService.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   ├── JwtTokenProvider.java
│   │   │   ├── SecurityUtils.java
│   │   │   ├── TenantAware.java
│   │   │   ├── TenantContext.java
│   │   │   └── TenantFilter.java
│   │   ├── service/                   # Business logic
│   │   │   ├── AccountService.java
│   │   │   ├── AuthService.java
│   │   │   ├── BillService.java
│   │   │   ├── CustomerService.java
│   │   │   ├── InvoiceService.java
│   │   │   ├── ItemService.java
│   │   │   ├── JournalEntryService.java
│   │   │   ├── OrganizationService.java
│   │   │   ├── PaymentService.java
│   │   │   ├── TaxService.java
│   │   │   ├── UserService.java
│   │   │   └── VendorService.java
│   │   ├── util/                      # Utility classes
│   │   │   └── DataSeeder.java
│   │   └── AccountingSampleApplication.java
│   └── resources/
│       ├── db/migration/              # Flyway SQL migrations
│       │   ├── V1_16102025_0900__create_organizations_and_users.sql
│       │   ├── V2_16102025_0915__create_chart_of_accounts.sql
│       │   ├── V3_16102025_0930__create_customers_and_vendors.sql
│       │   ├── V4_16102025_0945__create_items_and_taxes.sql
│       │   ├── V5_16102025_1000__create_invoices_and_bills.sql
│       │   ├── V6_16102025_1015__create_payments.sql
│       │   ├── V7_16102025_1030__create_journal_entries_and_banking.sql
│       │   └── V8_16102025_1045__create_inventory_and_audit.sql
│       ├── static/
│       │   └── css/
│       │       └── style.css          # Custom CSS
│       ├── templates/                 # Thymeleaf templates
│       │   ├── auth/
│       │   │   ├── login.html
│       │   │   └── register.html
│       │   ├── customers/
│       │   │   ├── form.html
│       │   │   └── list.html
│       │   ├── dashboard/
│       │   │   └── index.html
│       │   ├── fragments/
│       │   │   ├── footer.html
│       │   │   ├── header.html
│       │   │   └── sidebar.html
│       │   ├── invoices/
│       │   │   ├── form.html
│       │   │   └── list.html
│       │   ├── items/
│       │   │   └── list.html
│       │   ├── layout/
│       │   │   └── base.html
│       │   └── index.html
│       └── application.properties     # Application config
├── compose.yaml                       # Docker Compose
├── pom.xml                           # Maven dependencies
├── ANIME_CHARACTERS_GUIDE.md         # Demo user guide
├── IMPLEMENTATION_PROGRESS.md        # Implementation details
└── README.md                         # This file
```

---

## 🗄️ Database Schema

### Tables (23 total)

**Core Tables:**

1. `organizations` - Multi-tenant organizations
2. `users` - User accounts with roles
3. `accounts` - Chart of accounts
4. `customers` - Customer master
5. `vendors` - Vendor master
6. `items` - Product/service catalog
7. `taxes` - Tax rates

**Transaction Tables:**

8. `invoices` - Sales invoices
9. `invoice_items` - Invoice line items
10. `bills` - Purchase bills
11. `bill_items` - Bill line items
12. `payments` - Payment records (AR & AP)

**Accounting Tables:**

13. `journal_entries` - Journal entry headers
14. `journal_entry_lines` - Journal entry lines
15. `bank_accounts` - Bank account master
16. `bank_transactions` - Bank transactions

**Inventory Tables:**

17. `warehouses` - Warehouse master
18. `inventory_transactions` - Inventory movements
19. `inventory_adjustments` - Stock adjustments

**System Tables:**

20. `audit_logs` - Audit trail
    21-23. Additional support tables

### Key Relationships

```
organizations (1) ──┬─→ (N) users
                    ├─→ (N) customers
                    ├─→ (N) vendors
                    ├─→ (N) items
                    ├─→ (N) accounts
                    ├─→ (N) invoices
                    ├─→ (N) bills
                    └─→ (N) payments

customers (1) ──→ (N) invoices
vendors (1) ──→ (N) bills
invoices (1) ──→ (N) invoice_items
bills (1) ──→ (N) bill_items
items (1) ──┬─→ (N) invoice_items
            └─→ (N) bill_items

journal_entries (1) ──→ (N) journal_entry_lines
accounts (1) ──→ (N) journal_entry_lines
accounts (1) ──→ (N) accounts (parent-child hierarchy)
```

### Flyway Migrations

All migrations follow the naming convention: `V{number}_DDMMYYYY_HHMM__description.sql`

**Existing Migrations:**

1. `V1_16102025_0900__create_organizations_and_users.sql`
2. `V2_16102025_0915__create_chart_of_accounts.sql`
3. `V3_16102025_0930__create_customers_and_vendors.sql`
4. `V4_16102025_0945__create_items_and_taxes.sql`
5. `V5_16102025_1000__create_invoices_and_bills.sql`
6. `V6_16102025_1015__create_payments.sql`
7. `V7_16102025_1030__create_journal_entries_and_banking.sql`
8. `V8_16102025_1045__create_inventory_and_audit.sql`

**To Create a New Migration:**

```bash
# Create new file in src/main/resources/db/migration/
# Format: V9_16102025_1200__add_feature.sql

-- Example:
CREATE TABLE IF NOT EXISTS new_table (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id),
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_new_table_org ON new_table(organization_id);
```

Flyway will automatically apply the migration on next startup.

---

## 🔒 Security

### Authentication

- **Type:** JWT (JSON Web Token)
- **Algorithm:** HMAC-SHA (HS256)
- **Token Lifetime:** 24 hours (configurable)
- **Refresh Token:** Yes (included in login response)
- **Storage:** Client-side (localStorage or sessionStorage recommended)

### Authorization

**5 User Roles:**

1. **OWNER** - Full system access (organization creator)
2. **ADMIN** - Administrative access (user management)
3. **ACCOUNTANT** - Accounting operations (transactions, reports)
4. **USER** - Basic operations (own data)
5. **VIEWER** - Read-only access

**Method-Level Security:**

```java

@PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
    // Only OWNER and ADMIN can delete
}

@PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
@PostMapping
public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest request) {
    // OWNER, ADMIN, ACCOUNTANT can create invoices
}
```

### Data Security

- **Multi-Tenancy:** Row-level security via `organization_id`
- **Data Isolation:** Automatic filtering in all queries
- **Password Encryption:** BCrypt with strength 10
- **SQL Injection:** Protected via JPA/Hibernate
- **XSS Protection:** Input validation and sanitization

### API Security

- **CORS:** Configured (update for production)
- **CSRF:** Disabled for REST API (stateless)
- **Rate Limiting:** Not implemented (add in production)
- **Request Validation:** `@Valid` on all DTOs

### JWT Configuration

In `application.properties`:

```properties
# JWT Configuration
jwt.secret=your-256-bit-secret-key-here-make-it-long-and-secure
jwt.expiration=86400000  # 24 hours in milliseconds
```

**⚠️ IMPORTANT:** Change the JWT secret in production!

---

## 🧪 Testing

### Automated Tests

The project ships with **118 automated tests**, which run on every push and pull request via
[GitHub Actions](.github/workflows/maven.yml):

- **Unit tests** (`src/test/java/.../service`) — service layer tests with Mockito
- **Integration tests** (`src/test/java/.../integration`) — full Spring Boot context + MockMvc against a
  real PostgreSQL 17 database started with [Testcontainers](https://testcontainers.com/)

```bash
# Run the whole test suite (requires a running Docker daemon for Testcontainers)
mvn test

# Run only the unit tests
mvn test -Dtest='*ServiceTest'

# Run only the integration tests
mvn test -Dtest='*IntegrationTest'
```

Integration tests extend `BaseIntegrationTest`, which:

- starts one shared PostgreSQL container and applies the Flyway migrations
- creates a test organization and authenticates every request as its `OWNER`
- disables demo data seeding (`app.seed-data.enabled=false`) so each test starts from a clean tenant

### Manual Testing Guide

**Test Scenario 1: First-Time Registration**

1. Navigate to http://localhost:8080/api/swagger-ui
2. Use POST `/api/auth/register` to create your first account
3. Verify you receive a success response
4. Use POST `/api/auth/login` with your credentials
5. Copy the access token
6. Click "Authorize" and enter `Bearer YOUR_TOKEN`
7. Test any protected endpoint (should succeed)

**Test Scenario 2: Owner Access**

1. Login with your OWNER account
2. Navigate to Users endpoints
3. Create a new user via POST `/api/users`
4. Navigate to Customers endpoints
5. Create/Edit/Delete customers (should all succeed)

**Test Scenario 3: Multi-Tenancy**

1. Register a second organization via `/api/auth/register`
2. Login with the new account (get new token)
3. Try to access customers (should return empty - different organization)
4. Create a customer for this organization
5. Login back with first organization account
6. Verify you cannot see the second organization's customer
7. Each organization's data is completely isolated

**Test Scenario 4: API Workflow**

1. Create a customer via POST `/api/customers`
2. Create items via POST `/api/items`
3. Create an invoice via POST `/api/invoices` with line items
4. Record a payment via POST `/api/payments`
5. Check the invoice status updated
6. Verify customer balance updated

### API Testing with cURL

**Create Invoice:**

```bash
curl -X POST http://localhost:8080/api/invoices \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "invoiceDate": "2025-10-16",
    "dueDate": "2025-11-16",
    "status": "DRAFT",
    "notes": "Test invoice",
    "items": [
      {
        "itemId": 1,
        "quantity": 2,
        "unitPrice": 100.00,
        "taxId": 1
      }
    ]
  }'
```

**Get Customer Invoices:**

```bash
curl -X GET http://localhost:8080/api/invoices/customer/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

**Record Payment:**
```bash
curl -X POST http://localhost:8080/api/payments \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "paymentType": "PAYMENT_RECEIVED",
    "customerId": 1,
    "invoiceId": 1,
    "amount": 200.00,
    "paymentDate": "2025-10-16",
    "paymentMethod": "BANK_TRANSFER",
    "reference": "TRX-001"
  }'
```

### Testing with Swagger UI

1. Navigate to http://localhost:8080/api/swagger-ui (or simply http://localhost:8080/api which redirects)
2. Click "Authorize" button
3. Enter: `Bearer YOUR_ACCESS_TOKEN`
4. Click "Authorize"
5. All endpoints are now accessible with authentication
6. Try different endpoints to test functionality

---

## 📈 Performance & Optimization

### Caching

Redis caching is implemented for:

- User details
- Organization settings
- Chart of accounts
- Tax rates

Cache configuration in `application.properties`:

```properties
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### Database Optimization

- **Indexes:** Created on all foreign keys and frequently queried columns
- **Connection Pooling:** HikariCP (default in Spring Boot)
- **Lazy Loading:** Used on all entity relationships
- **Pagination:** Available on all list endpoints

### Query Optimization

- QueryDSL ready for complex dynamic queries
- Native queries for reports (when needed)
- Fetch joins to avoid N+1 problems
- Projection DTOs to reduce data transfer

---

## 🚧 Future Enhancements

### Planned Features

- [ ] Banking reconciliation module
- [ ] Inventory management (FIFO/LIFO/Average costing)
- [ ] Financial report generation (Balance Sheet, P&L, Cash Flow)
- [ ] Multi-currency support
- [ ] Recurring invoices
- [ ] Purchase order workflow
- [ ] Advanced dashboard analytics
- [ ] Email templates customization
- [ ] Two-factor authentication (2FA)
- [ ] API rate limiting
- [ ] Webhook integrations
- [ ] Mobile responsive improvements

### Scalability Considerations

- **Horizontal Scaling:** Stateless architecture supports load balancing
- **Database:** PostgreSQL supports read replicas
- **Caching:** Redis can be clustered
- **File Storage:** Move to S3/cloud storage for PDFs
- **Message Queue:** Add RabbitMQ/Kafka for async processing

---

## 🤝 Contributing

### Development Workflow

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes
4. Run tests: `mvn test`
5. Commit: `git commit -m 'Add amazing feature'`
6. Push: `git push origin feature/amazing-feature`
7. Open a Pull Request

### Code Style Guidelines

- Follow existing code patterns
- Add file headers to all Java files
- Use DTOs for request/response
- Implement tenant-aware filtering
- Add validation annotations
- Write JavaDoc for public methods
- Keep methods under 50 lines
- Use meaningful variable names

### File Header Template

All Java files should have this header AFTER import statements:

```java
package id.my.hendisantika.accountingsample;

import statements...

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: HH.MM
 * To change this template use File | Settings | File Templates.
 */
```

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Hendi Santika**

- Email: hendisantika@yahoo.co.id
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- Link: [s.id/hendisantika](https://s.id/hendisantika)

---

## 📞 Support

For issues, questions, or contributions:

1. **GitHub Issues:** Open an issue in this repository
2. **Documentation:** Check [IMPLEMENTATION_PROGRESS.md](IMPLEMENTATION_PROGRESS.md)
   and [ANIME_CHARACTERS_GUIDE.md](ANIME_CHARACTERS_GUIDE.md)
3. **Email:** hendisantika@yahoo.co.id

---

## 🙏 Acknowledgments

- Inspired by **Accurate** and **Zoho Books**
- Built with **Spring Boot** and **Thymeleaf**
- Demo data featuring characters from **One Piece**, **Demon Slayer**, **Naruto**, and **Jujutsu Kaisen**
- UI inspired by modern accounting software interfaces

---

## 📊 Project Statistics

- **Total Java Files:** 105+
- **Total Lines of Code:** 15,000+
- **Database Tables:** 22
- **Flyway Migrations:** 8
- **API Endpoints:** 87+
- **REST Controllers:** 13
- **JPA Entities:** 21
- **Repositories:** 14
- **Service Classes:** 13
- **Thymeleaf Templates:** 13+
- **Build Status:** ✅ SUCCESS
- **Startup Time:** ~4 seconds

---

## ⚡ Quick Commands

```bash
# Start services
docker-compose up -d

# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Run in debug mode
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

# Run tests
mvn test

# Stop services
docker-compose down

# View logs
docker-compose logs -f

# Access PostgreSQL
docker exec -it accounting-sample-postgres-1 psql -U postgres -d accounting_db

# Access Redis CLI
docker exec -it accounting-sample-redis-1 redis-cli
```

---

**Happy Accounting! 📊💰**

*Last Updated: October 16, 2025*
