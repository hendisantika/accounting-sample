package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.item.ItemRequest;
import id.my.hendisantika.accountingsample.dto.item.ItemResponse;
import id.my.hendisantika.accountingsample.model.enums.ItemType;
import id.my.hendisantika.accountingsample.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Item", description = "Item/Product/Service catalog management endpoints")
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    @Operation(summary = "Get all items")
    public ResponseEntity<ApiResponse<List<ItemResponse>>> getAllItems() {
        List<ItemResponse> items = itemService.getAllItems();
        return ResponseEntity.ok(ApiResponse.success("Items retrieved", items));
    }

    @GetMapping("/active")
    @Operation(summary = "Get active items")
    public ResponseEntity<ApiResponse<List<ItemResponse>>> getActiveItems() {
        List<ItemResponse> items = itemService.getActiveItems();
        return ResponseEntity.ok(ApiResponse.success("Active items retrieved", items));
    }

    @GetMapping("/type/{itemType}")
    @Operation(summary = "Get items by type")
    public ResponseEntity<ApiResponse<List<ItemResponse>>> getItemsByType(@PathVariable ItemType itemType) {
        List<ItemResponse> items = itemService.getItemsByType(itemType);
        return ResponseEntity.ok(ApiResponse.success("Items retrieved by type", items));
    }

    @GetMapping("/search")
    @Operation(summary = "Search items by name")
    public ResponseEntity<ApiResponse<List<ItemResponse>>> searchItems(@RequestParam String name) {
        List<ItemResponse> items = itemService.searchItems(name);
        return ResponseEntity.ok(ApiResponse.success("Items retrieved", items));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID")
    public ResponseEntity<ApiResponse<ItemResponse>> getItemById(@PathVariable Long id) {
        ItemResponse item = itemService.getItemById(id);
        return ResponseEntity.ok(ApiResponse.success("Item retrieved", item));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Create new item")
    public ResponseEntity<ApiResponse<ItemResponse>> createItem(@Valid @RequestBody ItemRequest request) {
        ItemResponse item = itemService.createItem(request);
        return ResponseEntity.ok(ApiResponse.success("Item created", item));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT', 'USER')")
    @Operation(summary = "Update item")
    public ResponseEntity<ApiResponse<ItemResponse>> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody ItemRequest request) {
        ItemResponse item = itemService.updateItem(id, request);
        return ResponseEntity.ok(ApiResponse.success("Item updated", item));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Delete item")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(ApiResponse.success("Item deleted", null));
    }
}
