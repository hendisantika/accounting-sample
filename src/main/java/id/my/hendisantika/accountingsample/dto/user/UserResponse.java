package id.my.hendisantika.accountingsample.dto.user;

import id.my.hendisantika.accountingsample.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 11.35
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private Long organizationId;
    private String organizationName;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private UserRole role;
    private Boolean isActive;
    private Boolean isEmailVerified;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
