package id.my.hendisantika.accountingsample.service;

import id.my.hendisantika.accountingsample.dto.item.ItemRequest;
import id.my.hendisantika.accountingsample.dto.item.ItemResponse;
import id.my.hendisantika.accountingsample.exception.BusinessException;
import id.my.hendisantika.accountingsample.exception.ResourceNotFoundException;
import id.my.hendisantika.accountingsample.model.Account;
import id.my.hendisantika.accountingsample.model.Item;
import id.my.hendisantika.accountingsample.model.Organization;
import id.my.hendisantika.accountingsample.model.Tax;
import id.my.hendisantika.accountingsample.model.enums.ItemType;
import id.my.hendisantika.accountingsample.repository.AccountRepository;
import id.my.hendisantika.accountingsample.repository.ItemRepository;
import id.my.hendisantika.accountingsample.repository.OrganizationRepository;
import id.my.hendisantika.accountingsample.repository.TaxRepository;
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
 * Time: 13.25
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final OrganizationRepository organizationRepository;
    private final AccountRepository accountRepository;
    private final TaxRepository taxRepository;

    @Transactional(readOnly = true)
    public List<ItemResponse> getAllItems() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return itemRepository.findByOrganizationId(orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getActiveItems() {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return itemRepository.findByIsActiveAndOrganizationId(true, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getItemsByType(ItemType itemType) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return itemRepository.findByItemTypeAndOrganizationId(itemType, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> searchItems(String name) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        return itemRepository.findByNameContainingIgnoreCaseAndOrganizationId(name, orgId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ItemResponse getItemById(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Item item = itemRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        return mapToResponse(item);
    }

    @Transactional
    public ItemResponse createItem(ItemRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();

        // Check if code already exists
        if (itemRepository.existsByCodeAndOrganizationId(request.getCode(), orgId)) {
            throw new BusinessException("Item with this code already exists");
        }

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        Item item = Item.builder()
                .organization(organization)
                .name(request.getName())
                .code(request.getCode())
                .sku(request.getSku())
                .description(request.getDescription())
                .itemType(request.getItemType())
                .unit(request.getUnit())
                .salePrice(request.getSalePrice())
                .purchasePrice(request.getPurchasePrice())
                .trackInventory(request.getTrackInventory() != null ? request.getTrackInventory() : false)
                .currentStock(request.getCurrentStock() != null ? request.getCurrentStock() : BigDecimal.ZERO)
                .reorderLevel(request.getReorderLevel())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        // Set tax relationships if provided
        if (request.getSalesTaxId() != null) {
            Tax salesTax = taxRepository.findById(request.getSalesTaxId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sales tax not found"));
            item.setSalesTax(salesTax);
        }

        if (request.getPurchaseTaxId() != null) {
            Tax purchaseTax = taxRepository.findById(request.getPurchaseTaxId())
                    .orElseThrow(() -> new ResourceNotFoundException("Purchase tax not found"));
            item.setPurchaseTax(purchaseTax);
        }

        // Set account relationships if provided
        if (request.getSalesAccountId() != null) {
            Account salesAccount = accountRepository.findById(request.getSalesAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sales account not found"));
            item.setSalesAccount(salesAccount);
        }

        if (request.getPurchaseAccountId() != null) {
            Account purchaseAccount = accountRepository.findById(request.getPurchaseAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Purchase account not found"));
            item.setPurchaseAccount(purchaseAccount);
        }

        if (request.getInventoryAccountId() != null) {
            Account inventoryAccount = accountRepository.findById(request.getInventoryAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory account not found"));
            item.setInventoryAccount(inventoryAccount);
        }

        item = itemRepository.save(item);
        return mapToResponse(item);
    }

    @Transactional
    public ItemResponse updateItem(Long id, ItemRequest request) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Item item = itemRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        // Check if new code already exists (if code is being changed)
        if (!request.getCode().equals(item.getCode()) &&
                itemRepository.existsByCodeAndOrganizationId(request.getCode(), orgId)) {
            throw new BusinessException("Item with this code already exists");
        }

        // Update basic fields
        item.setName(request.getName());
        item.setCode(request.getCode());
        item.setSku(request.getSku());
        item.setDescription(request.getDescription());
        item.setItemType(request.getItemType());
        item.setUnit(request.getUnit());
        item.setSalePrice(request.getSalePrice());
        item.setPurchasePrice(request.getPurchasePrice());
        item.setReorderLevel(request.getReorderLevel());
        if (request.getTrackInventory() != null) item.setTrackInventory(request.getTrackInventory());
        if (request.getCurrentStock() != null) item.setCurrentStock(request.getCurrentStock());
        if (request.getIsActive() != null) item.setIsActive(request.getIsActive());

        // Update tax relationships
        if (request.getSalesTaxId() != null) {
            Tax salesTax = taxRepository.findById(request.getSalesTaxId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sales tax not found"));
            item.setSalesTax(salesTax);
        } else {
            item.setSalesTax(null);
        }

        if (request.getPurchaseTaxId() != null) {
            Tax purchaseTax = taxRepository.findById(request.getPurchaseTaxId())
                    .orElseThrow(() -> new ResourceNotFoundException("Purchase tax not found"));
            item.setPurchaseTax(purchaseTax);
        } else {
            item.setPurchaseTax(null);
        }

        // Update account relationships
        if (request.getSalesAccountId() != null) {
            Account salesAccount = accountRepository.findById(request.getSalesAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sales account not found"));
            item.setSalesAccount(salesAccount);
        } else {
            item.setSalesAccount(null);
        }

        if (request.getPurchaseAccountId() != null) {
            Account purchaseAccount = accountRepository.findById(request.getPurchaseAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Purchase account not found"));
            item.setPurchaseAccount(purchaseAccount);
        } else {
            item.setPurchaseAccount(null);
        }

        if (request.getInventoryAccountId() != null) {
            Account inventoryAccount = accountRepository.findById(request.getInventoryAccountId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory account not found"));
            item.setInventoryAccount(inventoryAccount);
        } else {
            item.setInventoryAccount(null);
        }

        item = itemRepository.save(item);
        return mapToResponse(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        Long orgId = SecurityUtils.getCurrentOrganizationId();
        Item item = itemRepository.findByIdAndOrganizationId(id, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        // In production, you might want to check for related invoices/transactions
        itemRepository.delete(item);
    }

    private ItemResponse mapToResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .code(item.getCode())
                .sku(item.getSku())
                .description(item.getDescription())
                .itemType(item.getItemType())
                .unit(item.getUnit())
                .salePrice(item.getSalePrice())
                .purchasePrice(item.getPurchasePrice())
                .salesTaxId(item.getSalesTax() != null ? item.getSalesTax().getId() : null)
                .salesTaxName(item.getSalesTax() != null ? item.getSalesTax().getName() : null)
                .purchaseTaxId(item.getPurchaseTax() != null ? item.getPurchaseTax().getId() : null)
                .purchaseTaxName(item.getPurchaseTax() != null ? item.getPurchaseTax().getName() : null)
                .salesAccountId(item.getSalesAccount() != null ? item.getSalesAccount().getId() : null)
                .salesAccountName(item.getSalesAccount() != null ? item.getSalesAccount().getName() : null)
                .purchaseAccountId(item.getPurchaseAccount() != null ? item.getPurchaseAccount().getId() : null)
                .purchaseAccountName(item.getPurchaseAccount() != null ? item.getPurchaseAccount().getName() : null)
                .inventoryAccountId(item.getInventoryAccount() != null ? item.getInventoryAccount().getId() : null)
                .inventoryAccountName(item.getInventoryAccount() != null ? item.getInventoryAccount().getName() : null)
                .trackInventory(item.getTrackInventory())
                .currentStock(item.getCurrentStock())
                .reorderLevel(item.getReorderLevel())
                .isActive(item.getIsActive())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}
