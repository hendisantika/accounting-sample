package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.customer.CustomerRequest;
import id.my.hendisantika.accountingsample.dto.customer.CustomerResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Customer;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.repository.CustomerRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
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
 * Time: 12.55
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrganizationRepository organizationRepository;

    public List<CustomerResponse> getAllCustomers() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return customerRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> getActiveCustomers() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return customerRepository.findByIsActiveAndOrganizationId(true, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> searchCustomers(String name) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return customerRepository.findByNameContainingIgnoreCaseAndOrganizationId(name, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Customer customer = customerRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return mapToResponse(customer);
    }

    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        // Check if email already exists
        if (request.getEmail() != null &&
                customerRepository.existsByEmailAndOrganizationId(request.getEmail(), orgId)) {
            throw new BusinessException("Customer with this email already exists");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Customer customer = Customer.builder()
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
                .billingAddressLine1(request.getBillingAddressLine1())
                .billingAddressLine2(request.getBillingAddressLine2())
                .billingCity(request.getBillingCity())
                .billingState(request.getBillingState())
                .billingPostalCode(request.getBillingPostalCode())
                .billingCountry(request.getBillingCountry())
                .shippingAddressLine1(request.getShippingAddressLine1())
                .shippingAddressLine2(request.getShippingAddressLine2())
                .shippingCity(request.getShippingCity())
                .shippingState(request.getShippingState())
                .shippingPostalCode(request.getShippingPostalCode())
                .shippingCountry(request.getShippingCountry())
                .notes(request.getNotes())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .outstandingBalance(BigDecimal.ZERO)
                .build();

        customer = customerRepository.save(customer);
        return mapToResponse(customer);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Customer customer = customerRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Check if new email already exists (if email is being changed)
        if (request.getEmail() != null && !request.getEmail().equals(customer.getEmail()) &&
                customerRepository.existsByEmailAndOrganizationId(request.getEmail(), orgId)) {
            throw new BusinessException("Customer with this email already exists");
        }

        // Update fields
        customer.setName(request.getName());
        customer.setCompanyName(request.getCompanyName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setMobile(request.getMobile());
        customer.setWebsite(request.getWebsite());
        customer.setTaxNumber(request.getTaxNumber());
        customer.setPaymentTerms(request.getPaymentTerms());
        customer.setCreditLimit(request.getCreditLimit());
        customer.setBillingAddressLine1(request.getBillingAddressLine1());
        customer.setBillingAddressLine2(request.getBillingAddressLine2());
        customer.setBillingCity(request.getBillingCity());
        customer.setBillingState(request.getBillingState());
        customer.setBillingPostalCode(request.getBillingPostalCode());
        customer.setBillingCountry(request.getBillingCountry());
        customer.setShippingAddressLine1(request.getShippingAddressLine1());
        customer.setShippingAddressLine2(request.getShippingAddressLine2());
        customer.setShippingCity(request.getShippingCity());
        customer.setShippingState(request.getShippingState());
        customer.setShippingPostalCode(request.getShippingPostalCode());
        customer.setShippingCountry(request.getShippingCountry());
        customer.setNotes(request.getNotes());
        if (request.getIsActive() != null) customer.setIsActive(request.getIsActive());

        customer = customerRepository.save(customer);
        return mapToResponse(customer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Customer customer = customerRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Check if customer has outstanding balance
        if (customer.getOutstandingBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new BusinessException("Cannot delete customer with outstanding balance");
        }

        // In production, you might want to soft delete or check for related invoices
        customerRepository.delete(customer);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .companyName(customer.getCompanyName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .mobile(customer.getMobile())
                .website(customer.getWebsite())
                .taxNumber(customer.getTaxNumber())
                .paymentTerms(customer.getPaymentTerms())
                .creditLimit(customer.getCreditLimit())
                .billingAddressLine1(customer.getBillingAddressLine1())
                .billingAddressLine2(customer.getBillingAddressLine2())
                .billingCity(customer.getBillingCity())
                .billingState(customer.getBillingState())
                .billingPostalCode(customer.getBillingPostalCode())
                .billingCountry(customer.getBillingCountry())
                .shippingAddressLine1(customer.getShippingAddressLine1())
                .shippingAddressLine2(customer.getShippingAddressLine2())
                .shippingCity(customer.getShippingCity())
                .shippingState(customer.getShippingState())
                .shippingPostalCode(customer.getShippingPostalCode())
                .shippingCountry(customer.getShippingCountry())
                .notes(customer.getNotes())
                .isActive(customer.getIsActive())
                .outstandingBalance(customer.getOutstandingBalance())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
