package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.vendor.VendorRequest;
import id.my.hendisantika.accountingsample.dto.vendor.VendorResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Vendor;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.VendorRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
 * Time: 13.15
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;
    private final OrganizationRepository organizationRepository;

    public List<VendorResponse> getAllVendors() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return vendorRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<VendorResponse> getActiveVendors() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return vendorRepository.findByIsActiveAndOrganizationId(true, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<VendorResponse> searchVendors(String name) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return vendorRepository.findByNameContainingIgnoreCaseAndOrganizationId(name, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public VendorResponse getVendorById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Vendor vendor = vendorRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));
        return mapToResponse(vendor);
    }

    @Transactional
    public VendorResponse createVendor(VendorRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        // Check if email already exists
        if (request.getEmail() != null &&
                vendorRepository.existsByEmailAndOrganizationId(request.getEmail(), orgId)) {
            throw new BusinessException("Vendor with this email already exists");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Vendor vendor = Vendor.builder()
                .organization(organization)
                .name(request.getName())
                .companyName(request.getCompanyName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .mobile(request.getMobile())
                .website(request.getWebsite())
                .taxNumber(request.getTaxNumber())
                .paymentTerms(request.getPaymentTerms())
                .creditLimit(request.getCreditLimit())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .notes(request.getNotes())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .outstandingBalance(BigDecimal.ZERO)
                .build();

        vendor = vendorRepository.save(vendor);
        return mapToResponse(vendor);
    }

    @Transactional
    public VendorResponse updateVendor(Long id, VendorRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Vendor vendor = vendorRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        // Check if new email already exists (if email is being changed)
        if (request.getEmail() != null && !request.getEmail().equals(vendor.getEmail()) &&
                vendorRepository.existsByEmailAndOrganizationId(request.getEmail(), orgId)) {
            throw new BusinessException("Vendor with this email already exists");
        }

        // Update fields
        vendor.setName(request.getName());
        vendor.setCompanyName(request.getCompanyName());
        vendor.setEmail(request.getEmail());
        vendor.setPhone(request.getPhone());
        vendor.setMobile(request.getMobile());
        vendor.setWebsite(request.getWebsite());
        vendor.setTaxNumber(request.getTaxNumber());
        vendor.setPaymentTerms(request.getPaymentTerms());
        vendor.setCreditLimit(request.getCreditLimit());
        vendor.setAddressLine1(request.getAddressLine1());
        vendor.setAddressLine2(request.getAddressLine2());
        vendor.setCity(request.getCity());
        vendor.setState(request.getState());
        vendor.setPostalCode(request.getPostalCode());
        vendor.setCountry(request.getCountry());
        vendor.setNotes(request.getNotes());
        if (request.getIsActive() != null) vendor.setIsActive(request.getIsActive());

        vendor = vendorRepository.save(vendor);
        return mapToResponse(vendor);
    }

    @Transactional
    public void deleteVendor(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Vendor vendor = vendorRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        // Check if vendor has outstanding balance
        if (vendor.getOutstandingBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessException("Cannot delete vendor with outstanding balance");
        }

        // In production, you might want to soft delete or check for related invoices
        vendorRepository.delete(vendor);
    }

    private VendorResponse mapToResponse(Vendor vendor) {
        return VendorResponse.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .companyName(vendor.getCompanyName())
                .email(vendor.getEmail())
                .phone(vendor.getPhone())
                .mobile(vendor.getMobile())
                .website(vendor.getWebsite())
                .taxNumber(vendor.getTaxNumber())
                .paymentTerms(vendor.getPaymentTerms())
                .creditLimit(vendor.getCreditLimit())
                .addressLine1(vendor.getAddressLine1())
                .addressLine2(vendor.getAddressLine2())
                .city(vendor.getCity())
                .state(vendor.getState())
                .postalCode(vendor.getPostalCode())
                .country(vendor.getCountry())
                .notes(vendor.getNotes())
                .isActive(vendor.getIsActive())
                .outstandingBalance(vendor.getOutstandingBalance())
                .createdAt(vendor.getCreatedAt())
                .updatedAt(vendor.getUpdatedAt())
                .build();
    }
}
