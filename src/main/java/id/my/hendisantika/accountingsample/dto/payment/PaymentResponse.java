package id.my.hendisantika.accountingsample.dto.payment;

import id.my.hendisantika.accountingsample.model.enums.PaymentMethod;
import id.my.hendisantika.accountingsample.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 13.40
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private PaymentType paymentType;
    private Long customerId;
    private String customerName;
    private Long vendorId;
    private String vendorName;
    private Long invoiceId;
    private String invoiceNumber;
    private Long billId;
    private String billNumber;
    private String paymentNumber;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String referenceNumber;
    private String notes;
    private Long accountId;
    private String accountName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
