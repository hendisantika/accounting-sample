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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Email: hendisantika@yahoo.co.id
 * Date: 17/10/25
 * Time: 10.30
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private User currentUser;
    private User otherUser;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);

        organization = Organization.builder()
                .name("Test Org")
                .email("org@example.com")
                .country("US")
                .isActive(true)
                .build();
        organization.setId(1L);

        currentUser = User.builder()
                .organization(organization)
                .email("user@example.com")
                .passwordHash("$2a$10$encoded")
                .firstName("John")
                .lastName("Doe")
                .phone("+1234567890")
                .role(UserRole.OWNER)
                .isActive(true)
                .isEmailVerified(true)
                .build();
        currentUser.setId(1L);

        otherUser = User.builder()
                .organization(organization)
                .email("other@example.com")
                .passwordHash("$2a$10$encoded")
                .firstName("Jane")
                .lastName("Smith")
                .phone("+0987654321")
                .role(UserRole.USER)
                .isActive(true)
                .isEmailVerified(false)
                .build();
        otherUser.setId(2L);

        userRequest = new UserRequest();
        userRequest.setEmail("newuser@example.com");
        userRequest.setFirstName("New");
        userRequest.setLastName("User");
        userRequest.setPhone("+1111111111");
        userRequest.setRole(UserRole.USER);
        userRequest.setIsActive(true);

        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);
        securityUtilsMock.when(SecurityUtils::getCurrentUser).thenReturn(currentUser);
        securityUtilsMock.when(SecurityUtils::getCurrentUserId).thenReturn(1L);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should get all users for organization")
    void getAllUsers_Success() {
        // Given
        List<User> users = Arrays.asList(currentUser, otherUser);
        when(userRepository.findByOrganizationId(1L)).thenReturn(users);

        // When
        List<UserResponse> responses = userService.getAllUsers();

        // Then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getEmail()).isEqualTo("user@example.com");
        assertThat(responses.get(1).getEmail()).isEqualTo("other@example.com");

        verify(userRepository).findByOrganizationId(1L);
    }

    @Test
    @DisplayName("Should get user by ID")
    void getUserById_Success() {
        // Given
        when(userRepository.findByIdAndOrganizationId(2L, 1L)).thenReturn(Optional.of(otherUser));

        // When
        UserResponse response = userService.getUserById(2L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(2L);
        assertThat(response.getEmail()).isEqualTo("other@example.com");
        assertThat(response.getFirstName()).isEqualTo("Jane");
        assertThat(response.getLastName()).isEqualTo("Smith");

        verify(userRepository).findByIdAndOrganizationId(2L, 1L);
    }

    @Test
    @DisplayName("Should throw exception when user not found by ID")
    void getUserById_NotFound_ThrowsException() {
        // Given
        when(userRepository.findByIdAndOrganizationId(999L, 1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.getUserById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");

        verify(userRepository).findByIdAndOrganizationId(999L, 1L);
    }

    @Test
    @DisplayName("Should get current user")
    void getCurrentUser_Success() {
        // When
        UserResponse response = userService.getCurrentUser();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getEmail()).isEqualTo("user@example.com");
    }

    @Test
    @DisplayName("Should throw exception when no authenticated user")
    void getCurrentUser_NoAuthenticatedUser_ThrowsException() {
        // Given
        securityUtilsMock.when(SecurityUtils::getCurrentUser).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> userService.getCurrentUser())
                .isInstanceOf(BusinessException.class)
                .hasMessage("No authenticated user found");
    }

    @Test
    @DisplayName("Should create new user")
    void createUser_Success() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$encoded");
        when(userRepository.save(any(User.class))).thenReturn(otherUser);

        // When
        UserResponse response = userService.createUser(userRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo("other@example.com");

        verify(userRepository).existsByEmail("newuser@example.com");
        verify(organizationRepository).findById(1L);
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists during creation")
    void createUser_EmailExists_ThrowsException() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.createUser(userRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Email already registered");

        verify(userRepository).existsByEmail("newuser@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when organization not found during user creation")
    void createUser_OrganizationNotFound_ThrowsException() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.createUser(userRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Organization not found");

        verify(organizationRepository).findById(1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update user")
    void updateUser_Success() {
        // Given
        userRequest.setFirstName("Updated");
        userRequest.setLastName("Name");
        when(userRepository.findByIdAndOrganizationId(2L, 1L)).thenReturn(Optional.of(otherUser));
        when(userRepository.save(any(User.class))).thenReturn(otherUser);

        // When
        UserResponse response = userService.updateUser(2L, userRequest);

        // Then
        assertThat(response).isNotNull();
        verify(userRepository).findByIdAndOrganizationId(2L, 1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent user")
    void updateUser_NotFound_ThrowsException() {
        // Given
        when(userRepository.findByIdAndOrganizationId(999L, 1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.updateUser(999L, userRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");

        verify(userRepository).findByIdAndOrganizationId(999L, 1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update current user profile")
    void updateCurrentUser_Success() {
        // Given
        userRequest.setFirstName("Updated");
        userRequest.setLastName("Profile");
        when(userRepository.save(any(User.class))).thenReturn(currentUser);

        // When
        UserResponse response = userService.updateCurrentUser(userRequest);

        // Then
        assertThat(response).isNotNull();
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should change password successfully")
    void changePassword_Success() {
        // Given
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("oldPassword");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        when(passwordEncoder.matches("oldPassword", "$2a$10$encoded")).thenReturn(true);
        when(passwordEncoder.encode("newPassword123")).thenReturn("$2a$10$newEncoded");
        when(userRepository.save(any(User.class))).thenReturn(currentUser);

        // When
        userService.changePassword(request);

        // Then
        verify(passwordEncoder).matches("oldPassword", "$2a$10$encoded");
        verify(passwordEncoder).encode("newPassword123");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when current password is incorrect")
    void changePassword_IncorrectCurrentPassword_ThrowsException() {
        // Given
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("wrongPassword");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("newPassword123");

        when(passwordEncoder.matches("wrongPassword", "$2a$10$encoded")).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> userService.changePassword(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Current password is incorrect");

        verify(passwordEncoder).matches("wrongPassword", "$2a$10$encoded");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw exception when new password and confirmation do not match")
    void changePassword_PasswordMismatch_ThrowsException() {
        // Given
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setCurrentPassword("oldPassword");
        request.setNewPassword("newPassword123");
        request.setConfirmPassword("differentPassword");

        when(passwordEncoder.matches("oldPassword", "$2a$10$encoded")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.changePassword(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("New password and confirmation do not match");

        verify(passwordEncoder).matches("oldPassword", "$2a$10$encoded");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete user")
    void deleteUser_Success() {
        // Given
        when(userRepository.findByIdAndOrganizationId(2L, 1L)).thenReturn(Optional.of(otherUser));

        // When
        userService.deleteUser(2L);

        // Then
        verify(userRepository).findByIdAndOrganizationId(2L, 1L);
        verify(userRepository).delete(otherUser);
    }

    @Test
    @DisplayName("Should throw exception when deleting own account")
    void deleteUser_DeletingOwnAccount_ThrowsException() {
        // Given
        when(userRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(currentUser));

        // When & Then
        assertThatThrownBy(() -> userService.deleteUser(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot delete your own account");

        verify(userRepository).findByIdAndOrganizationId(1L, 1L);
        verify(userRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent user")
    void deleteUser_NotFound_ThrowsException() {
        // Given
        when(userRepository.findByIdAndOrganizationId(999L, 1L)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.deleteUser(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("User not found");

        verify(userRepository).findByIdAndOrganizationId(999L, 1L);
        verify(userRepository, never()).delete(any());
    }
}
