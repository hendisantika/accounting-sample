package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.tax.TaxRequest;
import id.my.hendisantika.accountingsample.dto.tax.TaxResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Tax;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.TaxRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
 * Time: 13.20
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional(readOnly = true)
    public List<TaxResponse> getAllTaxes() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return taxRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaxResponse> getActiveTaxes() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return taxRepository.findByIsActiveAndOrganizationId(true, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaxResponse getTaxById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Tax tax = taxRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));
        return mapToResponse(tax);
    }

    @Transactional(readOnly = true)
    public TaxResponse getTaxByCode(String code) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Tax tax = taxRepository.findByCodeAndOrganizationId(code, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found with code: " + code));
        return mapToResponse(tax);
    }

    @Transactional
    public TaxResponse createTax(TaxRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        // Check if tax code already exists
        if (taxRepository.existsByCodeAndOrganizationId(request.getCode(), orgId)) {
            throw new BusinessException("Tax with this code already exists");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Tax tax = Tax.builder()
                .organization(organization)
                .name(request.getName())
                .code(request.getCode())
                .rate(request.getRate())
                .description(request.getDescription())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .isCompound(request.getIsCompound() != null ? request.getIsCompound() : false)
                .build();

        tax = taxRepository.save(tax);
        return mapToResponse(tax);
    }

    @Transactional
    public TaxResponse updateTax(Long id, TaxRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Tax tax = taxRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));

        // Check if new code already exists (if code is being changed)
        if (!request.getCode().equals(tax.getCode()) &&
                taxRepository.existsByCodeAndOrganizationId(request.getCode(), orgId)) {
            throw new BusinessException("Tax with this code already exists");
        }

        // Update fields
        tax.setName(request.getName());
        tax.setCode(request.getCode());
        tax.setRate(request.getRate());
        tax.setDescription(request.getDescription());
        if (request.getIsActive() != null) tax.setIsActive(request.getIsActive());
        if (request.getIsCompound() != null) tax.setIsCompound(request.getIsCompound());

        tax = taxRepository.save(tax);
        return mapToResponse(tax);
    }

    @Transactional
    public void deleteTax(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Tax tax = taxRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));

        // In production, you might want to check for related invoices or transactions
        taxRepository.delete(tax);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTax(BigDecimal amount, Long taxId) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Tax tax = taxRepository.findByIdAndOrganizationId(taxId, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));

        if (!tax.getIsActive()) {
            throw new BusinessException("Cannot calculate with inactive tax");
        }

        // Calculate tax amount: amount * (rate / 100)
        BigDecimal taxAmount = amount.multiply(tax.getRate())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        return taxAmount;
    }

    private TaxResponse mapToResponse(Tax tax) {
        return TaxResponse.builder()
                .id(tax.getId())
                .name(tax.getName())
                .code(tax.getCode())
                .rate(tax.getRate())
                .description(tax.getDescription())
                .isActive(tax.getIsActive())
                .isCompound(tax.getIsCompound())
                .createdAt(tax.getCreatedAt())
                .updatedAt(tax.getUpdatedAt())
                .build();
    }
}
