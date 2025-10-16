package id.my.hendisantika.accountingsample.dto.journal;

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
 * Time: 13.45
 * To change this template use File | Settings | File Templates.
 */
@Data
public class JournalEntryRequest {

    @NotBlank(message = "Journal number is required")
    private String journalNumber;

    @NotNull(message = "Entry date is required")
    private LocalDate entryDate;

    private String reference;
    private String description;

    @NotEmpty(message = "Journal entry must have at least one line")
    @Valid
    private List<JournalEntryLineRequest> lines;
}
