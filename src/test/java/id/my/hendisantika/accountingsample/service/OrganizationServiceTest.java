package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.organization.OrganizationRequest;
import id.my.hendisantika.accountingsample.dto.organization.OrganizationResponse;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("OrganizationService Tests")
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private OrganizationRequest organizationRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Organization")
                .email("org@test.com")
                .country("US")
                .isActive(true)
                .build();
        organization.setId(1L);

        organizationRequest = new OrganizationRequest();
        organizationRequest.setName("Updated Organization");
        organizationRequest.setEmail("updated@test.com");
        organizationRequest.setPhone("+1234567890");
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should get current organization")
    void getCurrentOrganization_Success() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));

        OrganizationResponse response = organizationService.getCurrentOrganization();
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Test Organization");
        verify(organizationRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when organization not found")
    void getCurrentOrganization_NotFound_ThrowsException() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> organizationService.getCurrentOrganization())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Organization not found");
    }

    @Test
    @DisplayName("Should update organization")
    void updateOrganization_Success() {
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(organizationRepository.save(any(Organization.class))).thenReturn(organization);

        OrganizationResponse response = organizationService.updateOrganization(organizationRequest);
        assertThat(response).isNotNull();
        verify(organizationRepository).save(any(Organization.class));
    }
}
