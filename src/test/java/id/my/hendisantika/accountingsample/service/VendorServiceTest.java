package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.vendor.VendorRequest;
import id.my.hendisantika.accountingsample.dto.vendor.VendorResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Vendor;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.VendorRepository;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("VendorService Tests")
class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepository;
    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private VendorService vendorService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Vendor vendor;
    private VendorRequest vendorRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Org")
                .build();
        organization.setId(1L);

        vendor = Vendor.builder()
                .organization(organization)
                .name("Test Vendor")
                .email("vendor@test.com")
                .isActive(true)
                .outstandingBalance(BigDecimal.ZERO)
                .build();
        vendor.setId(1L);

        vendorRequest = new VendorRequest();
        vendorRequest.setName("New Vendor");
        vendorRequest.setEmail("new@vendor.com");
        vendorRequest.setIsActive(true);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should get all vendors")
    void getAllVendors_Success() {
        when(vendorRepository.findByOrganizationId(1L)).thenReturn(Collections.singletonList(vendor));
        List<VendorResponse> responses = vendorService.getAllVendors();
        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("Should create vendor successfully")
    void createVendor_Success() {
        when(vendorRepository.existsByEmailAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        VendorResponse response = vendorService.createVendor(vendorRequest);
        assertThat(response).isNotNull();
        verify(vendorRepository).save(any(Vendor.class));
    }

    @Test
    @DisplayName("Should throw exception when vendor email already exists")
    void createVendor_DuplicateEmail_ThrowsException() {
        when(vendorRepository.existsByEmailAndOrganizationId(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> vendorService.createVendor(vendorRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Vendor with this email already exists");
    }

    @Test
    @DisplayName("Should throw exception when deleting vendor with outstanding balance")
    void deleteVendor_OutstandingBalance_ThrowsException() {
        vendor.setOutstandingBalance(BigDecimal.valueOf(100));
        when(vendorRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(vendor));

        assertThatThrownBy(() -> vendorService.deleteVendor(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot delete vendor with outstanding balance");
    }
}
