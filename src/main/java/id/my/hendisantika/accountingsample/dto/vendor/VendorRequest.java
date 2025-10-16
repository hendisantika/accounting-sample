package id.my.hendisantika.accountingsample.dto.vendor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 13.15
 * To change this template use File | Settings | File Templates.
 */
@Data
public class VendorRequest {

    @NotBlank(message = "Vendor name is required")
    private String name;

    private String companyName;

    @Email(message = "Email must be valid")
    private String email;

    private String phone;
    private String mobile;
    private String website;
    private String taxNumber;
    private Integer paymentTerms;
    private BigDecimal creditLimit;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String notes;
    private Boolean isActive;
}
