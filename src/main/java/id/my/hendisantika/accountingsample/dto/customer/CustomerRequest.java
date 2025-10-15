package id.my.hendisantika.accountingsample.dto.customer;

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
 * Time: 12.40
 * To change this template use File | Settings | File Templates.
 */
@Data
public class CustomerRequest {

    @NotBlank(message = "Customer name is required")
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
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingCity;
    private String billingState;
    private String billingPostalCode;
    private String billingCountry;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingState;
    private String shippingPostalCode;
    private String shippingCountry;
    private String notes;
    private Boolean isActive;
}
