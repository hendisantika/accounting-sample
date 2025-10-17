package id.my.hendisantika.accountingsample.dto.invoice;

import id.my.hendisantika.accountingsample.model.enums.InvoiceStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 13.30
 * To change this template use File | Settings | File Templates.
 */
@Data
public class InvoiceRequest {

    private Long id; // Optional, used for updates

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private String invoiceNumber; // Auto-generated if not provided

    @NotNull(message = "Invoice date is required")
    private LocalDate invoiceDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private InvoiceStatus status;

    @Valid
    private List<InvoiceItemRequest> items;

    private String notes;

    private String termsAndConditions;

    private String billingAddress;

    private String shippingAddress;

    // Financial summary fields (calculated)
    private BigDecimal subtotal;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount;

    private BigDecimal paidAmount;
}
