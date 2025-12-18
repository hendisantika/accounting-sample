package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.invoice.InvoiceItemRequest;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceRequest;
import id.my.hendisantika.accountingsample.dto.invoice.InvoiceResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.*;
import id.my.hendisantika.accountingsample.model.enums.InvoiceStatus;
import id.my.hendisantika.accountingsample.repository.*;
import id.my.hendisantika.accountingsample.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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
 * Time: 13.30
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final OrganizationRepository organizationRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return invoiceRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponse> getInvoicesByCustomer(Long customerId) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return invoiceRepository.findByCustomerIdAndOrganizationId(customerId, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponse> getInvoicesByStatus(InvoiceStatus status) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return invoiceRepository.findByStatusAndOrganizationId(status, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponse> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return invoiceRepository.findByInvoiceDateBetweenAndOrganizationId(startDate, endDate, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InvoiceResponse getInvoiceById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Invoice invoice = invoiceRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));
        return mapToResponse(invoice);
    }

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        // Check if invoice number already exists
        if (invoiceRepository.existsByInvoiceNumberAndOrganizationId(request.getInvoiceNumber(), orgId)) {
            throw new BusinessException("Invoice number already exists");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Customer customer = customerRepository.findByIdAndOrganizationId(request.getCustomerId(), orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Invoice invoice = Invoice.builder()
                .organization(organization)
                .customer(customer)
                .invoiceNumber(request.getInvoiceNumber())
                .invoiceDate(request.getInvoiceDate())
                .dueDate(request.getDueDate())
                .status(request.getStatus() != null ? request.getStatus() : InvoiceStatus.DRAFT)
                .notes(request.getNotes())
                .termsAndConditions(request.getTermsAndConditions())
                .billingAddress(request.getBillingAddress())
                .shippingAddress(request.getShippingAddress())
                .items(new ArrayList<>())
                .build();

        // Add invoice items
        for (InvoiceItemRequest itemRequest : request.getItems()) {
            Item item = itemRepository.findByIdAndOrganizationId(itemRequest.getItemId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemRequest.getItemId()));

            BigDecimal quantity = itemRequest.getQuantity();
            BigDecimal unitPrice = itemRequest.getUnitPrice();
            BigDecimal discount = itemRequest.getDiscount() != null ? itemRequest.getDiscount() : BigDecimal.ZERO;
            BigDecimal taxRate = itemRequest.getTaxRate() != null ? itemRequest.getTaxRate() : BigDecimal.ZERO;

            // Calculate line amount: (quantity * unitPrice - discount) * (1 + taxRate/100)
            BigDecimal lineSubtotal = quantity.multiply(unitPrice).subtract(discount);
            BigDecimal lineTax = lineSubtotal.multiply(taxRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal lineAmount = lineSubtotal.add(lineTax);

            InvoiceItem invoiceItem = InvoiceItem.builder()
                    .invoice(invoice)
                    .item(item)
                    .description(itemRequest.getDescription() != null ? itemRequest.getDescription() : item.getDescription())
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .discount(discount)
                    .taxRate(taxRate)
                    .amount(lineAmount.setScale(2, RoundingMode.HALF_UP))
                    .lineOrder(itemRequest.getLineOrder())
                    .build();

            invoice.getItems().add(invoiceItem);
        }

        // Calculate invoice totals
        calculateInvoiceTotals(invoice);

        invoice = invoiceRepository.save(invoice);
        return mapToResponse(invoice);
    }

    @Transactional
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Invoice invoice = invoiceRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        // Check if invoice is editable
        if (invoice.getStatus() == InvoiceStatus.PAID || invoice.getStatus() == InvoiceStatus.VOID ||
                invoice.getStatus() == InvoiceStatus.CANCELLED) {
            throw new BusinessException("Cannot edit invoice with status: " + invoice.getStatus());
        }

        // Check if new invoice number already exists (if changed)
        if (!request.getInvoiceNumber().equals(invoice.getInvoiceNumber()) &&
                invoiceRepository.existsByInvoiceNumberAndOrganizationId(request.getInvoiceNumber(), orgId)) {
            throw new BusinessException("Invoice number already exists");
        }

        Customer customer = customerRepository.findByIdAndOrganizationId(request.getCustomerId(), orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Update invoice fields
        invoice.setCustomer(customer);
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setInvoiceDate(request.getInvoiceDate());
        invoice.setDueDate(request.getDueDate());
        if (request.getStatus() != null) invoice.setStatus(request.getStatus());
        invoice.setNotes(request.getNotes());
        invoice.setTermsAndConditions(request.getTermsAndConditions());
        invoice.setBillingAddress(request.getBillingAddress());
        invoice.setShippingAddress(request.getShippingAddress());

        // Clear existing items
        invoice.getItems().clear();

        // Add updated invoice items
        for (InvoiceItemRequest itemRequest : request.getItems()) {
            Item item = itemRepository.findByIdAndOrganizationId(itemRequest.getItemId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemRequest.getItemId()));

            BigDecimal quantity = itemRequest.getQuantity();
            BigDecimal unitPrice = itemRequest.getUnitPrice();
            BigDecimal discount = itemRequest.getDiscount() != null ? itemRequest.getDiscount() : BigDecimal.ZERO;
            BigDecimal taxRate = itemRequest.getTaxRate() != null ? itemRequest.getTaxRate() : BigDecimal.ZERO;

            // Calculate line amount
            BigDecimal lineSubtotal = quantity.multiply(unitPrice).subtract(discount);
            BigDecimal lineTax = lineSubtotal.multiply(taxRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal lineAmount = lineSubtotal.add(lineTax);

            InvoiceItem invoiceItem = InvoiceItem.builder()
                    .invoice(invoice)
                    .item(item)
                    .description(itemRequest.getDescription() != null ? itemRequest.getDescription() : item.getDescription())
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .discount(discount)
                    .taxRate(taxRate)
                    .amount(lineAmount.setScale(2, RoundingMode.HALF_UP))
                    .lineOrder(itemRequest.getLineOrder())
                    .build();

            invoice.getItems().add(invoiceItem);
        }

        // Recalculate invoice totals
        calculateInvoiceTotals(invoice);

        invoice = invoiceRepository.save(invoice);
        return mapToResponse(invoice);
    }

    @Transactional
    public InvoiceResponse updateInvoiceStatus(Long id, InvoiceStatus newStatus) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Invoice invoice = invoiceRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        // Validate status transition
        validateStatusTransition(invoice.getStatus(), newStatus);

        invoice.setStatus(newStatus);
        invoice = invoiceRepository.save(invoice);
        return mapToResponse(invoice);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Invoice invoice = invoiceRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        // Only allow deletion of draft or cancelled invoices
        if (invoice.getStatus() != InvoiceStatus.DRAFT && invoice.getStatus() != InvoiceStatus.CANCELLED) {
            throw new BusinessException("Can only delete draft or cancelled invoices");
        }

        invoiceRepository.delete(invoice);
    }

    private void calculateInvoiceTotals(Invoice invoice) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;

        for (InvoiceItem item : invoice.getItems()) {
            BigDecimal lineSubtotal = item.getQuantity().multiply(item.getUnitPrice()).subtract(item.getDiscount());
            BigDecimal lineTax = lineSubtotal.multiply(item.getTaxRate()).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            subtotal = subtotal.add(lineSubtotal);
            taxAmount = taxAmount.add(lineTax);
        }

        BigDecimal totalAmount = subtotal.add(taxAmount);
        BigDecimal balance = totalAmount.subtract(invoice.getPaidAmount());

        invoice.setSubtotal(subtotal.setScale(2, RoundingMode.HALF_UP));
        invoice.setTaxAmount(taxAmount.setScale(2, RoundingMode.HALF_UP));
        invoice.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
        invoice.setBalance(balance.setScale(2, RoundingMode.HALF_UP));
    }

    private void validateStatusTransition(InvoiceStatus currentStatus, InvoiceStatus newStatus) {
        // VOID and CANCELLED are terminal states
        if (currentStatus == InvoiceStatus.VOID || currentStatus == InvoiceStatus.CANCELLED) {
            throw new BusinessException("Cannot change status of a voided or cancelled invoice");
        }

        // PAID invoices can only be voided
        if (currentStatus == InvoiceStatus.PAID && newStatus != InvoiceStatus.VOID) {
            throw new BusinessException("Paid invoices can only be voided");
        }
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {
        List<InvoiceResponse.InvoiceItemResponse> itemResponses = invoice.getItems().stream()
                .map(item -> InvoiceResponse.InvoiceItemResponse.builder()
                        .id(item.getId())
                        .itemId(item.getItem().getId())
                        .itemName(item.getItem().getName())
                        .itemCode(item.getItem().getCode())
                        .description(item.getDescription())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .taxRate(item.getTaxRate())
                        .amount(item.getAmount())
                        .lineOrder(item.getLineOrder())
                        .build())
                .collect(Collectors.toList());

        return InvoiceResponse.builder()
                .id(invoice.getId())
                .customerId(invoice.getCustomer().getId())
                .customerName(invoice.getCustomer().getName())
                .invoiceNumber(invoice.getInvoiceNumber())
                .invoiceDate(invoice.getInvoiceDate())
                .dueDate(invoice.getDueDate())
                .status(invoice.getStatus())
                .items(itemResponses)
                .subtotal(invoice.getSubtotal())
                .taxAmount(invoice.getTaxAmount())
                .totalAmount(invoice.getTotalAmount())
                .paidAmount(invoice.getPaidAmount())
                .balance(invoice.getBalance())
                .notes(invoice.getNotes())
                .termsAndConditions(invoice.getTermsAndConditions())
                .billingAddress(invoice.getBillingAddress())
                .shippingAddress(invoice.getShippingAddress())
                .createdAt(invoice.getCreatedAt())
                .updatedAt(invoice.getUpdatedAt())
                .build();
    }
}
