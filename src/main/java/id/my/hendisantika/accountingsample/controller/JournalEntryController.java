package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.journal.JournalEntryRequest;
import id.my.hendisantika.accountingsample.dto.journal.JournalEntryResponse;
import id.my.hendisantika.accountingsample.model.enums.JournalEntryStatus;
import id.my.hendisantika.accountingsample.service.JournalEntryService;
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
 * Time: 13.45
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/journal-entries")
@RequiredArgsConstructor
@Tag(name = "Journal Entries", description = "Double-entry bookkeeping journal entries management endpoints")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    @GetMapping
    @Operation(summary = "Get all journal entries")
    public ResponseEntity<ApiResponse<List<JournalEntryResponse>>> getAllJournalEntries() {
        List<JournalEntryResponse> journalEntries = journalEntryService.getAllJournalEntries();
        return ResponseEntity.ok(ApiResponse.success("Journal entries retrieved", journalEntries));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get journal entries by status")
    public ResponseEntity<ApiResponse<List<JournalEntryResponse>>> getJournalEntriesByStatus(
            @PathVariable JournalEntryStatus status) {
        List<JournalEntryResponse> journalEntries = journalEntryService.getJournalEntriesByStatus(status);
        return ResponseEntity.ok(ApiResponse.success("Journal entries retrieved", journalEntries));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get journal entry by ID")
    public ResponseEntity<ApiResponse<JournalEntryResponse>> getJournalEntryById(@PathVariable Long id) {
        JournalEntryResponse journalEntry = journalEntryService.getJournalEntryById(id);
        return ResponseEntity.ok(ApiResponse.success("Journal entry retrieved", journalEntry));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Create new journal entry")
    public ResponseEntity<ApiResponse<JournalEntryResponse>> createJournalEntry(
            @Valid @RequestBody JournalEntryRequest request) {
        JournalEntryResponse journalEntry = journalEntryService.createJournalEntry(request);
        return ResponseEntity.ok(ApiResponse.success("Journal entry created", journalEntry));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Update journal entry (only DRAFT entries)")
    public ResponseEntity<ApiResponse<JournalEntryResponse>> updateJournalEntry(
            @PathVariable Long id,
            @Valid @RequestBody JournalEntryRequest request) {
        JournalEntryResponse journalEntry = journalEntryService.updateJournalEntry(id, request);
        return ResponseEntity.ok(ApiResponse.success("Journal entry updated", journalEntry));
    }

    @PostMapping("/{id}/post")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Post journal entry (updates account balances)")
    public ResponseEntity<ApiResponse<JournalEntryResponse>> postJournalEntry(@PathVariable Long id) {
        JournalEntryResponse journalEntry = journalEntryService.postJournalEntry(id);
        return ResponseEntity.ok(ApiResponse.success("Journal entry posted", journalEntry));
    }

    @PostMapping("/{id}/reverse")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'ACCOUNTANT')")
    @Operation(summary = "Reverse journal entry (creates reversing entry)")
    public ResponseEntity<ApiResponse<JournalEntryResponse>> reverseJournalEntry(@PathVariable Long id) {
        JournalEntryResponse reversingEntry = journalEntryService.reverseJournalEntry(id);
        return ResponseEntity.ok(ApiResponse.success("Journal entry reversed", reversingEntry));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Delete journal entry (only DRAFT entries)")
    public ResponseEntity<ApiResponse<Void>> deleteJournalEntry(@PathVariable Long id) {
        journalEntryService.deleteJournalEntry(id);
        return ResponseEntity.ok(ApiResponse.success("Journal entry deleted", null));
    }
}
