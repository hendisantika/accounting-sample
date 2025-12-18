package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.bill.BillItemRequest;
import id.my.hendisantika.accountingsample.dto.bill.BillRequest;
import id.my.hendisantika.accountingsample.dto.bill.BillResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.*;
import id.my.hendisantika.accountingsample.model.enums.BillStatus;
import id.my.hendisantika.accountingsample.repository.BillRepository;
import id.my.hendisantika.accountingsample.repository.ItemRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.VendorRepository;
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
 * Time: 13.35
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final VendorRepository vendorRepository;
    private final ItemRepository itemRepository;
    private final OrganizationRepository organizationRepository;

    @Transactional(readOnly = true)
    public List<BillResponse> getAllBills() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return billRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BillResponse> getBillsByStatus(BillStatus status) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return billRepository.findByStatusAndOrganizationId(status, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BillResponse> getBillsByVendor(Long vendorId) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return billRepository.findByVendorIdAndOrganizationId(vendorId, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BillResponse> getOverdueBills() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        List<BillStatus> statuses = List.of(BillStatus.SUBMITTED, BillStatus.APPROVED, BillStatus.PARTIALLY_PAID);
        return billRepository.findByDueDateBeforeAndStatusInAndOrganizationId(
                        LocalDate.now(), statuses, orgId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BillResponse> searchBills(String billNumber) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return billRepository.findByBillNumberContainingIgnoreCaseAndOrganizationId(billNumber, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BillResponse getBillById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Bill bill = billRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return mapToResponse(bill);
    }

    @Transactional
    public BillResponse createBill(BillRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        // Check if bill number already exists
        if (billRepository.existsByBillNumberAndOrganizationId(request.getBillNumber(), orgId)) {
            throw new BusinessException("Bill with this number already exists");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Vendor vendor = vendorRepository.findByIdAndOrganizationId(request.getVendorId(), orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        // Validate due date
        if (request.getDueDate().isBefore(request.getBillDate())) {
            throw new BusinessException("Due date cannot be before bill date");
        }

        Bill bill = Bill.builder()
                .organization(organization)
                .vendor(vendor)
                .billNumber(request.getBillNumber())
                .billDate(request.getBillDate())
                .dueDate(request.getDueDate())
                .status(request.getStatus() != null ? request.getStatus() : BillStatus.DRAFT)
                .notes(request.getNotes())
                .reference(request.getReference())
                .items(new ArrayList<>())
                .build();

        // Process items
        for (BillItemRequest itemRequest : request.getItems()) {
            Item item = itemRepository.findByIdAndOrganizationId(itemRequest.getItemId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemRequest.getItemId()));

            BigDecimal quantity = itemRequest.getQuantity();
            BigDecimal unitPrice = itemRequest.getUnitPrice();
            BigDecimal discount = itemRequest.getDiscount() != null ? itemRequest.getDiscount() : BigDecimal.ZERO;
            BigDecimal taxRate = itemRequest.getTaxRate() != null ? itemRequest.getTaxRate() : BigDecimal.ZERO;

            // Calculate line amount
            BigDecimal lineSubtotal = quantity.multiply(unitPrice);
            BigDecimal lineDiscount = lineSubtotal.multiply(discount).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal lineAfterDiscount = lineSubtotal.subtract(lineDiscount);
            BigDecimal lineTax = lineAfterDiscount.multiply(taxRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal lineAmount = lineAfterDiscount.add(lineTax);

            BillItem billItem = BillItem.builder()
                    .bill(bill)
                    .item(item)
                    .description(itemRequest.getDescription())
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .discount(discount)
                    .taxRate(taxRate)
                    .amount(lineAmount)
                    .lineOrder(itemRequest.getLineOrder())
                    .build();

            bill.addItem(billItem);
        }

        // Calculate totals
        calculateBillTotals(bill);

        bill = billRepository.save(bill);
        return mapToResponse(bill);
    }

    @Transactional
    public BillResponse updateBill(Long id, BillRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Bill bill = billRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));

        // Check if can be updated
        if (bill.getStatus() == BillStatus.PAID || bill.getStatus() == BillStatus.CANCELLED) {
            throw new BusinessException("Cannot update a paid or cancelled bill");
        }

        // Check if new bill number already exists (if bill number is being changed)
        if (!request.getBillNumber().equals(bill.getBillNumber()) &&
                billRepository.existsByBillNumberAndOrganizationId(request.getBillNumber(), orgId)) {
            throw new BusinessException("Bill with this number already exists");
        }

        Vendor vendor = vendorRepository.findByIdAndOrganizationId(request.getVendorId(), orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found"));

        // Validate due date
        if (request.getDueDate().isBefore(request.getBillDate())) {
            throw new BusinessException("Due date cannot be before bill date");
        }

        // Update basic fields
        bill.setVendor(vendor);
        bill.setBillNumber(request.getBillNumber());
        bill.setBillDate(request.getBillDate());
        bill.setDueDate(request.getDueDate());
        if (request.getStatus() != null) bill.setStatus(request.getStatus());
        bill.setNotes(request.getNotes());
        bill.setReference(request.getReference());

        // Clear existing items
        bill.getItems().clear();

        // Process new items
        for (BillItemRequest itemRequest : request.getItems()) {
            Item item = itemRepository.findByIdAndOrganizationId(itemRequest.getItemId(), orgId)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemRequest.getItemId()));

            BigDecimal quantity = itemRequest.getQuantity();
            BigDecimal unitPrice = itemRequest.getUnitPrice();
            BigDecimal discount = itemRequest.getDiscount() != null ? itemRequest.getDiscount() : BigDecimal.ZERO;
            BigDecimal taxRate = itemRequest.getTaxRate() != null ? itemRequest.getTaxRate() : BigDecimal.ZERO;

            // Calculate line amount
            BigDecimal lineSubtotal = quantity.multiply(unitPrice);
            BigDecimal lineDiscount = lineSubtotal.multiply(discount).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal lineAfterDiscount = lineSubtotal.subtract(lineDiscount);
            BigDecimal lineTax = lineAfterDiscount.multiply(taxRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal lineAmount = lineAfterDiscount.add(lineTax);

            BillItem billItem = BillItem.builder()
                    .bill(bill)
                    .item(item)
                    .description(itemRequest.getDescription())
                    .quantity(quantity)
                    .unitPrice(unitPrice)
                    .discount(discount)
                    .taxRate(taxRate)
                    .amount(lineAmount)
                    .lineOrder(itemRequest.getLineOrder())
                    .build();

            bill.addItem(billItem);
        }

        // Recalculate totals
        calculateBillTotals(bill);

        bill = billRepository.save(bill);
        return mapToResponse(bill);
    }

    @Transactional
    public BillResponse updateBillStatus(Long id, BillStatus status) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Bill bill = billRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));

        bill.setStatus(status);
        bill = billRepository.save(bill);
        return mapToResponse(bill);
    }

    @Transactional
    public void deleteBill(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Bill bill = billRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Bill not found"));

        // Only allow deletion of draft bills
        if (bill.getStatus() != BillStatus.DRAFT) {
            throw new BusinessException("Only draft bills can be deleted");
        }

        billRepository.delete(bill);
    }

    private void calculateBillTotals(Bill bill) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;

        for (BillItem item : bill.getItems()) {
            BigDecimal lineSubtotal = item.getQuantity().multiply(item.getUnitPrice());
            BigDecimal lineDiscount = lineSubtotal.multiply(item.getDiscount())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal lineAfterDiscount = lineSubtotal.subtract(lineDiscount);
            BigDecimal lineTax = lineAfterDiscount.multiply(item.getTaxRate())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            subtotal = subtotal.add(lineAfterDiscount);
            taxAmount = taxAmount.add(lineTax);
        }

        BigDecimal totalAmount = subtotal.add(taxAmount);
        BigDecimal balance = totalAmount.subtract(bill.getPaidAmount());

        bill.setSubtotal(subtotal);
        bill.setTaxAmount(taxAmount);
        bill.setTotalAmount(totalAmount);
        bill.setBalance(balance);
    }

    private BillResponse mapToResponse(Bill bill) {
        List<BillResponse.BillItemResponse> itemResponses = bill.getItems().stream()
                .map(item -> BillResponse.BillItemResponse.builder()
                        .id(item.getId())
                        .itemId(item.getItem().getId())
                        .itemName(item.getItem().getName())
                        .description(item.getDescription())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .taxRate(item.getTaxRate())
                        .amount(item.getAmount())
                        .lineOrder(item.getLineOrder())
                        .build())
                .collect(Collectors.toList());

        return BillResponse.builder()
                .id(bill.getId())
                .vendorId(bill.getVendor().getId())
                .vendorName(bill.getVendor().getName())
                .billNumber(bill.getBillNumber())
                .billDate(bill.getBillDate())
                .dueDate(bill.getDueDate())
                .status(bill.getStatus())
                .subtotal(bill.getSubtotal())
                .taxAmount(bill.getTaxAmount())
                .totalAmount(bill.getTotalAmount())
                .paidAmount(bill.getPaidAmount())
                .balance(bill.getBalance())
                .notes(bill.getNotes())
                .reference(bill.getReference())
                .items(itemResponses)
                .createdAt(bill.getCreatedAt())
                .updatedAt(bill.getUpdatedAt())
                .build();
    }
}
