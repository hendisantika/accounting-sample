package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.tax.TaxRequest;
import id.my.hendisantika.accountingsample.dto.tax.TaxResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Tax;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.TaxRepository;
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
@DisplayName("TaxService Tests")
class TaxServiceTest {

    @Mock
    private TaxRepository taxRepository;
    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private TaxService taxService;

    private MockedStatic<SecurityUtils> securityUtilsMock;
    private Organization organization;
    private Tax tax;
    private TaxRequest taxRequest;

    @BeforeEach
    void setUp() {
        securityUtilsMock = mockStatic(SecurityUtils.class);
        securityUtilsMock.when(SecurityUtils::getCurrentOrganizationId).thenReturn(1L);

        organization = Organization.builder()
                .name("Test Org")
                .build();
        organization.setId(1L);

        tax = Tax.builder()
                .organization(organization)
                .name("VAT")
                .code("VAT10")
                .rate(BigDecimal.valueOf(10))
                .isActive(true)
                .build();
        tax.setId(1L);

        taxRequest = new TaxRequest();
        taxRequest.setName("Sales Tax");
        taxRequest.setCode("ST5");
        taxRequest.setRate(BigDecimal.valueOf(5));
        taxRequest.setIsActive(true);
    }

    @AfterEach
    void tearDown() {
        securityUtilsMock.close();
    }

    @Test
    @DisplayName("Should get all taxes")
    void getAllTaxes_Success() {
        when(taxRepository.findByOrganizationId(1L)).thenReturn(Collections.singletonList(tax));
        List<TaxResponse> responses = taxService.getAllTaxes();
        assertThat(responses).hasSize(1);
    }

    @Test
    @DisplayName("Should create tax successfully")
    void createTax_Success() {
        when(taxRepository.existsByCodeAndOrganizationId(any(), any())).thenReturn(false);
        when(organizationRepository.findById(1L)).thenReturn(Optional.of(organization));
        when(taxRepository.save(any(Tax.class))).thenReturn(tax);

        TaxResponse response = taxService.createTax(taxRequest);
        assertThat(response).isNotNull();
        verify(taxRepository).save(any(Tax.class));
    }

    @Test
    @DisplayName("Should throw exception when tax code already exists")
    void createTax_DuplicateCode_ThrowsException() {
        when(taxRepository.existsByCodeAndOrganizationId(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> taxService.createTax(taxRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Tax with this code already exists");
    }

    @Test
    @DisplayName("Should calculate tax correctly")
    void calculateTax_Success() {
        when(taxRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(tax));

        BigDecimal taxAmount = taxService.calculateTax(BigDecimal.valueOf(100), 1L);
        assertThat(taxAmount).isEqualByComparingTo(BigDecimal.valueOf(10.00));
    }

    @Test
    @DisplayName("Should throw exception when calculating with inactive tax")
    void calculateTax_InactiveTax_ThrowsException() {
        tax.setIsActive(false);
        when(taxRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(tax));

        assertThatThrownBy(() -> taxService.calculateTax(BigDecimal.valueOf(100), 1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Cannot calculate with inactive tax");
    }

    @Test
    @DisplayName("Should delete tax")
    void deleteTax_Success() {
        when(taxRepository.findByIdAndOrganizationId(1L, 1L)).thenReturn(Optional.of(tax));
        taxService.deleteTax(1L);
        verify(taxRepository).delete(tax);
    }
}
