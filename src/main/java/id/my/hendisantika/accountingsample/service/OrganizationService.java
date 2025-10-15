package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.organization.OrganizationRequest;
import id.my.hendisantika.accountingsample.dto.organization.OrganizationResponse;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 11.45
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationResponse getCurrentOrganization() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        return mapToResponse(organization);
    }

    @Transactional
    public OrganizationResponse updateOrganization(OrganizationRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Update fields
        if (request.getName() != null) organization.setName(request.getName());
        if (request.getLegalName() != null) organization.setLegalName(request.getLegalName());
        if (request.getEmail() != null) organization.setEmail(request.getEmail());
        if (request.getPhone() != null) organization.setPhone(request.getPhone());
        if (request.getWebsite() != null) organization.setWebsite(request.getWebsite());
        if (request.getTaxId() != null) organization.setTaxId(request.getTaxId());
        if (request.getRegistrationNumber() != null)
            organization.setRegistrationNumber(request.getRegistrationNumber());
        if (request.getCurrencyCode() != null) organization.setCurrencyCode(request.getCurrencyCode());
        if (request.getFiscalYearStartMonth() != null)
            organization.setFiscalYearStartMonth(request.getFiscalYearStartMonth());
        if (request.getDateFormat() != null) organization.setDateFormat(request.getDateFormat());
        if (request.getTimeZone() != null) organization.setTimeZone(request.getTimeZone());
        if (request.getAddressLine1() != null) organization.setAddressLine1(request.getAddressLine1());
        if (request.getAddressLine2() != null) organization.setAddressLine2(request.getAddressLine2());
        if (request.getCity() != null) organization.setCity(request.getCity());
        if (request.getState() != null) organization.setState(request.getState());
        if (request.getPostalCode() != null) organization.setPostalCode(request.getPostalCode());
        if (request.getCountry() != null) organization.setCountry(request.getCountry());
        if (request.getLogoUrl() != null) organization.setLogoUrl(request.getLogoUrl());

        organization = organizationRepository.save(organization);
        return mapToResponse(organization);
    }

    private OrganizationResponse mapToResponse(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .legalName(organization.getLegalName())
                .email(organization.getEmail())
                .phone(organization.getPhone())
                .website(organization.getWebsite())
                .taxId(organization.getTaxId())
                .registrationNumber(organization.getRegistrationNumber())
                .currencyCode(organization.getCurrencyCode())
                .fiscalYearStartMonth(organization.getFiscalYearStartMonth())
                .dateFormat(organization.getDateFormat())
                .timeZone(organization.getTimeZone())
                .addressLine1(organization.getAddressLine1())
                .addressLine2(organization.getAddressLine2())
                .city(organization.getCity())
                .state(organization.getState())
                .postalCode(organization.getPostalCode())
                .country(organization.getCountry())
                .logoUrl(organization.getLogoUrl())
                .isActive(organization.getIsActive())
                .subscriptionPlan(organization.getSubscriptionPlan())
                .subscriptionExpiresAt(organization.getSubscriptionExpiresAt())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .build();
    }
}
