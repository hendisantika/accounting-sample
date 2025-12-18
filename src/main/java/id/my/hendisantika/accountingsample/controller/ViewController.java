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

    @GetMapping("/invoices/recurring")
    public String recurringInvoices() {
        return "invoices/recurring";
    }

    @GetMapping("/invoices/overdue")
    public String overdueInvoices() {
        return "invoices/overdue";
    }

    // ========== Sales Orders ==========

    @GetMapping("/sales-orders")
    public String salesOrders() {
        return "sales-orders/list";
    }

    @GetMapping("/sales-orders/new")
    public String newSalesOrder() {
        return "sales-orders/form";
    }

    // ========== Credit Notes ==========

    @GetMapping("/credit-notes")
    public String creditNotes() {
        return "credit-notes/list";
    }

    @GetMapping("/credit-notes/new")
    public String newCreditNote() {
        return "credit-notes/form";
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

    @GetMapping("/bills/recurring")
    public String recurringBills() {
        return "bills/recurring";
    }

    // ========== Purchase Orders ==========

    @GetMapping("/purchase-orders")
    public String purchaseOrders() {
        return "purchase-orders/list";
    }

    @GetMapping("/purchase-orders/new")
    public String newPurchaseOrder() {
        return "purchase-orders/form";
    }

    // ========== Vendor Credits ==========

    @GetMapping("/vendor-credits")
    public String vendorCredits() {
        return "vendor-credits/list";
    }

    @GetMapping("/vendor-credits/new")
    public String newVendorCredit() {
        return "vendor-credits/form";
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

    @GetMapping("/items/categories")
    public String itemCategories() {
        return "items/categories";
    }

    // ========== Inventory Views ==========

    @GetMapping("/inventory/adjustments")
    public String inventoryAdjustments() {
        return "inventory/adjustments";
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

    @GetMapping("/payments/received")
    public String paymentsReceived() {
        return "payments/received";
    }

    @GetMapping("/payments/made")
    public String paymentsMade() {
        return "payments/made";
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

    @GetMapping("/settings/organization")
    public String settingsOrganization() {
        return "settings/organization";
    }

    @GetMapping("/settings/users")
    public String settingsUsers() {
        return "settings/users";
    }

    @GetMapping("/settings/preferences")
    public String settingsPreferences() {
        return "settings/preferences";
    }

    @GetMapping("/settings/templates")
    public String settingsTemplates() {
        return "settings/templates";
    }

    @GetMapping("/settings/currencies")
    public String settingsCurrencies() {
        return "settings/currencies";
    }

    @GetMapping("/settings/payment-methods")
    public String settingsPaymentMethods() {
        return "settings/payment-methods";
    }

    @GetMapping("/settings/email")
    public String settingsEmail() {
        return "settings/email";
    }

    @GetMapping("/settings/integrations")
    public String settingsIntegrations() {
        return "settings/integrations";
    }

    // ========== Banking Views ==========

    @GetMapping("/banks")
    public String banks() {
        return "banks/list";
    }

    @GetMapping("/banks/transactions")
    public String bankTransactions() {
        return "banks/transactions";
    }

    @GetMapping("/banks/reconciliation")
    public String bankReconciliation() {
        return "banks/reconciliation";
    }

    // ========== Tax Views ==========

    @GetMapping("/taxes")
    public String taxes() {
        return "taxes/list";
    }

    // ========== Audit Log ==========

    @GetMapping("/audit-log")
    public String auditLog() {
        return "audit/log";
    }
}
