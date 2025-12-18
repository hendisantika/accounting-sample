package id.my.hendisantika.accountingsample.config;

import id.my.hendisantika.accountingsample.model.*;
import id.my.hendisantika.accountingsample.model.enums.*;
import id.my.hendisantika.accountingsample.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Project : accounting-sample
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 16/10/25
 * Time: 14.00
 * To change this template use File | Settings | File Templates.
 *
 * DISABLED: Data seeding is now handled by Flyway migration V9_18122025_1000__seed_data.sql
 * This class is kept for reference only.
 */
// @Component  // Disabled - Using Flyway migration for data seeding
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;
    private final AccountRepository accountRepository;
    private final TaxRepository taxRepository;
    private final ItemRepository itemRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final BillRepository billRepository;
    private final BillItemRepository billItemRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    private final Random random = new Random();
    private Organization organization;
    private List<User> users;
    private List<Customer> customers;
    private List<Vendor> vendors;
    private List<Account> accounts;
    private List<Tax> taxes;
    private List<Item> items;

    @Override
    public void run(String... args) {
        // Check if data already exists
        if (organizationRepository.count() > 0 || userRepository.count() > 0) {
            log.info("Data already seeded. Skipping...");
            return;
        }

        log.info("Starting data seeding...");

        try {
            seedOrganization();
            seedUsers();
            seedCustomers();
            seedVendors();
            seedChartOfAccounts();
            seedTaxRates();
            seedItems();
            seedInvoices();
            seedBills();
            seedPayments();

            log.info("Data seeding completed successfully!");
            printSeedingSummary();
        } catch (Exception e) {
            log.error("Error during data seeding: {}", e.getMessage(), e);
        }
    }

    private void seedOrganization() {
        log.info("Seeding organization...");

        // Check if organization already exists
        organization = organizationRepository.findAll().stream().findFirst().orElse(null);
        if (organization != null) {
            log.info("Organization already exists: {}", organization.getName());
            return;
        }

        organization = Organization.builder()
                .name("Anime Accounting Corp")
                .legalName("Anime Accounting Corporation Ltd.")
                .email("info@animeaccounting.com")
                .phone("+1-555-ANIME-01")
                .website("https://www.animeaccounting.com")
                .taxId("TAX-ANIME-2025")
                .registrationNumber("REG-AA-100001")
                .currencyCode("USD")
                .fiscalYearStartMonth(1)
                .dateFormat("MM/dd/yyyy")
                .timeZone("America/New_York")
                .addressLine1("123 Anime Street")
                .addressLine2("Suite 100")
                .city("Tokyo")
                .state("Tokyo")
                .postalCode("100-0001")
                .country("Japan")
                .isActive(true)
                .subscriptionPlan("PREMIUM")
                .build();
        organization = organizationRepository.save(organization);
        log.info("Organization created: {}", organization.getName());
    }

    private void seedUsers() {
        log.info("Seeding 100 users from anime series...");
        users = new ArrayList<>();

        // One Piece characters (25)
        String[] onePieceCharacters = {
                "Luffy", "Zoro", "Nami", "Sanji", "Usopp", "Robin", "Chopper", "Franky", "Brook", "Jinbe",
                "Ace", "Sabo", "Shanks", "Law", "Kid", "Whitebeard", "Roger", "Rayleigh", "Mihawk", "Crocodile",
                "Doflamingo", "Katakuri", "Big Mom", "Kaido", "Blackbeard"
        };

        // Demon Slayer characters (25)
        String[] demonSlayerCharacters = {
                "Tanjiro", "Nezuko", "Zenitsu", "Inosuke", "Kanao", "Shinobu", "Giyu", "Rengoku", "Tengen", "Mitsuri",
                "Obanai", "Sanemi", "Gyomei", "Muichiro", "Muzan", "Kokushibo", "Doma", "Akaza", "Gyutaro", "Daki",
                "Enmu", "Rui", "Kaigaku", "Nakime", "Hantengu"
        };

        // Naruto characters (25)
        String[] narutoCharacters = {
                "Naruto", "Sasuke", "Sakura", "Kakashi", "Itachi", "Madara", "Obito", "Gaara", "Rock Lee", "Neji",
                "Hinata", "Shikamaru", "Jiraiya", "Tsunade", "Orochimaru", "Minato", "Hashirama", "Tobirama", "Pain", "Konan",
                "Deidara", "Kisame", "Hidan", "Zabuza", "Kimimaro"
        };

        // Jujutsu Kaisen characters (25)
        String[] jjkCharacters = {
                "Yuji", "Megumi", "Nobara", "Gojo", "Nanami", "Maki", "Toge", "Panda", "Yuta", "Sukuna",
                "Mahito", "Jogo", "Hanami", "Choso", "Geto", "Toji", "Mai", "Mechamaru", "Todo", "Mei",
                "Kamo", "Yuki", "Uraume", "Kenjaku", "Hakari"
        };

        UserRole[] roles = {UserRole.OWNER, UserRole.ADMIN, UserRole.ACCOUNTANT, UserRole.USER, UserRole.VIEWER};
        int roleIndex = 0;

        // Create users from all anime series
        users.addAll(createUsersFromArray(onePieceCharacters, "onepiece", roles, roleIndex));
        roleIndex = (roleIndex + onePieceCharacters.length) % roles.length;

        users.addAll(createUsersFromArray(demonSlayerCharacters, "demonslayer", roles, roleIndex));
        roleIndex = (roleIndex + demonSlayerCharacters.length) % roles.length;

        users.addAll(createUsersFromArray(narutoCharacters, "naruto", roles, roleIndex));
        roleIndex = (roleIndex + narutoCharacters.length) % roles.length;

        users.addAll(createUsersFromArray(jjkCharacters, "jjk", roles, roleIndex));

        userRepository.saveAll(users);
        log.info("Created {} users", users.size());
    }

    private List<User> createUsersFromArray(String[] characters, String series, UserRole[] roles, int startRoleIndex) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < characters.length; i++) {
            String firstName = characters[i];
            String lastName = series.substring(0, 1).toUpperCase() + series.substring(1);
            String email = firstName.toLowerCase() + "." + series + "@animeaccounting.com";

            User user = User.builder()
                    .organization(organization)
                    .email(email)
                    .passwordHash(passwordEncoder.encode("password123"))
                    .firstName(firstName)
                    .lastName(lastName)
                    .phone("+1-555-" + String.format("%04d", random.nextInt(10000)))
                    .role(roles[(startRoleIndex + i) % roles.length])
                    .isActive(true)
                    .isEmailVerified(true)
                    .build();
            userList.add(user);
        }
        return userList;
    }

    private void seedCustomers() {
        log.info("Seeding 50 customers...");
        customers = new ArrayList<>();

        String[] companySuffixes = {"Corp", "Industries", "Trading Co.", "Ltd", "Inc", "Enterprises", "Group", "Holdings", "Solutions", "Technologies"};
        String[] animeNames = {
                "Luffy", "Zoro", "Nami", "Sanji", "Usopp", "Tanjiro", "Nezuko", "Zenitsu", "Inosuke", "Rengoku",
                "Naruto", "Sasuke", "Sakura", "Kakashi", "Itachi", "Yuji", "Megumi", "Nobara", "Gojo", "Nanami",
                "Shanks", "Law", "Kid", "Ace", "Sabo", "Shinobu", "Giyu", "Mitsuri", "Tengen", "Gyomei",
                "Madara", "Obito", "Gaara", "Jiraiya", "Tsunade", "Sukuna", "Mahito", "Geto", "Yuta", "Maki",
                "Robin", "Franky", "Brook", "Jinbe", "Roger", "Kanao", "Muichiro", "Hinata", "Shikamaru", "Toge"
        };

        for (int i = 0; i < 50; i++) {
            String name = animeNames[i];
            String suffix = companySuffixes[i % companySuffixes.length];
            String companyName = name + " " + suffix;

            Customer customer = Customer.builder()
                    .organization(organization)
                    .name(name)
                    .companyName(companyName)
                    .email(name.toLowerCase() + ".customer@example.com")
                    .phone("+1-555-" + String.format("%04d", 1000 + i))
                    .mobile("+1-555-" + String.format("%04d", 2000 + i))
                    .website("https://www." + name.toLowerCase() + ".com")
                    .taxNumber("TAX-" + String.format("%05d", i + 1))
                    .paymentTerms(30)
                    .creditLimit(new BigDecimal(random.nextInt(50000) + 10000))
                    .billingAddressLine1((100 + i) + " Customer Street")
                    .billingCity("New York")
                    .billingState("NY")
                    .billingPostalCode(String.format("%05d", 10000 + i))
                    .billingCountry("USA")
                    .shippingAddressLine1((200 + i) + " Shipping Lane")
                    .shippingCity("New York")
                    .shippingState("NY")
                    .shippingPostalCode(String.format("%05d", 20000 + i))
                    .shippingCountry("USA")
                    .isActive(true)
                    .outstandingBalance(BigDecimal.ZERO)
                    .build();
            customers.add(customer);
        }

        customerRepository.saveAll(customers);
        log.info("Created {} customers", customers.size());
    }

    private void seedVendors() {
        log.info("Seeding 50 vendors...");
        vendors = new ArrayList<>();

        String[] businessSuffixes = {"Supplies", "Manufacturing", "Wholesale", "Distribution", "Services", "Logistics", "Partners", "Vendors", "Imports", "Exports"};
        String[] vendorNames = {
                "Whitebeard", "Kaido", "Big Mom", "Blackbeard", "Mihawk", "Doflamingo", "Crocodile", "Katakuri", "Rayleigh", "Roger",
                "Muzan", "Kokushibo", "Doma", "Akaza", "Hantengu", "Gyutaro", "Daki", "Enmu", "Rui", "Kaigaku",
                "Pain", "Konan", "Deidara", "Kisame", "Hidan", "Orochimaru", "Minato", "Hashirama", "Tobirama", "Zabuza",
                "Jogo", "Hanami", "Choso", "Toji", "Kenjaku", "Uraume", "Mai", "Mechamaru", "Todo", "Yuki",
                "Panda", "Kamo", "Mei", "Hakari", "Kimimaro", "Neji", "Obanai", "Sanemi", "Nakime", "Kid"
        };

        for (int i = 0; i < 50; i++) {
            String name = vendorNames[i];
            String suffix = businessSuffixes[i % businessSuffixes.length];
            String companyName = name + " " + suffix;

            Vendor vendor = Vendor.builder()
                    .organization(organization)
                    .name(name)
                    .companyName(companyName)
                    .email(name.toLowerCase() + ".vendor@example.com")
                    .phone("+1-555-" + String.format("%04d", 3000 + i))
                    .mobile("+1-555-" + String.format("%04d", 4000 + i))
                    .website("https://www." + name.toLowerCase() + "vendor.com")
                    .taxNumber("VTAX-" + String.format("%05d", i + 1))
                    .paymentTerms(45)
                    .creditLimit(new BigDecimal(random.nextInt(100000) + 20000))
                    .addressLine1((300 + i) + " Vendor Avenue")
                    .city("Los Angeles")
                    .state("CA")
                    .postalCode(String.format("%05d", 90000 + i))
                    .country("USA")
                    .isActive(true)
                    .outstandingBalance(BigDecimal.ZERO)
                    .build();
            vendors.add(vendor);
        }

        vendorRepository.saveAll(vendors);
        log.info("Created {} vendors", vendors.size());
    }

    private void seedChartOfAccounts() {
        log.info("Seeding chart of accounts...");
        accounts = new ArrayList<>();

        // Assets (1000-1999)
        accounts.add(createAccount("1000", "Cash", AccountType.ASSET, "Cash on hand"));
        accounts.add(createAccount("1010", "Petty Cash", AccountType.ASSET, "Petty cash fund"));
        accounts.add(createAccount("1100", "Bank - Checking Account", AccountType.ASSET, "Main checking account"));
        accounts.add(createAccount("1110", "Bank - Savings Account", AccountType.ASSET, "Savings account"));
        accounts.add(createAccount("1200", "Accounts Receivable", AccountType.ASSET, "Money owed by customers"));
        accounts.add(createAccount("1300", "Inventory", AccountType.ASSET, "Inventory on hand"));
        accounts.add(createAccount("1400", "Prepaid Expenses", AccountType.ASSET, "Prepaid expenses"));
        accounts.add(createAccount("1500", "Equipment", AccountType.ASSET, "Office and business equipment"));
        accounts.add(createAccount("1510", "Accumulated Depreciation - Equipment", AccountType.ASSET, "Accumulated depreciation"));
        accounts.add(createAccount("1600", "Furniture & Fixtures", AccountType.ASSET, "Office furniture"));

        // Liabilities (2000-2999)
        accounts.add(createAccount("2000", "Accounts Payable", AccountType.LIABILITY, "Money owed to vendors"));
        accounts.add(createAccount("2100", "Credit Card Payable", AccountType.LIABILITY, "Credit card balances"));
        accounts.add(createAccount("2200", "Sales Tax Payable", AccountType.LIABILITY, "Sales tax collected"));
        accounts.add(createAccount("2300", "Payroll Tax Payable", AccountType.LIABILITY, "Payroll taxes payable"));
        accounts.add(createAccount("2400", "Short-term Loan", AccountType.LIABILITY, "Short-term loans"));
        accounts.add(createAccount("2500", "Long-term Loan", AccountType.LIABILITY, "Long-term loans"));
        accounts.add(createAccount("2600", "Accrued Expenses", AccountType.LIABILITY, "Accrued expenses"));

        // Equity (3000-3999)
        accounts.add(createAccount("3000", "Owner's Equity", AccountType.EQUITY, "Owner's equity"));
        accounts.add(createAccount("3100", "Retained Earnings", AccountType.EQUITY, "Retained earnings"));
        accounts.add(createAccount("3200", "Common Stock", AccountType.EQUITY, "Common stock"));
        accounts.add(createAccount("3300", "Dividends", AccountType.EQUITY, "Dividends paid"));

        // Revenue (4000-4999)
        accounts.add(createAccount("4000", "Sales Revenue", AccountType.REVENUE, "Sales of goods"));
        accounts.add(createAccount("4100", "Service Revenue", AccountType.REVENUE, "Service income"));
        accounts.add(createAccount("4200", "Interest Income", AccountType.REVENUE, "Interest earned"));
        accounts.add(createAccount("4300", "Other Income", AccountType.REVENUE, "Miscellaneous income"));

        // Expenses (5000-5999)
        accounts.add(createAccount("5000", "Cost of Goods Sold", AccountType.COST_OF_GOODS_SOLD, "Direct costs"));
        accounts.add(createAccount("5100", "Salaries & Wages", AccountType.EXPENSE, "Employee salaries"));
        accounts.add(createAccount("5200", "Rent Expense", AccountType.EXPENSE, "Office rent"));
        accounts.add(createAccount("5300", "Utilities Expense", AccountType.EXPENSE, "Electricity, water, internet"));
        accounts.add(createAccount("5400", "Office Supplies", AccountType.EXPENSE, "Office supplies"));
        accounts.add(createAccount("5500", "Insurance Expense", AccountType.EXPENSE, "Business insurance"));
        accounts.add(createAccount("5600", "Marketing & Advertising", AccountType.EXPENSE, "Marketing costs"));
        accounts.add(createAccount("5700", "Travel & Entertainment", AccountType.EXPENSE, "Business travel"));
        accounts.add(createAccount("5800", "Depreciation Expense", AccountType.EXPENSE, "Depreciation"));
        accounts.add(createAccount("5900", "Bank Fees", AccountType.EXPENSE, "Bank charges"));
        accounts.add(createAccount("5950", "Miscellaneous Expense", AccountType.EXPENSE, "Other expenses"));

        accountRepository.saveAll(accounts);
        log.info("Created {} accounts", accounts.size());
    }

    private Account createAccount(String code, String name, AccountType type, String description) {
        return Account.builder()
                .organization(organization)
                .code(code)
                .name(name)
                .accountType(type)
                .description(description)
                .currentBalance(BigDecimal.ZERO)
                .isActive(true)
                .isSystem(false)
                .taxApplicable(false)
                .build();
    }

    private void seedTaxRates() {
        log.info("Seeding tax rates...");
        taxes = new ArrayList<>();

        taxes.add(createTax("VAT-10", "VAT 10%", new BigDecimal("10.00"), "Value Added Tax 10%"));
        taxes.add(createTax("VAT-15", "VAT 15%", new BigDecimal("15.00"), "Value Added Tax 15%"));
        taxes.add(createTax("VAT-20", "VAT 20%", new BigDecimal("20.00"), "Value Added Tax 20%"));
        taxes.add(createTax("GST-5", "GST 5%", new BigDecimal("5.00"), "Goods and Services Tax 5%"));
        taxes.add(createTax("GST-10", "GST 10%", new BigDecimal("10.00"), "Goods and Services Tax 10%"));
        taxes.add(createTax("GST-15", "GST 15%", new BigDecimal("15.00"), "Goods and Services Tax 15%"));
        taxes.add(createTax("SALES-6", "Sales Tax 6%", new BigDecimal("6.00"), "Sales Tax 6%"));
        taxes.add(createTax("SALES-7", "Sales Tax 7%", new BigDecimal("7.00"), "Sales Tax 7%"));
        taxes.add(createTax("SALES-8", "Sales Tax 8%", new BigDecimal("8.00"), "Sales Tax 8%"));
        taxes.add(createTax("SALES-9", "Sales Tax 9%", new BigDecimal("9.00"), "Sales Tax 9%"));
        taxes.add(createTax("EXEMPT", "Tax Exempt", BigDecimal.ZERO, "No tax applicable"));
        taxes.add(createTax("LUXURY-25", "Luxury Tax 25%", new BigDecimal("25.00"), "Luxury goods tax"));
        taxes.add(createTax("EXCISE-5", "Excise Tax 5%", new BigDecimal("5.00"), "Excise tax"));
        taxes.add(createTax("EXCISE-10", "Excise Tax 10%", new BigDecimal("10.00"), "Excise tax 10%"));
        taxes.add(createTax("SERVICE-12", "Service Tax 12%", new BigDecimal("12.00"), "Service tax"));
        taxes.add(createTax("SERVICE-15", "Service Tax 15%", new BigDecimal("15.00"), "Service tax 15%"));
        taxes.add(createTax("WITHHOLD-10", "Withholding Tax 10%", new BigDecimal("10.00"), "Withholding tax"));
        taxes.add(createTax("IMPORT-5", "Import Duty 5%", new BigDecimal("5.00"), "Import duty"));
        taxes.add(createTax("IMPORT-15", "Import Duty 15%", new BigDecimal("15.00"), "Import duty 15%"));
        taxes.add(createTax("COMPOSITE-18", "Composite Tax 18%", new BigDecimal("18.00"), "Composite tax rate"));

        taxRepository.saveAll(taxes);
        log.info("Created {} tax rates", taxes.size());
    }

    private Tax createTax(String code, String name, BigDecimal rate, String description) {
        return Tax.builder()
                .organization(organization)
                .code(code)
                .name(name)
                .rate(rate)
                .description(description)
                .isActive(true)
                .isCompound(false)
                .build();
    }

    private void seedItems() {
        log.info("Seeding 50 items...");
        items = new ArrayList<>();

        String[] itemNames = {
                "Devil Fruit Extract", "Pirate Ship Model", "Straw Hat Premium", "Demon Slayer Sword", "Nichirin Blade",
                "Rasengan Energy Drink", "Sharingan Lens", "Chakra Stone", "Cursed Energy Seal", "Domain Expansion Kit",
                "Grand Line Map", "Log Pose Compass", "Breathing Technique Manual", "Jutsu Scroll", "Jujutsu Talisman",
                "Going Merry Replica", "Thousand Sunny Model", "Kasugai Crow", "Konoha Headband", "Tokyo Jujutsu Uniform",
                "Sea Prism Stone", "Wado Ichimonji Replica", "Entertainment District Pass", "Akatsuki Robe", "Sukuna Finger",
                "Treasure Map", "Vivre Card", "Demon Blood Sample", "Nine-Tails Chakra", "Cursed Spirit Core",
                "Anime Action Figure Set", "Collector's Edition Box", "Limited Edition Poster", "Signed Manga Volume", "Character Plushie",
                "Cosplay Costume", "Anime Trading Cards", "Soundtrack CD", "Art Book Premium", "Blu-ray Box Set",
                "Character Keychain", "Enamel Pin Set", "Wall Scroll", "Phone Case", "Laptop Sticker Pack",
                "Anime T-Shirt", "Hoodie Design", "Cap Collection", "Backpack", "Water Bottle"
        };

        Account salesAccount = accounts.stream()
                .filter(a -> a.getCode().equals("4000"))
                .findFirst().orElse(null);

        Account purchaseAccount = accounts.stream()
                .filter(a -> a.getCode().equals("5000"))
                .findFirst().orElse(null);

        Account inventoryAccount = accounts.stream()
                .filter(a -> a.getCode().equals("1300"))
                .findFirst().orElse(null);

        for (int i = 0; i < 50; i++) {
            String name = itemNames[i];
            ItemType type = i % 3 == 0 ? ItemType.SERVICE : (i % 3 == 1 ? ItemType.PRODUCT : ItemType.GOODS);
            Tax tax = taxes.get(i % taxes.size());

            Item item = Item.builder()
                    .organization(organization)
                    .name(name)
                    .code("ITEM-" + String.format("%04d", i + 1))
                    .sku("SKU-" + String.format("%06d", i + 1))
                    .description("Premium " + name + " - Limited Edition")
                    .itemType(type)
                    .unit("pcs")
                    .salePrice(new BigDecimal(random.nextInt(500) + 50))
                    .purchasePrice(new BigDecimal(random.nextInt(300) + 30))
                    .salesTax(tax)
                    .purchaseTax(tax)
                    .salesAccount(salesAccount)
                    .purchaseAccount(purchaseAccount)
                    .inventoryAccount(inventoryAccount)
                    .trackInventory(type != ItemType.SERVICE)
                    .currentStock(new BigDecimal(random.nextInt(1000) + 100))
                    .reorderLevel(new BigDecimal(50))
                    .isActive(true)
                    .build();
            items.add(item);
        }

        itemRepository.saveAll(items);
        log.info("Created {} items", items.size());
    }

    private void seedInvoices() {
        log.info("Seeding 20 invoices...");
        List<Invoice> invoices = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Customer customer = customers.get(i % customers.size());
            LocalDate invoiceDate = LocalDate.now().minusDays(random.nextInt(90));
            LocalDate dueDate = invoiceDate.plusDays(30);

            Invoice invoice = Invoice.builder()
                    .organization(organization)
                    .customer(customer)
                    .invoiceNumber("INV-" + String.format("%06d", i + 1))
                    .invoiceDate(invoiceDate)
                    .dueDate(dueDate)
                    .status(InvoiceStatus.values()[random.nextInt(InvoiceStatus.values().length)])
                    .billingAddress(customer.getBillingAddressLine1() + ", " + customer.getBillingCity())
                    .shippingAddress(customer.getShippingAddressLine1() + ", " + customer.getShippingCity())
                    .notes("Thank you for your business!")
                    .termsAndConditions("Payment due within 30 days")
                    .build();

            invoices.add(invoice);
        }

        invoiceRepository.saveAll(invoices);

        // Add invoice items
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        for (Invoice invoice : invoices) {
            int itemCount = random.nextInt(3) + 2; // 2-4 items per invoice
            BigDecimal subtotal = BigDecimal.ZERO;
            BigDecimal taxAmount = BigDecimal.ZERO;

            for (int j = 0; j < itemCount; j++) {
                Item item = items.get(random.nextInt(items.size()));
                BigDecimal quantity = new BigDecimal(random.nextInt(10) + 1);
                BigDecimal unitPrice = item.getSalePrice();
                BigDecimal discount = BigDecimal.ZERO;
                BigDecimal taxRate = item.getSalesTax() != null ? item.getSalesTax().getRate() : BigDecimal.ZERO;
                BigDecimal lineAmount = quantity.multiply(unitPrice).subtract(discount);
                BigDecimal lineTax = lineAmount.multiply(taxRate).divide(new BigDecimal("100"));

                InvoiceItem invoiceItem = InvoiceItem.builder()
                        .invoice(invoice)
                        .item(item)
                        .description(item.getDescription())
                        .quantity(quantity)
                        .unitPrice(unitPrice)
                        .discount(discount)
                        .taxRate(taxRate)
                        .amount(lineAmount)
                        .lineOrder(j + 1)
                        .build();

                invoiceItems.add(invoiceItem);
                subtotal = subtotal.add(lineAmount);
                taxAmount = taxAmount.add(lineTax);
            }

            invoice.setSubtotal(subtotal);
            invoice.setTaxAmount(taxAmount);
            invoice.setTotalAmount(subtotal.add(taxAmount));
            invoice.setBalance(invoice.getTotalAmount());
        }

        invoiceItemRepository.saveAll(invoiceItems);
        invoiceRepository.saveAll(invoices);
        log.info("Created {} invoices with items", invoices.size());
    }

    private void seedBills() {
        log.info("Seeding 20 bills...");
        List<Bill> bills = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Vendor vendor = vendors.get(i % vendors.size());
            LocalDate billDate = LocalDate.now().minusDays(random.nextInt(60));
            LocalDate dueDate = billDate.plusDays(45);

            Bill bill = Bill.builder()
                    .organization(organization)
                    .vendor(vendor)
                    .billNumber("BILL-" + String.format("%06d", i + 1))
                    .billDate(billDate)
                    .dueDate(dueDate)
                    .status(BillStatus.values()[random.nextInt(BillStatus.values().length)])
                    .reference("PO-" + String.format("%06d", i + 1))
                    .notes("Purchase order for inventory")
                    .build();

            bills.add(bill);
        }

        billRepository.saveAll(bills);

        // Add bill items
        List<BillItem> billItems = new ArrayList<>();
        for (Bill bill : bills) {
            int itemCount = random.nextInt(3) + 2; // 2-4 items per bill
            BigDecimal subtotal = BigDecimal.ZERO;
            BigDecimal taxAmount = BigDecimal.ZERO;

            for (int j = 0; j < itemCount; j++) {
                Item item = items.get(random.nextInt(items.size()));
                BigDecimal quantity = new BigDecimal(random.nextInt(20) + 5);
                BigDecimal unitPrice = item.getPurchasePrice();
                BigDecimal discount = BigDecimal.ZERO;
                BigDecimal taxRate = item.getPurchaseTax() != null ? item.getPurchaseTax().getRate() : BigDecimal.ZERO;
                BigDecimal lineAmount = quantity.multiply(unitPrice).subtract(discount);
                BigDecimal lineTax = lineAmount.multiply(taxRate).divide(new BigDecimal("100"));

                BillItem billItem = BillItem.builder()
                        .bill(bill)
                        .item(item)
                        .description(item.getDescription())
                        .quantity(quantity)
                        .unitPrice(unitPrice)
                        .discount(discount)
                        .taxRate(taxRate)
                        .amount(lineAmount)
                        .lineOrder(j + 1)
                        .build();

                billItems.add(billItem);
                subtotal = subtotal.add(lineAmount);
                taxAmount = taxAmount.add(lineTax);
            }

            bill.setSubtotal(subtotal);
            bill.setTaxAmount(taxAmount);
            bill.setTotalAmount(subtotal.add(taxAmount));
            bill.setBalance(bill.getTotalAmount());
        }

        billItemRepository.saveAll(billItems);
        billRepository.saveAll(bills);
        log.info("Created {} bills with items", bills.size());
    }

    private void seedPayments() {
        log.info("Seeding 30 payments...");
        List<Payment> payments = new ArrayList<>();

        Account cashAccount = accounts.stream()
                .filter(a -> a.getCode().equals("1000"))
                .findFirst().orElse(accounts.get(0));

        Account bankAccount = accounts.stream()
                .filter(a -> a.getCode().equals("1100"))
                .findFirst().orElse(accounts.get(0));

        // Get invoices and bills for payments
        List<Invoice> invoices = invoiceRepository.findAll();
        List<Bill> bills = billRepository.findAll();

        // Create 15 payments received (from invoices)
        for (int i = 0; i < 15 && i < invoices.size(); i++) {
            Invoice invoice = invoices.get(i);
            LocalDate paymentDate = invoice.getInvoiceDate().plusDays(random.nextInt(30) + 1);
            BigDecimal amount = invoice.getTotalAmount().multiply(new BigDecimal("0.5")); // Partial payment

            Payment payment = Payment.builder()
                    .organization(organization)
                    .paymentType(PaymentType.PAYMENT_RECEIVED)
                    .customer(invoice.getCustomer())
                    .invoice(invoice)
                    .paymentNumber("PAY-RCV-" + String.format("%06d", i + 1))
                    .paymentDate(paymentDate)
                    .amount(amount)
                    .paymentMethod(PaymentMethod.values()[random.nextInt(PaymentMethod.values().length)])
                    .referenceNumber("REF-" + String.format("%08d", random.nextInt(99999999)))
                    .notes("Payment received for " + invoice.getInvoiceNumber())
                    .account(i % 2 == 0 ? cashAccount : bankAccount)
                    .build();

            payments.add(payment);
        }

        // Create 15 payments made (for bills)
        for (int i = 0; i < 15 && i < bills.size(); i++) {
            Bill bill = bills.get(i);
            LocalDate paymentDate = bill.getBillDate().plusDays(random.nextInt(45) + 1);
            BigDecimal amount = bill.getTotalAmount(); // Full payment

            Payment payment = Payment.builder()
                    .organization(organization)
                    .paymentType(PaymentType.PAYMENT_MADE)
                    .vendor(bill.getVendor())
                    .bill(bill)
                    .paymentNumber("PAY-MADE-" + String.format("%06d", i + 1))
                    .paymentDate(paymentDate)
                    .amount(amount)
                    .paymentMethod(PaymentMethod.values()[random.nextInt(PaymentMethod.values().length)])
                    .referenceNumber("CHK-" + String.format("%08d", random.nextInt(99999999)))
                    .notes("Payment made for " + bill.getBillNumber())
                    .account(i % 2 == 0 ? cashAccount : bankAccount)
                    .build();

            payments.add(payment);
        }

        paymentRepository.saveAll(payments);
        log.info("Created {} payments", payments.size());
    }

    private void printSeedingSummary() {
        log.info("=".repeat(80));
        log.info("DATA SEEDING SUMMARY");
        log.info("=".repeat(80));
        log.info("Organization: {} created", organizationRepository.count());
        log.info("Users: {} created (100 anime characters)", userRepository.count());
        log.info("  - One Piece: 25 characters");
        log.info("  - Demon Slayer: 25 characters");
        log.info("  - Naruto: 25 characters");
        log.info("  - Jujutsu Kaisen: 25 characters");
        log.info("Customers: {} created", customerRepository.count());
        log.info("Vendors: {} created", vendorRepository.count());
        log.info("Chart of Accounts: {} accounts created", accountRepository.count());
        log.info("  - Assets: {} accounts", accountRepository.findAll().stream().filter(a -> a.getAccountType() == AccountType.ASSET).count());
        log.info("  - Liabilities: {} accounts", accountRepository.findAll().stream().filter(a -> a.getAccountType() == AccountType.LIABILITY).count());
        log.info("  - Equity: {} accounts", accountRepository.findAll().stream().filter(a -> a.getAccountType() == AccountType.EQUITY).count());
        log.info("  - Revenue: {} accounts", accountRepository.findAll().stream().filter(a -> a.getAccountType() == AccountType.REVENUE).count());
        log.info("  - Expenses: {} accounts", accountRepository.findAll().stream().filter(a -> a.getAccountType() == AccountType.EXPENSE).count());
        log.info("Tax Rates: {} created", taxRepository.count());
        log.info("Items/Products: {} created", itemRepository.count());
        log.info("Invoices: {} created with line items", invoiceRepository.count());
        log.info("Bills: {} created with line items", billRepository.count());
        log.info("Payments: {} created", paymentRepository.count());
        log.info("  - Payments Received: {}", paymentRepository.findAll().stream().filter(p -> p.getPaymentType() == PaymentType.PAYMENT_RECEIVED).count());
        log.info("  - Payments Made: {}", paymentRepository.findAll().stream().filter(p -> p.getPaymentType() == PaymentType.PAYMENT_MADE).count());
        log.info("=".repeat(80));
        log.info("All seed data has been successfully created!");
        log.info("Default password for all users: password123");
        log.info("=".repeat(80));
    }
}
