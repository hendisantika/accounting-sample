package id.my.hendisantika.accountingsample.controller;

import id.my.hendisantika.accountingsample.dto.invoice.InvoiceRequest;
import id.my.hendisantika.accountingsample.service.CustomerService;
import id.my.hendisantika.accountingsample.service.InvoiceService;
import id.my.hendisantika.accountingsample.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 14.30
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final ItemService itemService;

    // ========== Authentication Views ==========

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    // ========== Dashboard ==========

    @GetMapping("/")
    public String home() {
        return "redirect:/swagger-ui";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/index";
    }

    // ========== Customer Views ==========

    @GetMapping("/customers")
    public String customers() {
        return "customers/list";
    }

    @GetMapping("/customers/new")
    public String newCustomer() {
        return "customers/form";
    }

    @GetMapping("/customers/{id}/edit")
    public String editCustomer(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "customers/form";
    }

    // ========== Invoice Views ==========

    @GetMapping("/invoices")
    public String invoices() {
        return "invoices/list";
    }

    @GetMapping("/invoices/new")
    public String newInvoice(Model model) {
        // Create empty invoice request for new invoice
        InvoiceRequest invoice = new InvoiceRequest();
        model.addAttribute("invoice", invoice);

        // Add customers and items for dropdowns
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("items", itemService.getAllItems());

        return "invoices/form";
    }

    @GetMapping("/invoices/{id}/edit")
    public String editInvoice(@PathVariable Long id, Model model) {
        // Fetch existing invoice
        model.addAttribute("invoice", invoiceService.getInvoiceById(id));
        model.addAttribute("id", id);

        // Add customers and items for dropdowns
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("items", itemService.getAllItems());

        return "invoices/form";
    }

    // ========== Vendor Views ==========

    @GetMapping("/vendors")
    public String vendors() {
        return "vendors/list";
    }

    @GetMapping("/vendors/new")
    public String newVendor() {
        return "vendors/form";
    }

    @GetMapping("/vendors/{id}/edit")
    public String editVendor(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "vendors/form";
    }

    // ========== Bill Views ==========

    @GetMapping("/bills")
    public String bills() {
        return "bills/list";
    }

    @GetMapping("/bills/new")
    public String newBill() {
        return "bills/form";
    }

    @GetMapping("/bills/{id}/edit")
    public String editBill(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "bills/form";
    }

    // ========== Item Views ==========

    @GetMapping("/items")
    public String items() {
        return "items/list";
    }

    @GetMapping("/items/new")
    public String newItem() {
        return "items/form";
    }

    @GetMapping("/items/{id}/edit")
    public String editItem(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "items/form";
    }

    // ========== Account Views ==========

    @GetMapping("/accounts")
    public String accounts() {
        return "accounts/list";
    }

    @GetMapping("/accounts/new")
    public String newAccount() {
        return "accounts/form";
    }

    // ========== Payment Views ==========

    @GetMapping("/payments")
    public String payments() {
        return "payments/list";
    }

    @GetMapping("/payments/new")
    public String newPayment() {
        return "payments/form";
    }

    // ========== Journal Entry Views ==========

    @GetMapping("/journal-entries")
    public String journalEntries() {
        return "journal/list";
    }

    @GetMapping("/journal-entries/new")
    public String newJournalEntry() {
        return "journal/form";
    }

    // ========== Reports Views ==========

    @GetMapping("/reports/profit-loss")
    public String profitLossReport() {
        return "reports/profit-loss";
    }

    @GetMapping("/reports/balance-sheet")
    public String balanceSheetReport() {
        return "reports/balance-sheet";
    }

    @GetMapping("/reports/cash-flow")
    public String cashFlowReport() {
        return "reports/cash-flow";
    }

    @GetMapping("/reports/trial-balance")
    public String trialBalanceReport() {
        return "reports/trial-balance";
    }

    @GetMapping("/reports/aged-receivables")
    public String agedReceivablesReport() {
        return "reports/aged-receivables";
    }

    @GetMapping("/reports/aged-payables")
    public String agedPayablesReport() {
        return "reports/aged-payables";
    }

    @GetMapping("/reports/sales")
    public String salesReport() {
        return "reports/sales";
    }

    @GetMapping("/reports/purchases")
    public String purchasesReport() {
        return "reports/purchases";
    }

    // ========== Support Views ==========

    @GetMapping("/help")
    public String help() {
        return "support/help";
    }

    @GetMapping("/docs")
    public String documentation() {
        return "support/docs";
    }

    // ========== Other Views ==========

    @GetMapping("/analytics")
    public String analytics() {
        return "analytics/index";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/organization")
    public String organization() {
        return "organization/settings";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings/index";
    }
}
