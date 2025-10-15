package id.my.hendisantika.accountingsample.dto.organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 11.20
 * To change this template use File | Settings | File Templates.
 */
@Data
public class OrganizationRequest {

    @NotBlank(message = "Organization name is required")
    private String name;

    private String legalName;

    @Email(message = "Email must be valid")
    private String email;

    private String phone;
    private String website;
    private String taxId;
    private String registrationNumber;
    private String currencyCode;
    private Integer fiscalYearStartMonth;
    private String dateFormat;
    private String timeZone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String logoUrl;
}
