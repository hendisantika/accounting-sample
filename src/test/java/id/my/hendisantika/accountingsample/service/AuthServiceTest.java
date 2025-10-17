package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.auth.AuthResponse;
import id.my.hendisantika.accountingsample.dto.auth.LoginRequest;
import id.my.hendisantika.accountingsample.dto.auth.RegisterRequest;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.User;
import id.my.hendisantika.accountingsample.model.enums.UserRole;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.UserRepository;
import id.my.hendisantika.accountingsample.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Email: hendisantika@yahoo.co.id
 * Date: 17/10/25
 * Time: 10.00
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private Organization organization;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setPhone("+1234567890");
        registerRequest.setOrganizationName("Test Org");
        registerRequest.setCountry("US");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        organization = Organization.builder()
                .name("Test Org")
                .email("test@example.com")
                .country("US")
                .isActive(true)
                .build();
        organization.setId(1L);

        user = User.builder()
                .organization(organization)
                .email("test@example.com")
                .passwordHash("$2a$10$encoded")
                .firstName("John")
                .lastName("Doe")
                .phone("+1234567890")
                .role(UserRole.OWNER)
                .isActive(true)
                .isEmailVerified(true)
                .build();
        user.setId(1L);
    }

    @Test
    @DisplayName("Should successfully register a new user")
    void register_Success() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenProvider.generateToken(any(Authentication.class))).thenReturn("access-token");
        when(tokenProvider.generateRefreshToken(any(Authentication.class))).thenReturn("refresh-token");

        // When
        AuthResponse response = authService.register(registerRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("access-token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getRole()).isEqualTo("OWNER");
        assertThat(response.getOrganizationName()).isEqualTo("Test Org");

        verify(userRepository).existsByEmail("test@example.com");
        verify(organizationRepository).save(any(Organization.class));
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password123");
        verify(tokenProvider).generateToken(any(Authentication.class));
        verify(tokenProvider).generateRefreshToken(any(Authentication.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists during registration")
    void register_EmailAlreadyExists_ThrowsException() {
        // Given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Email already registered");

        verify(userRepository).existsByEmail("test@example.com");
        verify(organizationRepository, never()).save(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully login with valid credentials")
    void login_Success() {
        // Given
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByEmailWithOrganization(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenProvider.generateToken(any(Authentication.class))).thenReturn("access-token");
        when(tokenProvider.generateRefreshToken(any(Authentication.class))).thenReturn("refresh-token");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("access-token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getUserId()).isEqualTo(1L);
        assertThat(response.getOrganizationId()).isEqualTo(1L);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmailWithOrganization("test@example.com");
        verify(userRepository).save(any(User.class));
        verify(tokenProvider).generateToken(authentication);
        verify(tokenProvider).generateRefreshToken(authentication);
    }

    @Test
    @DisplayName("Should update last login time on successful login")
    void login_UpdatesLastLoginTime() {
        // Given
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByEmailWithOrganization(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(tokenProvider.generateToken(any(Authentication.class))).thenReturn("access-token");
        when(tokenProvider.generateRefreshToken(any(Authentication.class))).thenReturn("refresh-token");

        LocalDateTime beforeLogin = LocalDateTime.now();

        // When
        authService.login(loginRequest);

        // Then
        verify(userRepository).save(argThat(savedUser -> {
            LocalDateTime lastLogin = savedUser.getLastLoginAt();
            return lastLogin != null &&
                    !lastLogin.isBefore(beforeLogin) &&
                    !lastLogin.isAfter(LocalDateTime.now().plusSeconds(1));
        }));
    }

    @Test
    @DisplayName("Should throw exception when user not found during login")
    void login_UserNotFound_ThrowsException() {
        // Given
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByEmailWithOrganization(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User not found");

        verify(authenticationManager).authenticate(any());
        verify(userRepository).findByEmailWithOrganization("test@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully refresh token")
    void refreshToken_Success() {
        // Given
        String oldRefreshToken = "old-refresh-token";
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(tokenProvider.getUsernameFromToken(anyString())).thenReturn("test@example.com");
        when(tokenProvider.generateTokenFromUsername(anyString())).thenReturn("new-access-token");
        when(userRepository.findByEmailWithOrganization(anyString())).thenReturn(Optional.of(user));

        // When
        AuthResponse response = authService.refreshToken(oldRefreshToken);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("new-access-token");
        assertThat(response.getRefreshToken()).isEqualTo(oldRefreshToken);
        assertThat(response.getEmail()).isEqualTo("test@example.com");
        assertThat(response.getUserId()).isEqualTo(1L);

        verify(tokenProvider).validateToken(oldRefreshToken);
        verify(tokenProvider).getUsernameFromToken(oldRefreshToken);
        verify(tokenProvider).generateTokenFromUsername("test@example.com");
        verify(userRepository).findByEmailWithOrganization("test@example.com");
    }

    @Test
    @DisplayName("Should throw exception when refresh token is invalid")
    void refreshToken_InvalidToken_ThrowsException() {
        // Given
        String invalidToken = "invalid-token";
        when(tokenProvider.validateToken(anyString())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> authService.refreshToken(invalidToken))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Invalid refresh token");

        verify(tokenProvider).validateToken(invalidToken);
        verify(tokenProvider, never()).getUsernameFromToken(anyString());
        verify(userRepository, never()).findByEmailWithOrganization(anyString());
    }

    @Test
    @DisplayName("Should throw exception when user not found during token refresh")
    void refreshToken_UserNotFound_ThrowsException() {
        // Given
        String refreshToken = "valid-refresh-token";
        when(tokenProvider.validateToken(anyString())).thenReturn(true);
        when(tokenProvider.getUsernameFromToken(anyString())).thenReturn("test@example.com");
        when(userRepository.findByEmailWithOrganization(anyString())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> authService.refreshToken(refreshToken))
                .isInstanceOf(BusinessException.class)
                .hasMessage("User not found");

        verify(tokenProvider).validateToken(refreshToken);
        verify(tokenProvider).getUsernameFromToken(refreshToken);
        verify(userRepository).findByEmailWithOrganization("test@example.com");
    }
}
