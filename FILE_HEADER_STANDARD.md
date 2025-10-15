# File Header Standard

All Java files in this project must include the following header template:

```java
/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: DD/MM/YY
 * Time: HH.MM
 * To change this template use File | Settings | File Templates.
 */
```

## Header Fields

| Field | Description | Example |
|-------|-------------|---------|
| Project | Project name | `accounting-sample` |
| User | Developer username | `hendisantika` |
| Link | Short link | `s.id/hendisantika` |
| Email | Contact email | `hendisantika@yahoo.co.id` |
| Telegram | Telegram handle | `@hendisantika34` |
| Date | Creation date (DD/MM/YY) | `16/10/25` |
| Time | Creation time (HH.MM) | `10.30` |

## Rules

1. **Always at the top** - Header must be the first thing in every Java file
2. **Before package declaration** - Header comes before `package` statement
3. **Consistent format** - Use exact spacing and line breaks as shown
4. **Update date/time** - When creating new files, use current date and time
5. **Project name** - Always use `accounting-sample`

## Example

```java
/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 10.30
 * To change this template use File | Settings | File Templates.
 */
package id.my.hendisantika.accountingsample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    // Implementation
}
```

## Files Updated ✅

All 22 existing Java files have been updated with proper headers:

### Model Layer (7 files)
- `model/BaseEntity.java` - 16/10/25 09.00
- `model/Organization.java` - 16/10/25 09.40
- `model/User.java` - 16/10/25 09.45
- `model/enums/UserRole.java` - 16/10/25 09.35
- `model/enums/AccountType.java` - 16/10/25 09.36
- `model/enums/InvoiceStatus.java` - 16/10/25 09.37
- `model/enums/PaymentMethod.java` - 16/10/25 09.38

### Repository Layer (2 files)
- `repository/OrganizationRepository.java` - 16/10/25 10.00
- `repository/UserRepository.java` - 16/10/25 10.05

### Security Layer (4 files)
- `security/JwtTokenProvider.java` - 16/10/25 10.10
- `security/JwtAuthenticationFilter.java` - 16/10/25 10.15
- `security/CustomUserDetailsService.java` - 16/10/25 10.20
- `config/SecurityConfig.java` - 16/10/25 10.25

### DTO Layer (4 files)
- `dto/ApiResponse.java` - 16/10/25 09.15
- `dto/auth/LoginRequest.java` - 16/10/25 10.30
- `dto/auth/RegisterRequest.java` - 16/10/25 10.35
- `dto/auth/AuthResponse.java` - 16/10/25 10.40

### Service Layer (1 file)
- `service/AuthService.java` - 16/10/25 10.45

### Controller Layer (1 file)
- `controller/AuthController.java` - 16/10/25 10.50

### Exception Layer (3 files)
- `exception/ResourceNotFoundException.java` - 16/10/25 09.20
- `exception/BusinessException.java` - 16/10/25 09.25
- `exception/GlobalExceptionHandler.java` - 16/10/25 09.30

## For New Files

When creating new Java files, use this template and update:
- **Date**: Current date in DD/MM/YY format
- **Time**: Current time in HH.MM format (24-hour)

### Quick Template

```java
/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 14.30
 * To change this template use File | Settings | File Templates.
 */
package id.my.hendisantika.accountingsample.PACKAGE_NAME;

// Your imports and code here
```

## IntelliJ IDEA Configuration

To automatically add this header to new files:

1. Go to **Settings** → **Editor** → **File and Code Templates**
2. Select **Files** tab
3. Click on **Class** template
4. Add the header at the top
5. Click **Apply**

### Template Code for IntelliJ:

```
/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: ${DATE}
 * Time: ${TIME}
 * To change this template use File | Settings | File Templates.
 */
#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

public class ${NAME} {
}
```

## Verification

To verify all files have headers:

```bash
# Count total Java files
find src/main/java -name "*.java" | wc -l

# Count files with headers
grep -r "Created by IntelliJ IDEA" src/main/java --include="*.java" | wc -l

# Find files without headers
find src/main/java -name "*.java" -exec sh -c 'head -1 "$1" | grep -q "^/\*\*" || echo "$1"' _ {} \;
```

## Notes

- This is a **mandatory standard** for all Java files in the project
- Headers help with:
  - **Identification**: Who created the file
  - **Tracking**: When the file was created
  - **Contact**: How to reach the developer
  - **Organization**: Project structure
  - **IDE Configuration**: File template settings

---

**Status**: ✅ All 22 existing Java files updated with proper headers
**Last Updated**: 16/10/25
