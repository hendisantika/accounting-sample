package id.my.hendisantika.accountingsample.controller;

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
public class ViewController {

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
        return "redirect:/dashboard";
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
    public String newInvoice() {
        return "invoices/form";
    }

    @GetMapping("/invoices/{id}/edit")
    public String editInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
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
}
