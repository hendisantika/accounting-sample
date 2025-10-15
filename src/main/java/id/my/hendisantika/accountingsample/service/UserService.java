package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.user.ChangePasswordRequest;
import id.my.hendisantika.accountingsample.dto.user.UserRequest;
import id.my.hendisantika.accountingsample.dto.user.UserResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.User;
import id.my.hendisantika.accountingsample.model.enums.UserRole;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.UserRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 11.50
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return userRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        User user = userRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return mapToResponse(user);
    }

    public UserResponse getCurrentUser() {
        User user = SecurityUtils.getCurrentUser();
        if (user == null) {
            throw new BusinessException("No authenticated user found");
        }
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse createUser(UserRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Generate a temporary password
        String tempPassword = generateTempPassword();

        User user = User.builder()
                .organization(organization)
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(tempPassword))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .avatarUrl(request.getAvatarUrl())
                .role(request.getRole() != null ? request.getRole() : UserRole.USER)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .isEmailVerified(false)
                .build();

        user = userRepository.save(user);

        // TODO: Send email with temporary password
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        User user = userRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update fields
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
        if (request.getRole() != null) user.setRole(request.getRole());
        if (request.getIsActive() != null) user.setIsActive(request.getIsActive());

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Transactional
    public UserResponse updateCurrentUser(UserRequest request) {
        User user = SecurityUtils.getCurrentUser();
        if (user == null) {
            throw new BusinessException("No authenticated user found");
        }

        // Update fields (excluding role and isActive for self-update)
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        User user = SecurityUtils.getCurrentUser();
        if (user == null) {
            throw new BusinessException("No authenticated user found");
        }

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
            throw new BusinessException("Current password is incorrect");
        }

        // Verify new password matches confirmation
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException("New password and confirmation do not match");
        }

        // Update password
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        User user = userRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Prevent deleting yourself
        if (user.getId().equals(SecurityUtils.getCurrentUserId())) {
            throw new BusinessException("Cannot delete your own account");
        }

        userRepository.delete(user);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .organizationId(user.getOrganization().getId())
                .organizationName(user.getOrganization().getName())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole())
                .isActive(user.getIsActive())
                .isEmailVerified(user.getIsEmailVerified())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private String generateTempPassword() {
        // Simple temp password generation - in production use a better method
        return "TempPass" + System.currentTimeMillis();
    }
}
