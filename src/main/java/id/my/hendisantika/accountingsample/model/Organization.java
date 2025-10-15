package id.my.hendisantika.accountingsample.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 09.40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "legal_name")
    private String legalName;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String website;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "currency_code", nullable = false)
    @Builder.Default
    private String currencyCode = "USD";

    @Column(name = "fiscal_year_start_month", nullable = false)
    @Builder.Default
    private Integer fiscalYearStartMonth = 1;

    @Column(name = "date_format")
    @Builder.Default
    private String dateFormat = "dd/MM/yyyy";

    @Column(name = "time_zone")
    @Builder.Default
    private String timeZone = "UTC";

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    private String city;
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    private String country;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "subscription_plan")
    @Builder.Default
    private String subscriptionPlan = "FREE";

    @Column(name = "subscription_expires_at")
    private LocalDateTime subscriptionExpiresAt;
}
