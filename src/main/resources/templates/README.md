# Thymeleaf Layout Templates - Documentation

## Overview

This directory contains comprehensive Thymeleaf layout templates for the accounting-sample project with a Zoho
Books-like UI design. The templates are built using Bootstrap 5, Font Awesome icons, and Thymeleaf Layout Dialect.

## Directory Structure

```
templates/
├── layout/
│   └── base.html              # Base layout template
├── fragments/
│   ├── header.html            # Header fragment
│   ├── sidebar.html           # Sidebar navigation fragment
│   └── footer.html            # Footer fragment
├── index.html                 # Sample dashboard page
└── README.md                  # This file
```

## File Descriptions

### 1. layout/base.html

The main base layout template that provides the overall structure for all pages.

**Features:**

- HTML5 structure with responsive design
- Bootstrap 5 CSS from webjars
- Font Awesome 7.0.1 icons from webjars
- jQuery 3.7.1 from webjars
- Fixed sidebar navigation (250px width)
- Fixed header (60px height)
- Main content area with padding
- Footer at the bottom
- Custom CSS variables for easy theming
- Mobile-responsive with collapsible sidebar
- Custom scrollbar styling
- JavaScript for menu interactions

**Layout Fragments:**

- `css` - For page-specific CSS
- `breadcrumb` - For breadcrumb navigation
- `content` - For main page content
- `scripts` - For page-specific JavaScript

**Color Variables:**

```css
--primary-color: #4361ee
--secondary-color: #3f37c9
--success-color: #4cc9f0
--danger-color: #f72585
--warning-color: #ffd60a
--dark-color: #2b2d42
```

### 2. fragments/header.html

Header component with top navigation bar.

**Features:**

- Mobile menu toggle button
- Global search bar (400px on desktop, 200px on mobile)
- Notification dropdown with badge counter
- Quick actions dropdown (role-based with Spring Security)
    - Sales actions (New Invoice, Customer, Payment)
    - Purchase actions (New Bill, Vendor)
    - Accounting actions (New Journal Entry, Account)
- User menu dropdown with:
    - User avatar (shows first letter of username)
    - Username and role display
    - Profile link
    - Organization settings
    - System settings (admin only)
    - Help & documentation
    - Logout form

**Spring Security Integration:**

- Uses `sec:authorize` for role-based menu items
- Supports roles: ADMIN, ACCOUNTANT, SALES, PURCHASE, USER
- Displays current user information from authentication principal

### 3. fragments/sidebar.html

Left sidebar navigation with hierarchical menu structure.

**Features:**

- Logo area at top
- Collapsible menu sections
- Role-based menu items
- Menu categories:
    - **Dashboard** - Home page link
    - **Sales** - Customers, Invoices (with submenu), Sales Orders, Payments Received, Credit Notes
    - **Purchases** - Vendors, Bills (with submenu), Purchase Orders, Payments Made, Vendor Credits
    - **Accounting** - Chart of Accounts, Journal Entries, Banking (with submenu), Taxes
    - **Inventory** - Items (with submenu), Adjustments
    - **Reports** - Various financial reports (P&L, Balance Sheet, Cash Flow, etc.)
    - **Configuration** - Settings (admin only)
    - **Help** - Help Center, Documentation

**Submenu Items:**

- Invoices: All Invoices, New Invoice, Recurring, Overdue
- Bills: All Bills, New Bill, Recurring
- Banking: Bank Accounts, Transactions, Reconciliation
- Items: All Items, New Item, Categories
- Settings: Organization, Users & Roles, Preferences, Templates, etc.

**Interactive Features:**

- Expandable/collapsible submenus
- Active menu item highlighting
- Smooth transitions and hover effects
- Dropdown icons that rotate on expand

### 4. fragments/footer.html

Footer component with copyright and links.

**Three Footer Variations:**

**Default Footer (`footer`):**

- Simple two-column layout
- Copyright with dynamic year
- Version number
- Quick links: Privacy Policy, Terms of Service, Contact Us

**Detailed Footer (`footer-detailed`):**

- Multi-column layout with sections:
    - Company info and social links
    - Product links
    - Resources
    - Company information
    - Legal links
- Social media icons (Facebook, Twitter, LinkedIn, GitHub)

**Minimal Footer (`footer-minimal`):**

- Single line with copyright only

## How to Use

### Creating a New Page

1. **Create a new HTML file** in the templates directory:

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
      layout:decorate="~{layout/base}">
<head>
    <title>Page Title</title>

    <!-- Optional: Page Specific CSS -->
    <th:block layout:fragment="css">
        <style>
            /* Your custom CSS here */
        </style>
    </th:block>
</head>
<body>
<!-- Optional: Custom Breadcrumb -->
<nav layout:fragment="breadcrumb" aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><a th:href="@{/}">Home</a></li>
        <li class="breadcrumb-item active" aria-current="page">Page Title</li>
    </ol>
</nav>

<!-- Main Content -->
<div layout:fragment="content">
    <!-- Your page content here -->
    <h1>Hello World</h1>
</div>

<!-- Optional: Page Specific JavaScript -->
<th:block layout:fragment="scripts">
    <script>
        // Your custom JavaScript here
    </script>
</th:block>
</body>
</html>
```

2. **Use Thymeleaf URL syntax** for all links:

```html
<a th:href="@{/invoices}">Invoices</a>
<a th:href="@{/customers/new}">New Customer</a>
```

3. **Display dynamic data** from the controller:

```html
<h1 th:text="${pageTitle}">Default Title</h1>
<p th:text="${message}">Default message</p>
```

### Controller Example

```java

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        // Add data to model
        model.addAttribute("unreadNotifications", 5);

        // Return view name (without .html extension)
        return "index";
    }
}
```

### Using Different Footer Styles

In your base.html, you can change the footer fragment:

```html
<!-- Default footer -->
<div th:replace="~{fragments/footer :: footer}"></div>

<!-- Detailed footer -->
<div th:replace="~{fragments/footer :: footer-detailed}"></div>

<!-- Minimal footer -->
<div th:replace="~{fragments/footer :: footer-minimal}"></div>
```

## Security Integration

The templates use Spring Security's Thymeleaf extras for role-based access control.

**Available Roles:**

- `ROLE_ADMIN` - Full system access
- `ROLE_ACCOUNTANT` - Accounting and financial operations
- `ROLE_SALES` - Sales operations (customers, invoices)
- `ROLE_PURCHASE` - Purchase operations (vendors, bills)
- `ROLE_USER` - Basic user access

**Example Usage:**

```html
<!-- Show only to admins -->
<div sec:authorize="hasRole('ADMIN')">
    Admin only content
</div>

<!-- Show to multiple roles -->
<div sec:authorize="hasAnyAuthority('ROLE_ADMIN', 'ROLE_ACCOUNTANT')">
    Admin and Accountant content
</div>

<!-- Hide from specific roles -->
<div sec:authorize="!hasRole('USER')">
    Not visible to basic users
</div>

<!-- Check if user is authenticated -->
<div sec:authorize="isAuthenticated()">
    Logged in user content
</div>
```

## Responsive Design

The templates are fully responsive with the following breakpoints:

- **Desktop**: > 768px - Full sidebar visible
- **Mobile**: ≤ 768px - Sidebar hidden, toggle button visible

**Mobile Features:**

- Hamburger menu toggle
- Collapsible sidebar
- Responsive search bar
- Stacked footer columns
- Touch-friendly buttons

## Customization

### Changing Colors

Edit the CSS variables in `layout/base.html`:

```css
:root {
    --primary-color: #4361ee; /* Change to your brand color */
    --secondary-color: #3f37c9;
    --success-color: #4cc9f0;
    --danger-color: #f72585;
    /* ... */
}
```

### Adding New Menu Items

Edit `fragments/sidebar.html` and add your menu item:

```html

<div class="menu-item">
    <a th:href="@{/your-path}" class="menu-link">
        <i class="fas fa-your-icon"></i>
        <span>Your Menu Item</span>
    </a>
</div>
```

### Adding Submenus

```html

<div class="menu-item">
    <a href="#" class="menu-link" data-toggle="submenu">
        <i class="fas fa-folder"></i>
        <span>Parent Menu</span>
        <i class="fas fa-chevron-right dropdown-icon"></i>
    </a>
    <div class="submenu">
        <a th:href="@{/child-1}" class="menu-link">
            <i class="fas fa-file"></i>
            <span>Child Item 1</span>
        </a>
        <a th:href="@{/child-2}" class="menu-link">
            <i class="fas fa-file"></i>
            <span>Child Item 2</span>
        </a>
    </div>
</div>
```

## Dependencies

Ensure these dependencies are in your `pom.xml`:

```xml
<!-- Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

        <!-- Thymeleaf Layout Dialect -->
<dependency>
<groupId>nz.net.ultraq.thymeleaf</groupId>
<artifactId>thymeleaf-layout-dialect</artifactId>
</dependency>

        <!-- Thymeleaf Spring Security -->
<dependency>
<groupId>org.thymeleaf.extras</groupId>
<artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>

        <!-- WebJars -->
<dependency>
<groupId>org.webjars</groupId>
<artifactId>bootstrap</artifactId>
<version>5.3.8</version>
</dependency>
<dependency>
<groupId>org.webjars</groupId>
<artifactId>jquery</artifactId>
<version>3.7.1</version>
</dependency>
<dependency>
<groupId>org.webjars</groupId>
<artifactId>font-awesome</artifactId>
<version>7.0.1</version>
</dependency>
```

## WebJar URLs

The templates use WebJars for client-side dependencies. They are automatically served at:

- Bootstrap CSS: `/webjars/bootstrap/5.3.8/css/bootstrap.min.css`
- Bootstrap JS: `/webjars/bootstrap/5.3.8/js/bootstrap.bundle.min.js`
- jQuery: `/webjars/jquery/3.7.1/jquery.min.js`
- Font Awesome: `/webjars/font-awesome/7.0.1/css/all.min.css`

No additional configuration needed if `spring-boot-starter-web` is in your classpath.

## JavaScript Functionality

The templates include jQuery-based JavaScript for:

1. **Submenu Toggle**: Click on menu items with `data-toggle="submenu"`
2. **Mobile Sidebar**: Toggle sidebar on mobile devices
3. **Active Menu Detection**: Automatically highlights current page in menu
4. **Dropdown Menus**: Bootstrap-based dropdowns for user menu and notifications

## Best Practices

1. **Always use `th:href`** instead of plain `href` for internal links
2. **Use `@{/path}` syntax** for URL construction
3. **Add role-based security** using `sec:authorize` where needed
4. **Keep fragments small** and reusable
5. **Use layout:fragment** for customizable sections
6. **Test responsive design** on mobile devices
7. **Follow Bootstrap conventions** for consistent styling

## Example Pages

- **index.html** - Dashboard with statistics cards, charts, and tables
- This serves as a reference implementation

## Support

For more information:

- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Thymeleaf Layout Dialect](https://github.com/ultraq/thymeleaf-layout-dialect)
- [Bootstrap 5 Documentation](https://getbootstrap.com/docs/5.3/)
- [Font Awesome Icons](https://fontawesome.com/icons)

## License

These templates are part of the accounting-sample project.
