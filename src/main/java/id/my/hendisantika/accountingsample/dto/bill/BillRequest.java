package id.my.hendisantika.accountingsample.dto.bill;

import id.my.hendisantika.accountingsample.model.enums.BillStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
 * Time: 13.35
 * To change this template use File | Settings | File Templates.
 */
@Data
public class BillRequest {

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    @NotBlank(message = "Bill number is required")
    private String billNumber;

    @NotNull(message = "Bill date is required")
    private LocalDate billDate;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private BillStatus status;

    @NotEmpty(message = "Bill items cannot be empty")
    @Valid
    private List<BillItemRequest> items;

    private String notes;

    private String reference;
}
