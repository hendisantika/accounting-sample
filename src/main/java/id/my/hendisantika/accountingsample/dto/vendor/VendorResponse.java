package id.my.hendisantika.accountingsample.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorResponse {

    private Long id;
    private String name;
    private String companyName;
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
    private BigDecimal outstandingBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
