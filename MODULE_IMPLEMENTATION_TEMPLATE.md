# Module Implementation Template

This guide provides a complete pattern for implementing each accounting module. Follow this template for consistency
across the application.

## Implementation Pattern

Each module consists of:

1. **Entity** (already created via Flyway)
2. **Repository** - Data access layer
3. **DTOs** - Request & Response objects
4. **Mapper** - Entity ↔ DTO conversion
5. **Service** - Business logic
6. **Controller** - REST API endpoints
7. **Thymeleaf Views** - UI templates

---

## Example: Customer Module Implementation

### 1. Entity (Already exists - created by Flyway V3)

The Customer entity is mapped to the `customers` table.

### 2. Repository

**Path:** `src/main/java/id/my/hendisantika/accountingsample/repository/CustomerRepository.java`

```java
package id.my.hendisantika.accountingsample.repository;

import id.my.hendisantika.accountingsample.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByOrganizationId(Long organizationId, Pageable pageable);

    List<Customer> findByOrganizationIdAndIsActiveTrue(Long organizationId);

    Optional<Customer> findByIdAndOrganizationId(Long id, Long organizationId);

    boolean existsByOrganizationIdAndCustomerCode(Long organizationId, String customerCode);

    boolean existsByOrganizationIdAndEmail(Long organizationId, String email);
}
```

### 3. DTOs

**CustomerRequestDTO.java:**

```java
package id.my.hendisantika.accountingsample.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerRequestDTO {

    @NotBlank(message = "Customer code is required")
    private String customerCode;

    @NotBlank(message = "Display name is required")
    private String displayName;

    private String companyName;
    private String firstName;
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    private String phone;
    private String mobile;
    private String website;
    private String taxId;
    private String currencyCode;
    private Integer paymentTerms;
    private BigDecimal creditLimit;

    // Billing Address
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingCity;
    private String billingState;
    private String billingPostalCode;
    private String billingCountry;

    // Shipping Address
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingState;
    private String shippingPostalCode;
    private String shippingCountry;

    private String notes;
    private Boolean isActive;
}
```

**CustomerResponseDTO.java:**

```java
package id.my.hendisantika.accountingsample.dto.customer;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CustomerResponseDTO {

    private Long id;
    private String customerCode;
    private String displayName;
    private String companyName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String mobile;
    private String website;
    private String taxId;
    private String currencyCode;
    private Integer paymentTerms;
    private BigDecimal creditLimit;
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingCity;
    private String billingState;
    private String billingPostalCode;
    private String billingCountry;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingState;
    private String shippingPostalCode;
    private String shippingCountry;
    private String notes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### 4. Mapper (MapStruct)

**Path:** `src/main/java/id/my/hendisantika/accountingsample/mapper/CustomerMapper.java`

```java
package id.my.hendisantika.accountingsample.mapper;

import id.my.hendisantika.accountingsample.dto.customer.CustomerRequestDTO;
import id.my.hendisantika.accountingsample.dto.customer.CustomerResponseDTO;
import id.my.hendisantika.accountingsample.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerResponseDTO toResponseDTO(Customer customer);

    Customer toEntity(CustomerRequestDTO dto);

    void updateEntity(CustomerRequestDTO dto, @MappingTarget Customer customer);
}
```

### 5. Service

**CustomerService.java:**

```java
package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.customer.CustomerRequestDTO;
import id.my.hendisantika.accountingsample.dto.customer.CustomerResponseDTO;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.mapper.CustomerMapper;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrganizationRepository organizationRepository;
    private final CustomerMapper customerMapper;

    public Page<CustomerResponseDTO> getAllCustomers(Long organizationId, Pageable pageable) {
        return customerRepository.findByOrganizationId(organizationId, pageable)
                .map(customerMapper::toResponseDTO);
    }

    public List<CustomerResponseDTO> getActiveCustomers(Long organizationId) {
        return customerRepository.findByOrganizationIdAndIsActiveTrue(organizationId)
                .stream()
                .map(customerMapper::toResponseDTO)
                .toList();
    }

    public CustomerResponseDTO getCustomerById(Long id, Long organizationId) {
        Customer customer = findCustomerByIdAndOrganization(id, organizationId);
        return customerMapper.toResponseDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto, Long organizationId) {
        // Validate unique constraints
        if (customerRepository.existsByOrganizationIdAndCustomerCode(organizationId, dto.getCustomerCode())) {
            throw new BusinessException("Customer code already exists");
        }

        if (customerRepository.existsByOrganizationIdAndEmail(organizationId, dto.getEmail())) {
            throw new BusinessException("Email already exists");
        }

        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization", "id", organizationId));

        Customer customer = customerMapper.toEntity(dto);
        customer.setOrganization(organization);

        if (customer.getIsActive() == null) {
            customer.setIsActive(true);
        }

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toResponseDTO(savedCustomer);
    }

    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO dto, Long organizationId) {
        Customer customer = findCustomerByIdAndOrganization(id, organizationId);

        // Check for duplicate customer code (excluding current customer)
        customerRepository.findByOrganizationIdAndCustomerCode(organizationId, dto.getCustomerCode())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BusinessException("Customer code already exists");
                    }
                });

        customerMapper.updateEntity(dto, customer);
        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.toResponseDTO(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id, Long organizationId) {
        Customer customer = findCustomerByIdAndOrganization(id, organizationId);
        // Soft delete
        customer.setIsActive(false);
        customerRepository.save(customer);
    }

    private Customer findCustomerByIdAndOrganization(Long id, Long organizationId) {
        return customerRepository.findByIdAndOrganizationId(id, organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
    }
}
```

### 6. Controller

**CustomerController.java:**

```java
package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.customer.CustomerRequestDTO;
import id.my.hendisantika.accountingsample.dto.customer.CustomerResponseDTO;
import id.my.hendisantika.accountingsample.model.User;
import id.my.hendisantika.accountingsample.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Customer management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerController {

    private the CustomerService
    customerService;

    @GetMapping
    @Operation(summary = "Get all customers (paginated)")
    public ResponseEntity<ApiResponse<Page<CustomerResponseDTO>>> getAllCustomers(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<CustomerResponseDTO> customers = customerService.getAllCustomers(
                user.getOrganization().getId(), pageable
        );
        return ResponseEntity.ok(ApiResponse.success(customers));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active customers")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getActiveCustomers(
            @AuthenticationPrincipal User user
    ) {
        List<CustomerResponseDTO> customers = customerService.getActiveCustomers(
                user.getOrganization().getId()
        );
        return ResponseEntity.ok(ApiResponse.success(customers));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomerById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        CustomerResponseDTO customer = customerService.getCustomerById(
                id, user.getOrganization().getId()
        );
        return ResponseEntity.ok(ApiResponse.success(customer));
    }

    @PostMapping
    @Operation(summary = "Create new customer")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> createCustomer(
            @Valid @RequestBody CustomerRequestDTO dto,
            @AuthenticationPrincipal User user
    ) {
        CustomerResponseDTO customer = customerService.createCustomer(
                dto, user.getOrganization().getId()
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Customer created successfully", customer));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDTO dto,
            @AuthenticationPrincipal User user
    ) {
        CustomerResponseDTO customer = customerService.updateCustomer(
                id, dto, user.getOrganization().getId()
        );
        return ResponseEntity.ok(ApiResponse.success("Customer updated successfully", customer));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete customer (soft delete)")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        customerService.deleteCustomer(id, user.getOrganization().getId());
        return ResponseEntity.ok(ApiResponse.success("Customer deleted successfully", null));
    }
}
```

### 7. Thymeleaf Views

#### Layout Template

**Path:** `src/main/resources/templates/layout/main.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title layout:title-pattern="$CONTENT_TITLE - $LAYOUT_TITLE">Accounting System</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.3.3/css/bootstrap.min.css}">
    <!-- Font Awesome -->
    <link rel="stylesheet" th:href="@{/webjars/font-awesome/6.5.2/css/all.min.css}">
    <!-- DataTables CSS -->
    <link rel="stylesheet" th:href="@{/webjars/datatables/1.13.8/css/dataTables.bootstrap5.min.css}">
    <!-- Custom CSS -->
    <link rel="stylesheet" th:href="@{/css/main.css}">

    <th:block layout:fragment="extra-css"></th:block>
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">
            <i class="fas fa-calculator"></i> Accounting System
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-item" th:href="@{/dashboard}">
                        <i class="fas fa-dashboard"></i> Dashboard
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        Sales
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" th:href="@{/customers}">Customers</a></li>
                        <li><a class="dropdown-item" th:href="@{/invoices}">Invoices</a></li>
                        <li><a class="dropdown-item" th:href="@{/payments-received}">Payments</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        Purchases
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" th:href="@{/vendors}">Vendors</a></li>
                        <li><a class="dropdown-item" th:href="@{/bills}">Bills</a></li>
                        <li><a class="dropdown-item" th:href="@{/payments-made}">Payments</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link" th:href="@{/items}">Items</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" th:href="@{/accounts}">Chart of Accounts</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        Reports
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" th:href="@{/reports/balance-sheet}">Balance Sheet</a></li>
                        <li><a class="dropdown-item" th:href="@{/reports/profit-loss}">Profit & Loss</a></li>
                        <li><a class="dropdown-item" th:href="@{/reports/cash-flow}">Cash Flow</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown" sec:authentication="name">
                        User
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" th:href="@{/profile}">Profile</a></li>
                        <li><a class="dropdown-item" th:href="@{/settings}">Settings</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" th:href="@{/logout}">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Main Content -->
<main class="container-fluid py-4">
    <div layout:fragment="content"></div>
</main>

<!-- Footer -->
<footer class="footer mt-auto py-3 bg-light">
    <div class="container text-center">
        <span class="text-muted">&copy; 2025 Accounting System. All rights reserved.</span>
    </div>
</footer>

<!-- jQuery -->
<script th:src="@{/webjars/jquery/3.7.1/jquery.min.js}"></script>
<!-- Bootstrap JS -->
<script th:src="@{/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js}"></script>
<!-- DataTables JS -->
<script th:src="@{/webjars/datatables/1.13.8/js/jquery.dataTables.min.js}"></script>
<script th:src="@{/webjars/datatables/1.13.8/js/dataTables.bootstrap5.min.js}"></script>
<!-- Chart.js -->
<script th:src="@{/webjars/chart.js/4.4.1/dist/chart.umd.js}"></script>

<th:block layout:fragment="extra-js"></th:block>
</body>
</html>
```

#### Customers List Page

**Path:** `src/main/resources/templates/customers/list.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/main}">
<head>
    <title>Customers</title>
</head>
<body>
<div layout:fragment="content">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="fas fa-users"></i> Customers</h2>
        <a href="/customers/new" class="btn btn-primary">
            <i class="fas fa-plus"></i> New Customer
        </a>
    </div>

    <!-- Filters -->
    <div class="card mb-4">
        <div class="card-body">
            <form method="get">
                <div class="row g-3">
                    <div class="col-md-4">
                        <input type="text" class="form-control" name="search" placeholder="Search customers...">
                    </div>
                    <div class="col-md-3">
                        <select class="form-select" name="status">
                            <option value="">All Status</option>
                            <option value="active">Active</option>
                            <option value="inactive">Inactive</option>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-secondary w-100">
                            <i class="fas fa-search"></i> Search
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <!-- Customers Table -->
    <div class="card">
        <div class="card-body">
            <table id="customersTable" class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Balance</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="customer : ${customers}">
                    <td th:text="${customer.customerCode}"></td>
                    <td th:text="${customer.displayName}"></td>
                    <td th:text="${customer.email}"></td>
                    <td th:text="${customer.phone}"></td>
                    <td th:text="${#numbers.formatDecimal(customer.balance, 1, 2)}"></td>
                    <td>
                        <span th:if="${customer.isActive}" class="badge bg-success">Active</span>
                        <span th:unless="${customer.isActive}" class="badge bg-secondary">Inactive</span>
                    </td>
                    <td>
                        <a th:href="@{/customers/{id}(id=${customer.id})}" class="btn btn-sm btn-info">
                            <i class="fas fa-eye"></i>
                        </a>
                        <a th:href="@{/customers/{id}/edit(id=${customer.id})}" class="btn btn-sm btn-warning">
                            <i class="fas fa-edit"></i>
                        </a>
                        <button type="button" class="btn btn-sm btn-danger"
                                th:onclick="|deleteCustomer(${customer.id})|">
                            <i class="fas fa-trash"></i>
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

<th:block layout:fragment="extra-js">
    <script>
        $(document).ready(function () {
            $('#customersTable').DataTable({
                order: [[0, 'asc']],
                pageLength: 25
            });
        });

        function deleteCustomer(id) {
            if (confirm('Are you sure you want to delete this customer?')) {
                fetch(`/api/v1/customers/${id}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('token')
                    }
                })
                        .then(response => {
                            if (response.ok) {
                                location.reload();
                            }
                        });
            }
        }
    </script>
</th:block>
</body>
</html>
```

---

## Quick Reference for All Modules

Apply the same pattern for:

- **Vendors** (similar to Customers)
- **Items** (Products/Services)
- **Invoices** (with line items)
- **Bills** (with line items)
- **Payments** (Received & Made)
- **Accounts** (Chart of Accounts)
- **Journal Entries** (with lines)
- **Bank Accounts** & **Transactions**
- **Warehouses** & **Inventory**
- **Tax Rates**

## Key Points

1. **Multi-tenancy**: Always use `organizationId` from authenticated user
2. **Security**: Use `@AuthenticationPrincipal User user` in controllers
3. **Validation**: Use `@Valid` with DTOs
4. **Soft Delete**: Set `isActive = false` instead of hard delete
5. **Pagination**: Use `Pageable` for large datasets
6. **Error Handling**: Use custom exceptions (BusinessException, ResourceNotFoundException)
7. **MapStruct**: Auto-generates mapping code at compile time
8. **Thymeleaf**: Use layout dialect for consistent UI

## Testing Pattern

For each service, create unit tests:

```java

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_Success() {
        // Arrange
        // Act
        // Assert
    }
}
```

---

Follow this template for all remaining modules!
