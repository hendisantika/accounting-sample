package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.ApiResponse;
import id.my.hendisantika.accountingsample.dto.organization.OrganizationRequest;
import id.my.hendisantika.accountingsample.dto.organization.OrganizationResponse;
import id.my.hendisantika.accountingsample.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 11.55
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
@Tag(name = "Organization", description = "Organization management endpoints")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/current")
    @Operation(summary = "Get current organization details")
    public ResponseEntity<ApiResponse<OrganizationResponse>> getCurrentOrganization() {
        OrganizationResponse response = organizationService.getCurrentOrganization();
        return ResponseEntity.ok(ApiResponse.success("Organization retrieved", response));
    }

    @PutMapping("/current")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @Operation(summary = "Update current organization")
    public ResponseEntity<ApiResponse<OrganizationResponse>> updateOrganization(
            @Valid @RequestBody OrganizationRequest request) {
        OrganizationResponse response = organizationService.updateOrganization(request);
        return ResponseEntity.ok(ApiResponse.success("Organization updated", response));
    }
}
