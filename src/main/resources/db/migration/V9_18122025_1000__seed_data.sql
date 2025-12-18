-- Flyway Migration V9: Seed Data for Accounting Sample Application
-- Compatible with MySQL 9.5.0
-- Date: 18/12/2025 10:00
-- Converts DataSeeder.java to SQL

-- =====================================================
-- ORGANIZATION
-- =====================================================
INSERT INTO organizations (name, legal_name, email, phone, website, tax_id, registration_number,
                           currency_code, fiscal_year_start_month, date_format, time_zone,
                           address_line1, address_line2, city, state, postal_code, country,
                           is_active, subscription_plan)
VALUES ('Anime Accounting Corp', 'Anime Accounting Corporation Ltd.', 'info@animeaccounting.com',
        '+1-555-ANIME-01', 'https://www.animeaccounting.com', 'TAX-ANIME-2025', 'REG-AA-100001',
        'USD', 1, 'MM/dd/yyyy', 'America/New_York',
        '123 Anime Street', 'Suite 100', 'Tokyo', 'Tokyo', '100-0001', 'Japan',
        true, 'PREMIUM');

SET @org_id = LAST_INSERT_ID();

-- =====================================================
-- USERS (100 anime characters)
-- Correct BCrypt hash for 'password123' (verified with Spring Security BCryptPasswordEncoder)
-- =====================================================
SET @password_hash = '$2a$10$g0iCxs5ro9/8moFrPHlmju5OWj1sSvJuCNWXVoDonvj8/NOAW54hO';

-- One Piece characters (25)
INSERT INTO users (organization_id, email, password_hash, first_name, last_name, phone, role, is_active, is_email_verified) VALUES
(@org_id, 'luffy.onepiece@animeaccounting.com', @password_hash, 'Luffy', 'Onepiece', '+1-555-0001', 'OWNER', true, true),
(@org_id, 'zoro.onepiece@animeaccounting.com', @password_hash, 'Zoro', 'Onepiece', '+1-555-0002', 'ADMIN', true, true),
(@org_id, 'nami.onepiece@animeaccounting.com', @password_hash, 'Nami', 'Onepiece', '+1-555-0003', 'ACCOUNTANT', true, true),
(@org_id, 'sanji.onepiece@animeaccounting.com', @password_hash, 'Sanji', 'Onepiece', '+1-555-0004', 'USER', true, true),
(@org_id, 'usopp.onepiece@animeaccounting.com', @password_hash, 'Usopp', 'Onepiece', '+1-555-0005', 'VIEWER', true, true),
(@org_id, 'robin.onepiece@animeaccounting.com', @password_hash, 'Robin', 'Onepiece', '+1-555-0006', 'OWNER', true, true),
(@org_id, 'chopper.onepiece@animeaccounting.com', @password_hash, 'Chopper', 'Onepiece', '+1-555-0007', 'ADMIN', true, true),
(@org_id, 'franky.onepiece@animeaccounting.com', @password_hash, 'Franky', 'Onepiece', '+1-555-0008', 'ACCOUNTANT', true, true),
(@org_id, 'brook.onepiece@animeaccounting.com', @password_hash, 'Brook', 'Onepiece', '+1-555-0009', 'USER', true, true),
(@org_id, 'jinbe.onepiece@animeaccounting.com', @password_hash, 'Jinbe', 'Onepiece', '+1-555-0010', 'VIEWER', true, true),
(@org_id, 'ace.onepiece@animeaccounting.com', @password_hash, 'Ace', 'Onepiece', '+1-555-0011', 'OWNER', true, true),
(@org_id, 'sabo.onepiece@animeaccounting.com', @password_hash, 'Sabo', 'Onepiece', '+1-555-0012', 'ADMIN', true, true),
(@org_id, 'shanks.onepiece@animeaccounting.com', @password_hash, 'Shanks', 'Onepiece', '+1-555-0013', 'ACCOUNTANT', true, true),
(@org_id, 'law.onepiece@animeaccounting.com', @password_hash, 'Law', 'Onepiece', '+1-555-0014', 'USER', true, true),
(@org_id, 'kid.onepiece@animeaccounting.com', @password_hash, 'Kid', 'Onepiece', '+1-555-0015', 'VIEWER', true, true),
(@org_id, 'whitebeard.onepiece@animeaccounting.com', @password_hash, 'Whitebeard', 'Onepiece', '+1-555-0016', 'OWNER', true, true),
(@org_id, 'roger.onepiece@animeaccounting.com', @password_hash, 'Roger', 'Onepiece', '+1-555-0017', 'ADMIN', true, true),
(@org_id, 'rayleigh.onepiece@animeaccounting.com', @password_hash, 'Rayleigh', 'Onepiece', '+1-555-0018', 'ACCOUNTANT', true, true),
(@org_id, 'mihawk.onepiece@animeaccounting.com', @password_hash, 'Mihawk', 'Onepiece', '+1-555-0019', 'USER', true, true),
(@org_id, 'crocodile.onepiece@animeaccounting.com', @password_hash, 'Crocodile', 'Onepiece', '+1-555-0020', 'VIEWER', true, true),
(@org_id, 'doflamingo.onepiece@animeaccounting.com', @password_hash, 'Doflamingo', 'Onepiece', '+1-555-0021', 'OWNER', true, true),
(@org_id, 'katakuri.onepiece@animeaccounting.com', @password_hash, 'Katakuri', 'Onepiece', '+1-555-0022', 'ADMIN', true, true),
(@org_id, 'bigmom.onepiece@animeaccounting.com', @password_hash, 'Big Mom', 'Onepiece', '+1-555-0023', 'ACCOUNTANT', true, true),
(@org_id, 'kaido.onepiece@animeaccounting.com', @password_hash, 'Kaido', 'Onepiece', '+1-555-0024', 'USER', true, true),
(@org_id, 'blackbeard.onepiece@animeaccounting.com', @password_hash, 'Blackbeard', 'Onepiece', '+1-555-0025', 'VIEWER', true, true);

-- Demon Slayer characters (25)
INSERT INTO users (organization_id, email, password_hash, first_name, last_name, phone, role, is_active, is_email_verified) VALUES
(@org_id, 'tanjiro.demonslayer@animeaccounting.com', @password_hash, 'Tanjiro', 'Demonslayer', '+1-555-0026', 'OWNER', true, true),
(@org_id, 'nezuko.demonslayer@animeaccounting.com', @password_hash, 'Nezuko', 'Demonslayer', '+1-555-0027', 'ADMIN', true, true),
(@org_id, 'zenitsu.demonslayer@animeaccounting.com', @password_hash, 'Zenitsu', 'Demonslayer', '+1-555-0028', 'ACCOUNTANT', true, true),
(@org_id, 'inosuke.demonslayer@animeaccounting.com', @password_hash, 'Inosuke', 'Demonslayer', '+1-555-0029', 'USER', true, true),
(@org_id, 'kanao.demonslayer@animeaccounting.com', @password_hash, 'Kanao', 'Demonslayer', '+1-555-0030', 'VIEWER', true, true),
(@org_id, 'shinobu.demonslayer@animeaccounting.com', @password_hash, 'Shinobu', 'Demonslayer', '+1-555-0031', 'OWNER', true, true),
(@org_id, 'giyu.demonslayer@animeaccounting.com', @password_hash, 'Giyu', 'Demonslayer', '+1-555-0032', 'ADMIN', true, true),
(@org_id, 'rengoku.demonslayer@animeaccounting.com', @password_hash, 'Rengoku', 'Demonslayer', '+1-555-0033', 'ACCOUNTANT', true, true),
(@org_id, 'tengen.demonslayer@animeaccounting.com', @password_hash, 'Tengen', 'Demonslayer', '+1-555-0034', 'USER', true, true),
(@org_id, 'mitsuri.demonslayer@animeaccounting.com', @password_hash, 'Mitsuri', 'Demonslayer', '+1-555-0035', 'VIEWER', true, true),
(@org_id, 'obanai.demonslayer@animeaccounting.com', @password_hash, 'Obanai', 'Demonslayer', '+1-555-0036', 'OWNER', true, true),
(@org_id, 'sanemi.demonslayer@animeaccounting.com', @password_hash, 'Sanemi', 'Demonslayer', '+1-555-0037', 'ADMIN', true, true),
(@org_id, 'gyomei.demonslayer@animeaccounting.com', @password_hash, 'Gyomei', 'Demonslayer', '+1-555-0038', 'ACCOUNTANT', true, true),
(@org_id, 'muichiro.demonslayer@animeaccounting.com', @password_hash, 'Muichiro', 'Demonslayer', '+1-555-0039', 'USER', true, true),
(@org_id, 'muzan.demonslayer@animeaccounting.com', @password_hash, 'Muzan', 'Demonslayer', '+1-555-0040', 'VIEWER', true, true),
(@org_id, 'kokushibo.demonslayer@animeaccounting.com', @password_hash, 'Kokushibo', 'Demonslayer', '+1-555-0041', 'OWNER', true, true),
(@org_id, 'doma.demonslayer@animeaccounting.com', @password_hash, 'Doma', 'Demonslayer', '+1-555-0042', 'ADMIN', true, true),
(@org_id, 'akaza.demonslayer@animeaccounting.com', @password_hash, 'Akaza', 'Demonslayer', '+1-555-0043', 'ACCOUNTANT', true, true),
(@org_id, 'gyutaro.demonslayer@animeaccounting.com', @password_hash, 'Gyutaro', 'Demonslayer', '+1-555-0044', 'USER', true, true),
(@org_id, 'daki.demonslayer@animeaccounting.com', @password_hash, 'Daki', 'Demonslayer', '+1-555-0045', 'VIEWER', true, true),
(@org_id, 'enmu.demonslayer@animeaccounting.com', @password_hash, 'Enmu', 'Demonslayer', '+1-555-0046', 'OWNER', true, true),
(@org_id, 'rui.demonslayer@animeaccounting.com', @password_hash, 'Rui', 'Demonslayer', '+1-555-0047', 'ADMIN', true, true),
(@org_id, 'kaigaku.demonslayer@animeaccounting.com', @password_hash, 'Kaigaku', 'Demonslayer', '+1-555-0048', 'ACCOUNTANT', true, true),
(@org_id, 'nakime.demonslayer@animeaccounting.com', @password_hash, 'Nakime', 'Demonslayer', '+1-555-0049', 'USER', true, true),
(@org_id, 'hantengu.demonslayer@animeaccounting.com', @password_hash, 'Hantengu', 'Demonslayer', '+1-555-0050', 'VIEWER', true, true);

-- Naruto characters (25)
INSERT INTO users (organization_id, email, password_hash, first_name, last_name, phone, role, is_active, is_email_verified) VALUES
(@org_id, 'naruto.naruto@animeaccounting.com', @password_hash, 'Naruto', 'Naruto', '+1-555-0051', 'OWNER', true, true),
(@org_id, 'sasuke.naruto@animeaccounting.com', @password_hash, 'Sasuke', 'Naruto', '+1-555-0052', 'ADMIN', true, true),
(@org_id, 'sakura.naruto@animeaccounting.com', @password_hash, 'Sakura', 'Naruto', '+1-555-0053', 'ACCOUNTANT', true, true),
(@org_id, 'kakashi.naruto@animeaccounting.com', @password_hash, 'Kakashi', 'Naruto', '+1-555-0054', 'USER', true, true),
(@org_id, 'itachi.naruto@animeaccounting.com', @password_hash, 'Itachi', 'Naruto', '+1-555-0055', 'VIEWER', true, true),
(@org_id, 'madara.naruto@animeaccounting.com', @password_hash, 'Madara', 'Naruto', '+1-555-0056', 'OWNER', true, true),
(@org_id, 'obito.naruto@animeaccounting.com', @password_hash, 'Obito', 'Naruto', '+1-555-0057', 'ADMIN', true, true),
(@org_id, 'gaara.naruto@animeaccounting.com', @password_hash, 'Gaara', 'Naruto', '+1-555-0058', 'ACCOUNTANT', true, true),
(@org_id, 'rocklee.naruto@animeaccounting.com', @password_hash, 'Rock Lee', 'Naruto', '+1-555-0059', 'USER', true, true),
(@org_id, 'neji.naruto@animeaccounting.com', @password_hash, 'Neji', 'Naruto', '+1-555-0060', 'VIEWER', true, true),
(@org_id, 'hinata.naruto@animeaccounting.com', @password_hash, 'Hinata', 'Naruto', '+1-555-0061', 'OWNER', true, true),
(@org_id, 'shikamaru.naruto@animeaccounting.com', @password_hash, 'Shikamaru', 'Naruto', '+1-555-0062', 'ADMIN', true, true),
(@org_id, 'jiraiya.naruto@animeaccounting.com', @password_hash, 'Jiraiya', 'Naruto', '+1-555-0063', 'ACCOUNTANT', true, true),
(@org_id, 'tsunade.naruto@animeaccounting.com', @password_hash, 'Tsunade', 'Naruto', '+1-555-0064', 'USER', true, true),
(@org_id, 'orochimaru.naruto@animeaccounting.com', @password_hash, 'Orochimaru', 'Naruto', '+1-555-0065', 'VIEWER', true, true),
(@org_id, 'minato.naruto@animeaccounting.com', @password_hash, 'Minato', 'Naruto', '+1-555-0066', 'OWNER', true, true),
(@org_id, 'hashirama.naruto@animeaccounting.com', @password_hash, 'Hashirama', 'Naruto', '+1-555-0067', 'ADMIN', true, true),
(@org_id, 'tobirama.naruto@animeaccounting.com', @password_hash, 'Tobirama', 'Naruto', '+1-555-0068', 'ACCOUNTANT', true, true),
(@org_id, 'pain.naruto@animeaccounting.com', @password_hash, 'Pain', 'Naruto', '+1-555-0069', 'USER', true, true),
(@org_id, 'konan.naruto@animeaccounting.com', @password_hash, 'Konan', 'Naruto', '+1-555-0070', 'VIEWER', true, true),
(@org_id, 'deidara.naruto@animeaccounting.com', @password_hash, 'Deidara', 'Naruto', '+1-555-0071', 'OWNER', true, true),
(@org_id, 'kisame.naruto@animeaccounting.com', @password_hash, 'Kisame', 'Naruto', '+1-555-0072', 'ADMIN', true, true),
(@org_id, 'hidan.naruto@animeaccounting.com', @password_hash, 'Hidan', 'Naruto', '+1-555-0073', 'ACCOUNTANT', true, true),
(@org_id, 'zabuza.naruto@animeaccounting.com', @password_hash, 'Zabuza', 'Naruto', '+1-555-0074', 'USER', true, true),
(@org_id, 'kimimaro.naruto@animeaccounting.com', @password_hash, 'Kimimaro', 'Naruto', '+1-555-0075', 'VIEWER', true, true);

-- Jujutsu Kaisen characters (25)
INSERT INTO users (organization_id, email, password_hash, first_name, last_name, phone, role, is_active, is_email_verified) VALUES
(@org_id, 'yuji.jjk@animeaccounting.com', @password_hash, 'Yuji', 'Jjk', '+1-555-0076', 'OWNER', true, true),
(@org_id, 'megumi.jjk@animeaccounting.com', @password_hash, 'Megumi', 'Jjk', '+1-555-0077', 'ADMIN', true, true),
(@org_id, 'nobara.jjk@animeaccounting.com', @password_hash, 'Nobara', 'Jjk', '+1-555-0078', 'ACCOUNTANT', true, true),
(@org_id, 'gojo.jjk@animeaccounting.com', @password_hash, 'Gojo', 'Jjk', '+1-555-0079', 'USER', true, true),
(@org_id, 'nanami.jjk@animeaccounting.com', @password_hash, 'Nanami', 'Jjk', '+1-555-0080', 'VIEWER', true, true),
(@org_id, 'maki.jjk@animeaccounting.com', @password_hash, 'Maki', 'Jjk', '+1-555-0081', 'OWNER', true, true),
(@org_id, 'toge.jjk@animeaccounting.com', @password_hash, 'Toge', 'Jjk', '+1-555-0082', 'ADMIN', true, true),
(@org_id, 'panda.jjk@animeaccounting.com', @password_hash, 'Panda', 'Jjk', '+1-555-0083', 'ACCOUNTANT', true, true),
(@org_id, 'yuta.jjk@animeaccounting.com', @password_hash, 'Yuta', 'Jjk', '+1-555-0084', 'USER', true, true),
(@org_id, 'sukuna.jjk@animeaccounting.com', @password_hash, 'Sukuna', 'Jjk', '+1-555-0085', 'VIEWER', true, true),
(@org_id, 'mahito.jjk@animeaccounting.com', @password_hash, 'Mahito', 'Jjk', '+1-555-0086', 'OWNER', true, true),
(@org_id, 'jogo.jjk@animeaccounting.com', @password_hash, 'Jogo', 'Jjk', '+1-555-0087', 'ADMIN', true, true),
(@org_id, 'hanami.jjk@animeaccounting.com', @password_hash, 'Hanami', 'Jjk', '+1-555-0088', 'ACCOUNTANT', true, true),
(@org_id, 'choso.jjk@animeaccounting.com', @password_hash, 'Choso', 'Jjk', '+1-555-0089', 'USER', true, true),
(@org_id, 'geto.jjk@animeaccounting.com', @password_hash, 'Geto', 'Jjk', '+1-555-0090', 'VIEWER', true, true),
(@org_id, 'toji.jjk@animeaccounting.com', @password_hash, 'Toji', 'Jjk', '+1-555-0091', 'OWNER', true, true),
(@org_id, 'mai.jjk@animeaccounting.com', @password_hash, 'Mai', 'Jjk', '+1-555-0092', 'ADMIN', true, true),
(@org_id, 'mechamaru.jjk@animeaccounting.com', @password_hash, 'Mechamaru', 'Jjk', '+1-555-0093', 'ACCOUNTANT', true, true),
(@org_id, 'todo.jjk@animeaccounting.com', @password_hash, 'Todo', 'Jjk', '+1-555-0094', 'USER', true, true),
(@org_id, 'mei.jjk@animeaccounting.com', @password_hash, 'Mei', 'Jjk', '+1-555-0095', 'VIEWER', true, true),
(@org_id, 'kamo.jjk@animeaccounting.com', @password_hash, 'Kamo', 'Jjk', '+1-555-0096', 'OWNER', true, true),
(@org_id, 'yuki.jjk@animeaccounting.com', @password_hash, 'Yuki', 'Jjk', '+1-555-0097', 'ADMIN', true, true),
(@org_id, 'uraume.jjk@animeaccounting.com', @password_hash, 'Uraume', 'Jjk', '+1-555-0098', 'ACCOUNTANT', true, true),
(@org_id, 'kenjaku.jjk@animeaccounting.com', @password_hash, 'Kenjaku', 'Jjk', '+1-555-0099', 'USER', true, true),
(@org_id, 'hakari.jjk@animeaccounting.com', @password_hash, 'Hakari', 'Jjk', '+1-555-0100', 'VIEWER', true, true);

-- =====================================================
-- CHART OF ACCOUNTS (35 accounts)
-- =====================================================

-- Assets (1000-1999)
INSERT INTO accounts (organization_id, account_code, account_name, account_type, description, current_balance, is_active, is_system, tax_applicable) VALUES
(@org_id, '1000', 'Cash', 'ASSET', 'Cash on hand', 0.0000, true, false, false),
(@org_id, '1010', 'Petty Cash', 'ASSET', 'Petty cash fund', 0.0000, true, false, false),
(@org_id, '1100', 'Bank - Checking Account', 'ASSET', 'Main checking account', 0.0000, true, false, false),
(@org_id, '1110', 'Bank - Savings Account', 'ASSET', 'Savings account', 0.0000, true, false, false),
(@org_id, '1200', 'Accounts Receivable', 'ASSET', 'Money owed by customers', 0.0000, true, false, false),
(@org_id, '1300', 'Inventory', 'ASSET', 'Inventory on hand', 0.0000, true, false, false),
(@org_id, '1400', 'Prepaid Expenses', 'ASSET', 'Prepaid expenses', 0.0000, true, false, false),
(@org_id, '1500', 'Equipment', 'ASSET', 'Office and business equipment', 0.0000, true, false, false),
(@org_id, '1510', 'Accumulated Depreciation - Equipment', 'ASSET', 'Accumulated depreciation', 0.0000, true, false, false),
(@org_id, '1600', 'Furniture & Fixtures', 'ASSET', 'Office furniture', 0.0000, true, false, false);

-- Liabilities (2000-2999)
INSERT INTO accounts (organization_id, account_code, account_name, account_type, description, current_balance, is_active, is_system, tax_applicable) VALUES
(@org_id, '2000', 'Accounts Payable', 'LIABILITY', 'Money owed to vendors', 0.0000, true, false, false),
(@org_id, '2100', 'Credit Card Payable', 'LIABILITY', 'Credit card balances', 0.0000, true, false, false),
(@org_id, '2200', 'Sales Tax Payable', 'LIABILITY', 'Sales tax collected', 0.0000, true, false, false),
(@org_id, '2300', 'Payroll Tax Payable', 'LIABILITY', 'Payroll taxes payable', 0.0000, true, false, false),
(@org_id, '2400', 'Short-term Loan', 'LIABILITY', 'Short-term loans', 0.0000, true, false, false),
(@org_id, '2500', 'Long-term Loan', 'LIABILITY', 'Long-term loans', 0.0000, true, false, false),
(@org_id, '2600', 'Accrued Expenses', 'LIABILITY', 'Accrued expenses', 0.0000, true, false, false);

-- Equity (3000-3999)
INSERT INTO accounts (organization_id, account_code, account_name, account_type, description, current_balance, is_active, is_system, tax_applicable) VALUES
(@org_id, '3000', 'Owner''s Equity', 'EQUITY', 'Owner''s equity', 0.0000, true, false, false),
(@org_id, '3100', 'Retained Earnings', 'EQUITY', 'Retained earnings', 0.0000, true, false, false),
(@org_id, '3200', 'Common Stock', 'EQUITY', 'Common stock', 0.0000, true, false, false),
(@org_id, '3300', 'Dividends', 'EQUITY', 'Dividends paid', 0.0000, true, false, false);

-- Revenue (4000-4999)
INSERT INTO accounts (organization_id, account_code, account_name, account_type, description, current_balance, is_active, is_system, tax_applicable) VALUES
(@org_id, '4000', 'Sales Revenue', 'REVENUE', 'Sales of goods', 0.0000, true, false, false),
(@org_id, '4100', 'Service Revenue', 'REVENUE', 'Service income', 0.0000, true, false, false),
(@org_id, '4200', 'Interest Income', 'REVENUE', 'Interest earned', 0.0000, true, false, false),
(@org_id, '4300', 'Other Income', 'REVENUE', 'Miscellaneous income', 0.0000, true, false, false);

-- Expenses (5000-5999)
INSERT INTO accounts (organization_id, account_code, account_name, account_type, description, current_balance, is_active, is_system, tax_applicable) VALUES
(@org_id, '5000', 'Cost of Goods Sold', 'COST_OF_GOODS_SOLD', 'Direct costs', 0.0000, true, false, false),
(@org_id, '5100', 'Salaries & Wages', 'EXPENSE', 'Employee salaries', 0.0000, true, false, false),
(@org_id, '5200', 'Rent Expense', 'EXPENSE', 'Office rent', 0.0000, true, false, false),
(@org_id, '5300', 'Utilities Expense', 'EXPENSE', 'Electricity, water, internet', 0.0000, true, false, false),
(@org_id, '5400', 'Office Supplies', 'EXPENSE', 'Office supplies', 0.0000, true, false, false),
(@org_id, '5500', 'Insurance Expense', 'EXPENSE', 'Business insurance', 0.0000, true, false, false),
(@org_id, '5600', 'Marketing & Advertising', 'EXPENSE', 'Marketing costs', 0.0000, true, false, false),
(@org_id, '5700', 'Travel & Entertainment', 'EXPENSE', 'Business travel', 0.0000, true, false, false),
(@org_id, '5800', 'Depreciation Expense', 'EXPENSE', 'Depreciation', 0.0000, true, false, false),
(@org_id, '5900', 'Bank Fees', 'EXPENSE', 'Bank charges', 0.0000, true, false, false),
(@org_id, '5950', 'Miscellaneous Expense', 'EXPENSE', 'Other expenses', 0.0000, true, false, false);

-- =====================================================
-- TAX RATES (20 taxes)
-- =====================================================
INSERT INTO tax_rates (organization_id, tax_code, tax_name, tax_type, tax_percentage, description, is_active, is_compound) VALUES
(@org_id, 'VAT-10', 'VAT 10%', 'VAT', 10.00, 'Value Added Tax 10%', true, false),
(@org_id, 'VAT-15', 'VAT 15%', 'VAT', 15.00, 'Value Added Tax 15%', true, false),
(@org_id, 'VAT-20', 'VAT 20%', 'VAT', 20.00, 'Value Added Tax 20%', true, false),
(@org_id, 'GST-5', 'GST 5%', 'GST', 5.00, 'Goods and Services Tax 5%', true, false),
(@org_id, 'GST-10', 'GST 10%', 'GST', 10.00, 'Goods and Services Tax 10%', true, false),
(@org_id, 'GST-15', 'GST 15%', 'GST', 15.00, 'Goods and Services Tax 15%', true, false),
(@org_id, 'SALES-6', 'Sales Tax 6%', 'SALES', 6.00, 'Sales Tax 6%', true, false),
(@org_id, 'SALES-7', 'Sales Tax 7%', 'SALES', 7.00, 'Sales Tax 7%', true, false),
(@org_id, 'SALES-8', 'Sales Tax 8%', 'SALES', 8.00, 'Sales Tax 8%', true, false),
(@org_id, 'SALES-9', 'Sales Tax 9%', 'SALES', 9.00, 'Sales Tax 9%', true, false),
(@org_id, 'EXEMPT', 'Tax Exempt', 'EXEMPT', 0.00, 'No tax applicable', true, false),
(@org_id, 'LUXURY-25', 'Luxury Tax 25%', 'LUXURY', 25.00, 'Luxury goods tax', true, false),
(@org_id, 'EXCISE-5', 'Excise Tax 5%', 'EXCISE', 5.00, 'Excise tax', true, false),
(@org_id, 'EXCISE-10', 'Excise Tax 10%', 'EXCISE', 10.00, 'Excise tax 10%', true, false),
(@org_id, 'SERVICE-12', 'Service Tax 12%', 'SERVICE', 12.00, 'Service tax', true, false),
(@org_id, 'SERVICE-15', 'Service Tax 15%', 'SERVICE', 15.00, 'Service tax 15%', true, false),
(@org_id, 'WITHHOLD-10', 'Withholding Tax 10%', 'WITHHOLDING', 10.00, 'Withholding tax', true, false),
(@org_id, 'IMPORT-5', 'Import Duty 5%', 'IMPORT', 5.00, 'Import duty', true, false),
(@org_id, 'IMPORT-15', 'Import Duty 15%', 'IMPORT', 15.00, 'Import duty 15%', true, false),
(@org_id, 'COMPOSITE-18', 'Composite Tax 18%', 'COMPOSITE', 18.00, 'Composite tax rate', true, false);

-- =====================================================
-- CUSTOMERS (50 customers)
-- =====================================================
INSERT INTO customers (organization_id, customer_code, display_name, company_name, email, phone, mobile, website,
                       tax_id, payment_terms, credit_limit, billing_address_line1, billing_city, billing_state,
                       billing_postal_code, billing_country, shipping_address_line1, shipping_city, shipping_state,
                       shipping_postal_code, shipping_country, is_active, outstanding_balance) VALUES
(@org_id, 'CUST-0001', 'Luffy', 'Luffy Corp', 'luffy.customer@example.com', '+1-555-1001', '+1-555-2001', 'https://www.luffy.com', 'TAX-00001', 30, 35000.0000, '101 Customer Street', 'New York', 'NY', '10001', 'USA', '201 Shipping Lane', 'New York', 'NY', '20001', 'USA', true, 0.0000),
(@org_id, 'CUST-0002', 'Zoro', 'Zoro Industries', 'zoro.customer@example.com', '+1-555-1002', '+1-555-2002', 'https://www.zoro.com', 'TAX-00002', 30, 42000.0000, '102 Customer Street', 'New York', 'NY', '10002', 'USA', '202 Shipping Lane', 'New York', 'NY', '20002', 'USA', true, 0.0000),
(@org_id, 'CUST-0003', 'Nami', 'Nami Trading Co.', 'nami.customer@example.com', '+1-555-1003', '+1-555-2003', 'https://www.nami.com', 'TAX-00003', 30, 28000.0000, '103 Customer Street', 'New York', 'NY', '10003', 'USA', '203 Shipping Lane', 'New York', 'NY', '20003', 'USA', true, 0.0000),
(@org_id, 'CUST-0004', 'Sanji', 'Sanji Ltd', 'sanji.customer@example.com', '+1-555-1004', '+1-555-2004', 'https://www.sanji.com', 'TAX-00004', 30, 55000.0000, '104 Customer Street', 'New York', 'NY', '10004', 'USA', '204 Shipping Lane', 'New York', 'NY', '20004', 'USA', true, 0.0000),
(@org_id, 'CUST-0005', 'Usopp', 'Usopp Inc', 'usopp.customer@example.com', '+1-555-1005', '+1-555-2005', 'https://www.usopp.com', 'TAX-00005', 30, 33000.0000, '105 Customer Street', 'New York', 'NY', '10005', 'USA', '205 Shipping Lane', 'New York', 'NY', '20005', 'USA', true, 0.0000),
(@org_id, 'CUST-0006', 'Tanjiro', 'Tanjiro Enterprises', 'tanjiro.customer@example.com', '+1-555-1006', '+1-555-2006', 'https://www.tanjiro.com', 'TAX-00006', 30, 47000.0000, '106 Customer Street', 'New York', 'NY', '10006', 'USA', '206 Shipping Lane', 'New York', 'NY', '20006', 'USA', true, 0.0000),
(@org_id, 'CUST-0007', 'Nezuko', 'Nezuko Group', 'nezuko.customer@example.com', '+1-555-1007', '+1-555-2007', 'https://www.nezuko.com', 'TAX-00007', 30, 38000.0000, '107 Customer Street', 'New York', 'NY', '10007', 'USA', '207 Shipping Lane', 'New York', 'NY', '20007', 'USA', true, 0.0000),
(@org_id, 'CUST-0008', 'Zenitsu', 'Zenitsu Holdings', 'zenitsu.customer@example.com', '+1-555-1008', '+1-555-2008', 'https://www.zenitsu.com', 'TAX-00008', 30, 51000.0000, '108 Customer Street', 'New York', 'NY', '10008', 'USA', '208 Shipping Lane', 'New York', 'NY', '20008', 'USA', true, 0.0000),
(@org_id, 'CUST-0009', 'Inosuke', 'Inosuke Solutions', 'inosuke.customer@example.com', '+1-555-1009', '+1-555-2009', 'https://www.inosuke.com', 'TAX-00009', 30, 29000.0000, '109 Customer Street', 'New York', 'NY', '10009', 'USA', '209 Shipping Lane', 'New York', 'NY', '20009', 'USA', true, 0.0000),
(@org_id, 'CUST-0010', 'Rengoku', 'Rengoku Technologies', 'rengoku.customer@example.com', '+1-555-1010', '+1-555-2010', 'https://www.rengoku.com', 'TAX-00010', 30, 44000.0000, '110 Customer Street', 'New York', 'NY', '10010', 'USA', '210 Shipping Lane', 'New York', 'NY', '20010', 'USA', true, 0.0000),
(@org_id, 'CUST-0011', 'Naruto', 'Naruto Corp', 'naruto.customer@example.com', '+1-555-1011', '+1-555-2011', 'https://www.naruto.com', 'TAX-00011', 30, 36000.0000, '111 Customer Street', 'New York', 'NY', '10011', 'USA', '211 Shipping Lane', 'New York', 'NY', '20011', 'USA', true, 0.0000),
(@org_id, 'CUST-0012', 'Sasuke', 'Sasuke Industries', 'sasuke.customer@example.com', '+1-555-1012', '+1-555-2012', 'https://www.sasuke.com', 'TAX-00012', 30, 48000.0000, '112 Customer Street', 'New York', 'NY', '10012', 'USA', '212 Shipping Lane', 'New York', 'NY', '20012', 'USA', true, 0.0000),
(@org_id, 'CUST-0013', 'Sakura', 'Sakura Trading Co.', 'sakura.customer@example.com', '+1-555-1013', '+1-555-2013', 'https://www.sakura.com', 'TAX-00013', 30, 31000.0000, '113 Customer Street', 'New York', 'NY', '10013', 'USA', '213 Shipping Lane', 'New York', 'NY', '20013', 'USA', true, 0.0000),
(@org_id, 'CUST-0014', 'Kakashi', 'Kakashi Ltd', 'kakashi.customer@example.com', '+1-555-1014', '+1-555-2014', 'https://www.kakashi.com', 'TAX-00014', 30, 53000.0000, '114 Customer Street', 'New York', 'NY', '10014', 'USA', '214 Shipping Lane', 'New York', 'NY', '20014', 'USA', true, 0.0000),
(@org_id, 'CUST-0015', 'Itachi', 'Itachi Inc', 'itachi.customer@example.com', '+1-555-1015', '+1-555-2015', 'https://www.itachi.com', 'TAX-00015', 30, 39000.0000, '115 Customer Street', 'New York', 'NY', '10015', 'USA', '215 Shipping Lane', 'New York', 'NY', '20015', 'USA', true, 0.0000),
(@org_id, 'CUST-0016', 'Yuji', 'Yuji Enterprises', 'yuji.customer@example.com', '+1-555-1016', '+1-555-2016', 'https://www.yuji.com', 'TAX-00016', 30, 46000.0000, '116 Customer Street', 'New York', 'NY', '10016', 'USA', '216 Shipping Lane', 'New York', 'NY', '20016', 'USA', true, 0.0000),
(@org_id, 'CUST-0017', 'Megumi', 'Megumi Group', 'megumi.customer@example.com', '+1-555-1017', '+1-555-2017', 'https://www.megumi.com', 'TAX-00017', 30, 34000.0000, '117 Customer Street', 'New York', 'NY', '10017', 'USA', '217 Shipping Lane', 'New York', 'NY', '20017', 'USA', true, 0.0000),
(@org_id, 'CUST-0018', 'Nobara', 'Nobara Holdings', 'nobara.customer@example.com', '+1-555-1018', '+1-555-2018', 'https://www.nobara.com', 'TAX-00018', 30, 57000.0000, '118 Customer Street', 'New York', 'NY', '10018', 'USA', '218 Shipping Lane', 'New York', 'NY', '20018', 'USA', true, 0.0000),
(@org_id, 'CUST-0019', 'Gojo', 'Gojo Solutions', 'gojo.customer@example.com', '+1-555-1019', '+1-555-2019', 'https://www.gojo.com', 'TAX-00019', 30, 27000.0000, '119 Customer Street', 'New York', 'NY', '10019', 'USA', '219 Shipping Lane', 'New York', 'NY', '20019', 'USA', true, 0.0000),
(@org_id, 'CUST-0020', 'Nanami', 'Nanami Technologies', 'nanami.customer@example.com', '+1-555-1020', '+1-555-2020', 'https://www.nanami.com', 'TAX-00020', 30, 43000.0000, '120 Customer Street', 'New York', 'NY', '10020', 'USA', '220 Shipping Lane', 'New York', 'NY', '20020', 'USA', true, 0.0000),
(@org_id, 'CUST-0021', 'Shanks', 'Shanks Corp', 'shanks.customer@example.com', '+1-555-1021', '+1-555-2021', 'https://www.shanks.com', 'TAX-00021', 30, 50000.0000, '121 Customer Street', 'New York', 'NY', '10021', 'USA', '221 Shipping Lane', 'New York', 'NY', '20021', 'USA', true, 0.0000),
(@org_id, 'CUST-0022', 'Law', 'Law Industries', 'law.customer@example.com', '+1-555-1022', '+1-555-2022', 'https://www.law.com', 'TAX-00022', 30, 32000.0000, '122 Customer Street', 'New York', 'NY', '10022', 'USA', '222 Shipping Lane', 'New York', 'NY', '20022', 'USA', true, 0.0000),
(@org_id, 'CUST-0023', 'Kid', 'Kid Trading Co.', 'kid.customer@example.com', '+1-555-1023', '+1-555-2023', 'https://www.kid.com', 'TAX-00023', 30, 45000.0000, '123 Customer Street', 'New York', 'NY', '10023', 'USA', '223 Shipping Lane', 'New York', 'NY', '20023', 'USA', true, 0.0000),
(@org_id, 'CUST-0024', 'Ace', 'Ace Ltd', 'ace.customer@example.com', '+1-555-1024', '+1-555-2024', 'https://www.ace.com', 'TAX-00024', 30, 37000.0000, '124 Customer Street', 'New York', 'NY', '10024', 'USA', '224 Shipping Lane', 'New York', 'NY', '20024', 'USA', true, 0.0000),
(@org_id, 'CUST-0025', 'Sabo', 'Sabo Inc', 'sabo.customer@example.com', '+1-555-1025', '+1-555-2025', 'https://www.sabo.com', 'TAX-00025', 30, 54000.0000, '125 Customer Street', 'New York', 'NY', '10025', 'USA', '225 Shipping Lane', 'New York', 'NY', '20025', 'USA', true, 0.0000),
(@org_id, 'CUST-0026', 'Shinobu', 'Shinobu Enterprises', 'shinobu.customer@example.com', '+1-555-1026', '+1-555-2026', 'https://www.shinobu.com', 'TAX-00026', 30, 40000.0000, '126 Customer Street', 'New York', 'NY', '10026', 'USA', '226 Shipping Lane', 'New York', 'NY', '20026', 'USA', true, 0.0000),
(@org_id, 'CUST-0027', 'Giyu', 'Giyu Group', 'giyu.customer@example.com', '+1-555-1027', '+1-555-2027', 'https://www.giyu.com', 'TAX-00027', 30, 49000.0000, '127 Customer Street', 'New York', 'NY', '10027', 'USA', '227 Shipping Lane', 'New York', 'NY', '20027', 'USA', true, 0.0000),
(@org_id, 'CUST-0028', 'Mitsuri', 'Mitsuri Holdings', 'mitsuri.customer@example.com', '+1-555-1028', '+1-555-2028', 'https://www.mitsuri.com', 'TAX-00028', 30, 30000.0000, '128 Customer Street', 'New York', 'NY', '10028', 'USA', '228 Shipping Lane', 'New York', 'NY', '20028', 'USA', true, 0.0000),
(@org_id, 'CUST-0029', 'Tengen', 'Tengen Solutions', 'tengen.customer@example.com', '+1-555-1029', '+1-555-2029', 'https://www.tengen.com', 'TAX-00029', 30, 52000.0000, '129 Customer Street', 'New York', 'NY', '10029', 'USA', '229 Shipping Lane', 'New York', 'NY', '20029', 'USA', true, 0.0000),
(@org_id, 'CUST-0030', 'Gyomei', 'Gyomei Technologies', 'gyomei.customer@example.com', '+1-555-1030', '+1-555-2030', 'https://www.gyomei.com', 'TAX-00030', 30, 41000.0000, '130 Customer Street', 'New York', 'NY', '10030', 'USA', '230 Shipping Lane', 'New York', 'NY', '20030', 'USA', true, 0.0000),
(@org_id, 'CUST-0031', 'Madara', 'Madara Corp', 'madara.customer@example.com', '+1-555-1031', '+1-555-2031', 'https://www.madara.com', 'TAX-00031', 30, 56000.0000, '131 Customer Street', 'New York', 'NY', '10031', 'USA', '231 Shipping Lane', 'New York', 'NY', '20031', 'USA', true, 0.0000),
(@org_id, 'CUST-0032', 'Obito', 'Obito Industries', 'obito.customer@example.com', '+1-555-1032', '+1-555-2032', 'https://www.obito.com', 'TAX-00032', 30, 26000.0000, '132 Customer Street', 'New York', 'NY', '10032', 'USA', '232 Shipping Lane', 'New York', 'NY', '20032', 'USA', true, 0.0000),
(@org_id, 'CUST-0033', 'Gaara', 'Gaara Trading Co.', 'gaara.customer@example.com', '+1-555-1033', '+1-555-2033', 'https://www.gaara.com', 'TAX-00033', 30, 58000.0000, '133 Customer Street', 'New York', 'NY', '10033', 'USA', '233 Shipping Lane', 'New York', 'NY', '20033', 'USA', true, 0.0000),
(@org_id, 'CUST-0034', 'Jiraiya', 'Jiraiya Ltd', 'jiraiya.customer@example.com', '+1-555-1034', '+1-555-2034', 'https://www.jiraiya.com', 'TAX-00034', 30, 35000.0000, '134 Customer Street', 'New York', 'NY', '10034', 'USA', '234 Shipping Lane', 'New York', 'NY', '20034', 'USA', true, 0.0000),
(@org_id, 'CUST-0035', 'Tsunade', 'Tsunade Inc', 'tsunade.customer@example.com', '+1-555-1035', '+1-555-2035', 'https://www.tsunade.com', 'TAX-00035', 30, 47000.0000, '135 Customer Street', 'New York', 'NY', '10035', 'USA', '235 Shipping Lane', 'New York', 'NY', '20035', 'USA', true, 0.0000),
(@org_id, 'CUST-0036', 'Sukuna', 'Sukuna Enterprises', 'sukuna.customer@example.com', '+1-555-1036', '+1-555-2036', 'https://www.sukuna.com', 'TAX-00036', 30, 33000.0000, '136 Customer Street', 'New York', 'NY', '10036', 'USA', '236 Shipping Lane', 'New York', 'NY', '20036', 'USA', true, 0.0000),
(@org_id, 'CUST-0037', 'Mahito', 'Mahito Group', 'mahito.customer@example.com', '+1-555-1037', '+1-555-2037', 'https://www.mahito.com', 'TAX-00037', 30, 44000.0000, '137 Customer Street', 'New York', 'NY', '10037', 'USA', '237 Shipping Lane', 'New York', 'NY', '20037', 'USA', true, 0.0000),
(@org_id, 'CUST-0038', 'Geto', 'Geto Holdings', 'geto.customer@example.com', '+1-555-1038', '+1-555-2038', 'https://www.geto.com', 'TAX-00038', 30, 51000.0000, '138 Customer Street', 'New York', 'NY', '10038', 'USA', '238 Shipping Lane', 'New York', 'NY', '20038', 'USA', true, 0.0000),
(@org_id, 'CUST-0039', 'Yuta', 'Yuta Solutions', 'yuta.customer@example.com', '+1-555-1039', '+1-555-2039', 'https://www.yuta.com', 'TAX-00039', 30, 29000.0000, '139 Customer Street', 'New York', 'NY', '10039', 'USA', '239 Shipping Lane', 'New York', 'NY', '20039', 'USA', true, 0.0000),
(@org_id, 'CUST-0040', 'Maki', 'Maki Technologies', 'maki.customer@example.com', '+1-555-1040', '+1-555-2040', 'https://www.maki.com', 'TAX-00040', 30, 38000.0000, '140 Customer Street', 'New York', 'NY', '10040', 'USA', '240 Shipping Lane', 'New York', 'NY', '20040', 'USA', true, 0.0000),
(@org_id, 'CUST-0041', 'Robin', 'Robin Corp', 'robin.customer@example.com', '+1-555-1041', '+1-555-2041', 'https://www.robin.com', 'TAX-00041', 30, 55000.0000, '141 Customer Street', 'New York', 'NY', '10041', 'USA', '241 Shipping Lane', 'New York', 'NY', '20041', 'USA', true, 0.0000),
(@org_id, 'CUST-0042', 'Franky', 'Franky Industries', 'franky.customer@example.com', '+1-555-1042', '+1-555-2042', 'https://www.franky.com', 'TAX-00042', 30, 42000.0000, '142 Customer Street', 'New York', 'NY', '10042', 'USA', '242 Shipping Lane', 'New York', 'NY', '20042', 'USA', true, 0.0000),
(@org_id, 'CUST-0043', 'Brook', 'Brook Trading Co.', 'brook.customer@example.com', '+1-555-1043', '+1-555-2043', 'https://www.brook.com', 'TAX-00043', 30, 36000.0000, '143 Customer Street', 'New York', 'NY', '10043', 'USA', '243 Shipping Lane', 'New York', 'NY', '20043', 'USA', true, 0.0000),
(@org_id, 'CUST-0044', 'Jinbe', 'Jinbe Ltd', 'jinbe.customer@example.com', '+1-555-1044', '+1-555-2044', 'https://www.jinbe.com', 'TAX-00044', 30, 48000.0000, '144 Customer Street', 'New York', 'NY', '10044', 'USA', '244 Shipping Lane', 'New York', 'NY', '20044', 'USA', true, 0.0000),
(@org_id, 'CUST-0045', 'Roger', 'Roger Inc', 'roger.customer@example.com', '+1-555-1045', '+1-555-2045', 'https://www.roger.com', 'TAX-00045', 30, 31000.0000, '145 Customer Street', 'New York', 'NY', '10045', 'USA', '245 Shipping Lane', 'New York', 'NY', '20045', 'USA', true, 0.0000),
(@org_id, 'CUST-0046', 'Kanao', 'Kanao Enterprises', 'kanao.customer@example.com', '+1-555-1046', '+1-555-2046', 'https://www.kanao.com', 'TAX-00046', 30, 53000.0000, '146 Customer Street', 'New York', 'NY', '10046', 'USA', '246 Shipping Lane', 'New York', 'NY', '20046', 'USA', true, 0.0000),
(@org_id, 'CUST-0047', 'Muichiro', 'Muichiro Group', 'muichiro.customer@example.com', '+1-555-1047', '+1-555-2047', 'https://www.muichiro.com', 'TAX-00047', 30, 39000.0000, '147 Customer Street', 'New York', 'NY', '10047', 'USA', '247 Shipping Lane', 'New York', 'NY', '20047', 'USA', true, 0.0000),
(@org_id, 'CUST-0048', 'Hinata', 'Hinata Holdings', 'hinata.customer@example.com', '+1-555-1048', '+1-555-2048', 'https://www.hinata.com', 'TAX-00048', 30, 46000.0000, '148 Customer Street', 'New York', 'NY', '10048', 'USA', '248 Shipping Lane', 'New York', 'NY', '20048', 'USA', true, 0.0000),
(@org_id, 'CUST-0049', 'Shikamaru', 'Shikamaru Solutions', 'shikamaru.customer@example.com', '+1-555-1049', '+1-555-2049', 'https://www.shikamaru.com', 'TAX-00049', 30, 34000.0000, '149 Customer Street', 'New York', 'NY', '10049', 'USA', '249 Shipping Lane', 'New York', 'NY', '20049', 'USA', true, 0.0000),
(@org_id, 'CUST-0050', 'Toge', 'Toge Technologies', 'toge.customer@example.com', '+1-555-1050', '+1-555-2050', 'https://www.toge.com', 'TAX-00050', 30, 57000.0000, '150 Customer Street', 'New York', 'NY', '10050', 'USA', '250 Shipping Lane', 'New York', 'NY', '20050', 'USA', true, 0.0000);

-- =====================================================
-- VENDORS (50 vendors)
-- =====================================================
INSERT INTO vendors (organization_id, vendor_code, display_name, company_name, email, phone, mobile, website,
                     tax_id, payment_terms, credit_limit, address_line1, city, state, postal_code, country,
                     is_active, outstanding_balance) VALUES
(@org_id, 'VEND-0001', 'Whitebeard', 'Whitebeard Supplies', 'whitebeard.vendor@example.com', '+1-555-3001', '+1-555-4001', 'https://www.whitebeardvendor.com', 'VTAX-00001', 45, 80000.0000, '301 Vendor Avenue', 'Los Angeles', 'CA', '90001', 'USA', true, 0.0000),
(@org_id, 'VEND-0002', 'Kaido', 'Kaido Manufacturing', 'kaido.vendor@example.com', '+1-555-3002', '+1-555-4002', 'https://www.kaidovendor.com', 'VTAX-00002', 45, 95000.0000, '302 Vendor Avenue', 'Los Angeles', 'CA', '90002', 'USA', true, 0.0000),
(@org_id, 'VEND-0003', 'Big Mom', 'Big Mom Wholesale', 'bigmom.vendor@example.com', '+1-555-3003', '+1-555-4003', 'https://www.bigmomvendor.com', 'VTAX-00003', 45, 72000.0000, '303 Vendor Avenue', 'Los Angeles', 'CA', '90003', 'USA', true, 0.0000),
(@org_id, 'VEND-0004', 'Blackbeard', 'Blackbeard Distribution', 'blackbeard.vendor@example.com', '+1-555-3004', '+1-555-4004', 'https://www.blackbeardvendor.com', 'VTAX-00004', 45, 110000.0000, '304 Vendor Avenue', 'Los Angeles', 'CA', '90004', 'USA', true, 0.0000),
(@org_id, 'VEND-0005', 'Mihawk', 'Mihawk Services', 'mihawk.vendor@example.com', '+1-555-3005', '+1-555-4005', 'https://www.mihawkvendor.com', 'VTAX-00005', 45, 67000.0000, '305 Vendor Avenue', 'Los Angeles', 'CA', '90005', 'USA', true, 0.0000),
(@org_id, 'VEND-0006', 'Doflamingo', 'Doflamingo Logistics', 'doflamingo.vendor@example.com', '+1-555-3006', '+1-555-4006', 'https://www.doflamingovendor.com', 'VTAX-00006', 45, 88000.0000, '306 Vendor Avenue', 'Los Angeles', 'CA', '90006', 'USA', true, 0.0000),
(@org_id, 'VEND-0007', 'Crocodile', 'Crocodile Partners', 'crocodile.vendor@example.com', '+1-555-3007', '+1-555-4007', 'https://www.crocodilevendor.com', 'VTAX-00007', 45, 75000.0000, '307 Vendor Avenue', 'Los Angeles', 'CA', '90007', 'USA', true, 0.0000),
(@org_id, 'VEND-0008', 'Katakuri', 'Katakuri Vendors', 'katakuri.vendor@example.com', '+1-555-3008', '+1-555-4008', 'https://www.katakurivendor.com', 'VTAX-00008', 45, 102000.0000, '308 Vendor Avenue', 'Los Angeles', 'CA', '90008', 'USA', true, 0.0000),
(@org_id, 'VEND-0009', 'Rayleigh', 'Rayleigh Imports', 'rayleigh.vendor@example.com', '+1-555-3009', '+1-555-4009', 'https://www.rayleighvendor.com', 'VTAX-00009', 45, 63000.0000, '309 Vendor Avenue', 'Los Angeles', 'CA', '90009', 'USA', true, 0.0000),
(@org_id, 'VEND-0010', 'Roger', 'Roger Exports', 'roger.vendor@example.com', '+1-555-3010', '+1-555-4010', 'https://www.rogervendor.com', 'VTAX-00010', 45, 91000.0000, '310 Vendor Avenue', 'Los Angeles', 'CA', '90010', 'USA', true, 0.0000),
(@org_id, 'VEND-0011', 'Muzan', 'Muzan Supplies', 'muzan.vendor@example.com', '+1-555-3011', '+1-555-4011', 'https://www.muzanvendor.com', 'VTAX-00011', 45, 78000.0000, '311 Vendor Avenue', 'Los Angeles', 'CA', '90011', 'USA', true, 0.0000),
(@org_id, 'VEND-0012', 'Kokushibo', 'Kokushibo Manufacturing', 'kokushibo.vendor@example.com', '+1-555-3012', '+1-555-4012', 'https://www.kokushibovendor.com', 'VTAX-00012', 45, 105000.0000, '312 Vendor Avenue', 'Los Angeles', 'CA', '90012', 'USA', true, 0.0000),
(@org_id, 'VEND-0013', 'Doma', 'Doma Wholesale', 'doma.vendor@example.com', '+1-555-3013', '+1-555-4013', 'https://www.domavendor.com', 'VTAX-00013', 45, 69000.0000, '313 Vendor Avenue', 'Los Angeles', 'CA', '90013', 'USA', true, 0.0000),
(@org_id, 'VEND-0014', 'Akaza', 'Akaza Distribution', 'akaza.vendor@example.com', '+1-555-3014', '+1-555-4014', 'https://www.akazavendor.com', 'VTAX-00014', 45, 115000.0000, '314 Vendor Avenue', 'Los Angeles', 'CA', '90014', 'USA', true, 0.0000),
(@org_id, 'VEND-0015', 'Hantengu', 'Hantengu Services', 'hantengu.vendor@example.com', '+1-555-3015', '+1-555-4015', 'https://www.hantenguvendor.com', 'VTAX-00015', 45, 84000.0000, '315 Vendor Avenue', 'Los Angeles', 'CA', '90015', 'USA', true, 0.0000),
(@org_id, 'VEND-0016', 'Gyutaro', 'Gyutaro Logistics', 'gyutaro.vendor@example.com', '+1-555-3016', '+1-555-4016', 'https://www.gyutarovendor.com', 'VTAX-00016', 45, 71000.0000, '316 Vendor Avenue', 'Los Angeles', 'CA', '90016', 'USA', true, 0.0000),
(@org_id, 'VEND-0017', 'Daki', 'Daki Partners', 'daki.vendor@example.com', '+1-555-3017', '+1-555-4017', 'https://www.dakivendor.com', 'VTAX-00017', 45, 98000.0000, '317 Vendor Avenue', 'Los Angeles', 'CA', '90017', 'USA', true, 0.0000),
(@org_id, 'VEND-0018', 'Enmu', 'Enmu Vendors', 'enmu.vendor@example.com', '+1-555-3018', '+1-555-4018', 'https://www.enmuvendor.com', 'VTAX-00018', 45, 65000.0000, '318 Vendor Avenue', 'Los Angeles', 'CA', '90018', 'USA', true, 0.0000),
(@org_id, 'VEND-0019', 'Rui', 'Rui Imports', 'rui.vendor@example.com', '+1-555-3019', '+1-555-4019', 'https://www.ruivendor.com', 'VTAX-00019', 45, 108000.0000, '319 Vendor Avenue', 'Los Angeles', 'CA', '90019', 'USA', true, 0.0000),
(@org_id, 'VEND-0020', 'Kaigaku', 'Kaigaku Exports', 'kaigaku.vendor@example.com', '+1-555-3020', '+1-555-4020', 'https://www.kaigakuvendor.com', 'VTAX-00020', 45, 76000.0000, '320 Vendor Avenue', 'Los Angeles', 'CA', '90020', 'USA', true, 0.0000),
(@org_id, 'VEND-0021', 'Pain', 'Pain Supplies', 'pain.vendor@example.com', '+1-555-3021', '+1-555-4021', 'https://www.painvendor.com', 'VTAX-00021', 45, 93000.0000, '321 Vendor Avenue', 'Los Angeles', 'CA', '90021', 'USA', true, 0.0000),
(@org_id, 'VEND-0022', 'Konan', 'Konan Manufacturing', 'konan.vendor@example.com', '+1-555-3022', '+1-555-4022', 'https://www.konanvendor.com', 'VTAX-00022', 45, 82000.0000, '322 Vendor Avenue', 'Los Angeles', 'CA', '90022', 'USA', true, 0.0000),
(@org_id, 'VEND-0023', 'Deidara', 'Deidara Wholesale', 'deidara.vendor@example.com', '+1-555-3023', '+1-555-4023', 'https://www.deidaravendor.com', 'VTAX-00023', 45, 74000.0000, '323 Vendor Avenue', 'Los Angeles', 'CA', '90023', 'USA', true, 0.0000),
(@org_id, 'VEND-0024', 'Kisame', 'Kisame Distribution', 'kisame.vendor@example.com', '+1-555-3024', '+1-555-4024', 'https://www.kisamevendor.com', 'VTAX-00024', 45, 100000.0000, '324 Vendor Avenue', 'Los Angeles', 'CA', '90024', 'USA', true, 0.0000),
(@org_id, 'VEND-0025', 'Hidan', 'Hidan Services', 'hidan.vendor@example.com', '+1-555-3025', '+1-555-4025', 'https://www.hidanvendor.com', 'VTAX-00025', 45, 68000.0000, '325 Vendor Avenue', 'Los Angeles', 'CA', '90025', 'USA', true, 0.0000),
(@org_id, 'VEND-0026', 'Orochimaru', 'Orochimaru Logistics', 'orochimaru.vendor@example.com', '+1-555-3026', '+1-555-4026', 'https://www.orochimaruvendor.com', 'VTAX-00026', 45, 87000.0000, '326 Vendor Avenue', 'Los Angeles', 'CA', '90026', 'USA', true, 0.0000),
(@org_id, 'VEND-0027', 'Minato', 'Minato Partners', 'minato.vendor@example.com', '+1-555-3027', '+1-555-4027', 'https://www.minatovendor.com', 'VTAX-00027', 45, 79000.0000, '327 Vendor Avenue', 'Los Angeles', 'CA', '90027', 'USA', true, 0.0000),
(@org_id, 'VEND-0028', 'Hashirama', 'Hashirama Vendors', 'hashirama.vendor@example.com', '+1-555-3028', '+1-555-4028', 'https://www.hashiramavendor.com', 'VTAX-00028', 45, 112000.0000, '328 Vendor Avenue', 'Los Angeles', 'CA', '90028', 'USA', true, 0.0000),
(@org_id, 'VEND-0029', 'Tobirama', 'Tobirama Imports', 'tobirama.vendor@example.com', '+1-555-3029', '+1-555-4029', 'https://www.tobiramavendor.com', 'VTAX-00029', 45, 66000.0000, '329 Vendor Avenue', 'Los Angeles', 'CA', '90029', 'USA', true, 0.0000),
(@org_id, 'VEND-0030', 'Zabuza', 'Zabuza Exports', 'zabuza.vendor@example.com', '+1-555-3030', '+1-555-4030', 'https://www.zabuzavendor.com', 'VTAX-00030', 45, 96000.0000, '330 Vendor Avenue', 'Los Angeles', 'CA', '90030', 'USA', true, 0.0000),
(@org_id, 'VEND-0031', 'Jogo', 'Jogo Supplies', 'jogo.vendor@example.com', '+1-555-3031', '+1-555-4031', 'https://www.jogovendor.com', 'VTAX-00031', 45, 73000.0000, '331 Vendor Avenue', 'Los Angeles', 'CA', '90031', 'USA', true, 0.0000),
(@org_id, 'VEND-0032', 'Hanami', 'Hanami Manufacturing', 'hanami.vendor@example.com', '+1-555-3032', '+1-555-4032', 'https://www.hanamivendor.com', 'VTAX-00032', 45, 104000.0000, '332 Vendor Avenue', 'Los Angeles', 'CA', '90032', 'USA', true, 0.0000),
(@org_id, 'VEND-0033', 'Choso', 'Choso Wholesale', 'choso.vendor@example.com', '+1-555-3033', '+1-555-4033', 'https://www.chosovendor.com', 'VTAX-00033', 45, 81000.0000, '333 Vendor Avenue', 'Los Angeles', 'CA', '90033', 'USA', true, 0.0000),
(@org_id, 'VEND-0034', 'Toji', 'Toji Distribution', 'toji.vendor@example.com', '+1-555-3034', '+1-555-4034', 'https://www.tojivendor.com', 'VTAX-00034', 45, 70000.0000, '334 Vendor Avenue', 'Los Angeles', 'CA', '90034', 'USA', true, 0.0000),
(@org_id, 'VEND-0035', 'Kenjaku', 'Kenjaku Services', 'kenjaku.vendor@example.com', '+1-555-3035', '+1-555-4035', 'https://www.kenjakuvendor.com', 'VTAX-00035', 45, 99000.0000, '335 Vendor Avenue', 'Los Angeles', 'CA', '90035', 'USA', true, 0.0000),
(@org_id, 'VEND-0036', 'Uraume', 'Uraume Logistics', 'uraume.vendor@example.com', '+1-555-3036', '+1-555-4036', 'https://www.uraumevendor.com', 'VTAX-00036', 45, 86000.0000, '336 Vendor Avenue', 'Los Angeles', 'CA', '90036', 'USA', true, 0.0000),
(@org_id, 'VEND-0037', 'Mai', 'Mai Partners', 'mai.vendor@example.com', '+1-555-3037', '+1-555-4037', 'https://www.maivendor.com', 'VTAX-00037', 45, 64000.0000, '337 Vendor Avenue', 'Los Angeles', 'CA', '90037', 'USA', true, 0.0000),
(@org_id, 'VEND-0038', 'Mechamaru', 'Mechamaru Vendors', 'mechamaru.vendor@example.com', '+1-555-3038', '+1-555-4038', 'https://www.mechamaruvendor.com', 'VTAX-00038', 45, 107000.0000, '338 Vendor Avenue', 'Los Angeles', 'CA', '90038', 'USA', true, 0.0000),
(@org_id, 'VEND-0039', 'Todo', 'Todo Imports', 'todo.vendor@example.com', '+1-555-3039', '+1-555-4039', 'https://www.todovendor.com', 'VTAX-00039', 45, 77000.0000, '339 Vendor Avenue', 'Los Angeles', 'CA', '90039', 'USA', true, 0.0000),
(@org_id, 'VEND-0040', 'Yuki', 'Yuki Exports', 'yuki.vendor@example.com', '+1-555-3040', '+1-555-4040', 'https://www.yukivendor.com', 'VTAX-00040', 45, 94000.0000, '340 Vendor Avenue', 'Los Angeles', 'CA', '90040', 'USA', true, 0.0000),
(@org_id, 'VEND-0041', 'Panda', 'Panda Supplies', 'panda.vendor@example.com', '+1-555-3041', '+1-555-4041', 'https://www.pandavendor.com', 'VTAX-00041', 45, 83000.0000, '341 Vendor Avenue', 'Los Angeles', 'CA', '90041', 'USA', true, 0.0000),
(@org_id, 'VEND-0042', 'Kamo', 'Kamo Manufacturing', 'kamo.vendor@example.com', '+1-555-3042', '+1-555-4042', 'https://www.kamovendor.com', 'VTAX-00042', 45, 62000.0000, '342 Vendor Avenue', 'Los Angeles', 'CA', '90042', 'USA', true, 0.0000),
(@org_id, 'VEND-0043', 'Mei', 'Mei Wholesale', 'mei.vendor@example.com', '+1-555-3043', '+1-555-4043', 'https://www.meivendor.com', 'VTAX-00043', 45, 111000.0000, '343 Vendor Avenue', 'Los Angeles', 'CA', '90043', 'USA', true, 0.0000),
(@org_id, 'VEND-0044', 'Hakari', 'Hakari Distribution', 'hakari.vendor@example.com', '+1-555-3044', '+1-555-4044', 'https://www.hakarivendor.com', 'VTAX-00044', 45, 89000.0000, '344 Vendor Avenue', 'Los Angeles', 'CA', '90044', 'USA', true, 0.0000),
(@org_id, 'VEND-0045', 'Kimimaro', 'Kimimaro Services', 'kimimaro.vendor@example.com', '+1-555-3045', '+1-555-4045', 'https://www.kimimarovendor.com', 'VTAX-00045', 45, 75000.0000, '345 Vendor Avenue', 'Los Angeles', 'CA', '90045', 'USA', true, 0.0000),
(@org_id, 'VEND-0046', 'Neji', 'Neji Logistics', 'neji.vendor@example.com', '+1-555-3046', '+1-555-4046', 'https://www.nejivendor.com', 'VTAX-00046', 45, 101000.0000, '346 Vendor Avenue', 'Los Angeles', 'CA', '90046', 'USA', true, 0.0000),
(@org_id, 'VEND-0047', 'Obanai', 'Obanai Partners', 'obanai.vendor@example.com', '+1-555-3047', '+1-555-4047', 'https://www.obanaivendor.com', 'VTAX-00047', 45, 67000.0000, '347 Vendor Avenue', 'Los Angeles', 'CA', '90047', 'USA', true, 0.0000),
(@org_id, 'VEND-0048', 'Sanemi', 'Sanemi Vendors', 'sanemi.vendor@example.com', '+1-555-3048', '+1-555-4048', 'https://www.sanemivendor.com', 'VTAX-00048', 45, 97000.0000, '348 Vendor Avenue', 'Los Angeles', 'CA', '90048', 'USA', true, 0.0000),
(@org_id, 'VEND-0049', 'Nakime', 'Nakime Imports', 'nakime.vendor@example.com', '+1-555-3049', '+1-555-4049', 'https://www.nakimevendor.com', 'VTAX-00049', 45, 85000.0000, '349 Vendor Avenue', 'Los Angeles', 'CA', '90049', 'USA', true, 0.0000),
(@org_id, 'VEND-0050', 'Kid', 'Kid Exports', 'kid.vendor@example.com', '+1-555-3050', '+1-555-4050', 'https://www.kidvendor.com', 'VTAX-00050', 45, 92000.0000, '350 Vendor Avenue', 'Los Angeles', 'CA', '90050', 'USA', true, 0.0000);

-- =====================================================
-- ITEMS (50 items)
-- =====================================================
-- Get account IDs for items
SET @sales_account_id = (SELECT id FROM accounts WHERE account_code = '4000' AND organization_id = @org_id);
SET @purchase_account_id = (SELECT id FROM accounts WHERE account_code = '5000' AND organization_id = @org_id);
SET @inventory_account_id = (SELECT id FROM accounts WHERE account_code = '1300' AND organization_id = @org_id);

INSERT INTO items (organization_id, item_code, item_name, item_type, sku, description, unit_of_measure,
                   sales_price, purchase_price, sales_account_id, purchase_account_id, inventory_account_id,
                   current_stock, reorder_level, is_active, is_inventoried) VALUES
(@org_id, 'ITEM-0001', 'Devil Fruit Extract', 'SERVICE', 'SKU-000001', 'Premium Devil Fruit Extract - Limited Edition', 'pcs', 250.0000, 150.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 500.0000, 50.0000, true, false),
(@org_id, 'ITEM-0002', 'Pirate Ship Model', 'PRODUCT', 'SKU-000002', 'Premium Pirate Ship Model - Limited Edition', 'pcs', 350.0000, 210.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 420.0000, 50.0000, true, true),
(@org_id, 'ITEM-0003', 'Straw Hat Premium', 'GOODS', 'SKU-000003', 'Premium Straw Hat Premium - Limited Edition', 'pcs', 180.0000, 108.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 680.0000, 50.0000, true, true),
(@org_id, 'ITEM-0004', 'Demon Slayer Sword', 'SERVICE', 'SKU-000004', 'Premium Demon Slayer Sword - Limited Edition', 'pcs', 420.0000, 252.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 310.0000, 50.0000, true, false),
(@org_id, 'ITEM-0005', 'Nichirin Blade', 'PRODUCT', 'SKU-000005', 'Premium Nichirin Blade - Limited Edition', 'pcs', 380.0000, 228.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 550.0000, 50.0000, true, true),
(@org_id, 'ITEM-0006', 'Rasengan Energy Drink', 'GOODS', 'SKU-000006', 'Premium Rasengan Energy Drink - Limited Edition', 'pcs', 95.0000, 57.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 890.0000, 50.0000, true, true),
(@org_id, 'ITEM-0007', 'Sharingan Lens', 'SERVICE', 'SKU-000007', 'Premium Sharingan Lens - Limited Edition', 'pcs', 275.0000, 165.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 200.0000, 50.0000, true, false),
(@org_id, 'ITEM-0008', 'Chakra Stone', 'PRODUCT', 'SKU-000008', 'Premium Chakra Stone - Limited Edition', 'pcs', 150.0000, 90.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 750.0000, 50.0000, true, true),
(@org_id, 'ITEM-0009', 'Cursed Energy Seal', 'GOODS', 'SKU-000009', 'Premium Cursed Energy Seal - Limited Edition', 'pcs', 320.0000, 192.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 380.0000, 50.0000, true, true),
(@org_id, 'ITEM-0010', 'Domain Expansion Kit', 'SERVICE', 'SKU-000010', 'Premium Domain Expansion Kit - Limited Edition', 'pcs', 550.0000, 330.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 150.0000, 50.0000, true, false),
(@org_id, 'ITEM-0011', 'Grand Line Map', 'PRODUCT', 'SKU-000011', 'Premium Grand Line Map - Limited Edition', 'pcs', 125.0000, 75.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 920.0000, 50.0000, true, true),
(@org_id, 'ITEM-0012', 'Log Pose Compass', 'GOODS', 'SKU-000012', 'Premium Log Pose Compass - Limited Edition', 'pcs', 200.0000, 120.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 600.0000, 50.0000, true, true),
(@org_id, 'ITEM-0013', 'Breathing Technique Manual', 'SERVICE', 'SKU-000013', 'Premium Breathing Technique Manual - Limited Edition', 'pcs', 85.0000, 51.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1000.0000, 50.0000, true, false),
(@org_id, 'ITEM-0014', 'Jutsu Scroll', 'PRODUCT', 'SKU-000014', 'Premium Jutsu Scroll - Limited Edition', 'pcs', 175.0000, 105.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 480.0000, 50.0000, true, true),
(@org_id, 'ITEM-0015', 'Jujutsu Talisman', 'GOODS', 'SKU-000015', 'Premium Jujutsu Talisman - Limited Edition', 'pcs', 230.0000, 138.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 350.0000, 50.0000, true, true),
(@org_id, 'ITEM-0016', 'Going Merry Replica', 'SERVICE', 'SKU-000016', 'Premium Going Merry Replica - Limited Edition', 'pcs', 450.0000, 270.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 180.0000, 50.0000, true, false),
(@org_id, 'ITEM-0017', 'Thousand Sunny Model', 'PRODUCT', 'SKU-000017', 'Premium Thousand Sunny Model - Limited Edition', 'pcs', 500.0000, 300.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 220.0000, 50.0000, true, true),
(@org_id, 'ITEM-0018', 'Kasugai Crow', 'GOODS', 'SKU-000018', 'Premium Kasugai Crow - Limited Edition', 'pcs', 135.0000, 81.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 700.0000, 50.0000, true, true),
(@org_id, 'ITEM-0019', 'Konoha Headband', 'SERVICE', 'SKU-000019', 'Premium Konoha Headband - Limited Edition', 'pcs', 75.0000, 45.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1100.0000, 50.0000, true, false),
(@org_id, 'ITEM-0020', 'Tokyo Jujutsu Uniform', 'PRODUCT', 'SKU-000020', 'Premium Tokyo Jujutsu Uniform - Limited Edition', 'pcs', 280.0000, 168.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 400.0000, 50.0000, true, true),
(@org_id, 'ITEM-0021', 'Sea Prism Stone', 'GOODS', 'SKU-000021', 'Premium Sea Prism Stone - Limited Edition', 'pcs', 400.0000, 240.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 280.0000, 50.0000, true, true),
(@org_id, 'ITEM-0022', 'Wado Ichimonji Replica', 'SERVICE', 'SKU-000022', 'Premium Wado Ichimonji Replica - Limited Edition', 'pcs', 480.0000, 288.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 160.0000, 50.0000, true, false),
(@org_id, 'ITEM-0023', 'Entertainment District Pass', 'PRODUCT', 'SKU-000023', 'Premium Entertainment District Pass - Limited Edition', 'pcs', 110.0000, 66.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 850.0000, 50.0000, true, true),
(@org_id, 'ITEM-0024', 'Akatsuki Robe', 'GOODS', 'SKU-000024', 'Premium Akatsuki Robe - Limited Edition', 'pcs', 220.0000, 132.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 520.0000, 50.0000, true, true),
(@org_id, 'ITEM-0025', 'Sukuna Finger', 'SERVICE', 'SKU-000025', 'Premium Sukuna Finger - Limited Edition', 'pcs', 350.0000, 210.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 300.0000, 50.0000, true, false),
(@org_id, 'ITEM-0026', 'Treasure Map', 'PRODUCT', 'SKU-000026', 'Premium Treasure Map - Limited Edition', 'pcs', 145.0000, 87.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 660.0000, 50.0000, true, true),
(@org_id, 'ITEM-0027', 'Vivre Card', 'GOODS', 'SKU-000027', 'Premium Vivre Card - Limited Edition', 'pcs', 65.0000, 39.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1200.0000, 50.0000, true, true),
(@org_id, 'ITEM-0028', 'Demon Blood Sample', 'SERVICE', 'SKU-000028', 'Premium Demon Blood Sample - Limited Edition', 'pcs', 380.0000, 228.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 240.0000, 50.0000, true, false),
(@org_id, 'ITEM-0029', 'Nine-Tails Chakra', 'PRODUCT', 'SKU-000029', 'Premium Nine-Tails Chakra - Limited Edition', 'pcs', 520.0000, 312.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 140.0000, 50.0000, true, true),
(@org_id, 'ITEM-0030', 'Cursed Spirit Core', 'GOODS', 'SKU-000030', 'Premium Cursed Spirit Core - Limited Edition', 'pcs', 290.0000, 174.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 430.0000, 50.0000, true, true),
(@org_id, 'ITEM-0031', 'Anime Action Figure Set', 'SERVICE', 'SKU-000031', 'Premium Anime Action Figure Set - Limited Edition', 'pcs', 185.0000, 111.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 580.0000, 50.0000, true, false),
(@org_id, 'ITEM-0032', 'Collector Edition Box', 'PRODUCT', 'SKU-000032', 'Premium Collector Edition Box - Limited Edition', 'pcs', 300.0000, 180.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 360.0000, 50.0000, true, true),
(@org_id, 'ITEM-0033', 'Limited Edition Poster', 'GOODS', 'SKU-000033', 'Premium Limited Edition Poster - Limited Edition', 'pcs', 55.0000, 33.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 980.0000, 50.0000, true, true),
(@org_id, 'ITEM-0034', 'Signed Manga Volume', 'SERVICE', 'SKU-000034', 'Premium Signed Manga Volume - Limited Edition', 'pcs', 195.0000, 117.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 470.0000, 50.0000, true, false),
(@org_id, 'ITEM-0035', 'Character Plushie', 'PRODUCT', 'SKU-000035', 'Premium Character Plushie - Limited Edition', 'pcs', 80.0000, 48.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 820.0000, 50.0000, true, true),
(@org_id, 'ITEM-0036', 'Cosplay Costume', 'GOODS', 'SKU-000036', 'Premium Cosplay Costume - Limited Edition', 'pcs', 260.0000, 156.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 340.0000, 50.0000, true, true),
(@org_id, 'ITEM-0037', 'Anime Trading Cards', 'SERVICE', 'SKU-000037', 'Premium Anime Trading Cards - Limited Edition', 'pcs', 45.0000, 27.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1500.0000, 50.0000, true, false),
(@org_id, 'ITEM-0038', 'Soundtrack CD', 'PRODUCT', 'SKU-000038', 'Premium Soundtrack CD - Limited Edition', 'pcs', 70.0000, 42.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 730.0000, 50.0000, true, true),
(@org_id, 'ITEM-0039', 'Art Book Premium', 'GOODS', 'SKU-000039', 'Premium Art Book Premium - Limited Edition', 'pcs', 155.0000, 93.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 540.0000, 50.0000, true, true),
(@org_id, 'ITEM-0040', 'Blu-ray Box Set', 'SERVICE', 'SKU-000040', 'Premium Blu-ray Box Set - Limited Edition', 'pcs', 400.0000, 240.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 260.0000, 50.0000, true, false),
(@org_id, 'ITEM-0041', 'Character Keychain', 'PRODUCT', 'SKU-000041', 'Premium Character Keychain - Limited Edition', 'pcs', 35.0000, 21.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1800.0000, 50.0000, true, true),
(@org_id, 'ITEM-0042', 'Enamel Pin Set', 'GOODS', 'SKU-000042', 'Premium Enamel Pin Set - Limited Edition', 'pcs', 50.0000, 30.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1400.0000, 50.0000, true, true),
(@org_id, 'ITEM-0043', 'Wall Scroll', 'SERVICE', 'SKU-000043', 'Premium Wall Scroll - Limited Edition', 'pcs', 90.0000, 54.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 650.0000, 50.0000, true, false),
(@org_id, 'ITEM-0044', 'Phone Case', 'PRODUCT', 'SKU-000044', 'Premium Phone Case - Limited Edition', 'pcs', 40.0000, 24.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1600.0000, 50.0000, true, true),
(@org_id, 'ITEM-0045', 'Laptop Sticker Pack', 'GOODS', 'SKU-000045', 'Premium Laptop Sticker Pack - Limited Edition', 'pcs', 25.0000, 15.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 2000.0000, 50.0000, true, true),
(@org_id, 'ITEM-0046', 'Anime T-Shirt', 'SERVICE', 'SKU-000046', 'Premium Anime T-Shirt - Limited Edition', 'pcs', 60.0000, 36.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 900.0000, 50.0000, true, false),
(@org_id, 'ITEM-0047', 'Hoodie Design', 'PRODUCT', 'SKU-000047', 'Premium Hoodie Design - Limited Edition', 'pcs', 120.0000, 72.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 450.0000, 50.0000, true, true),
(@org_id, 'ITEM-0048', 'Cap Collection', 'GOODS', 'SKU-000048', 'Premium Cap Collection - Limited Edition', 'pcs', 55.0000, 33.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 780.0000, 50.0000, true, true),
(@org_id, 'ITEM-0049', 'Backpack', 'SERVICE', 'SKU-000049', 'Premium Backpack - Limited Edition', 'pcs', 140.0000, 84.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 390.0000, 50.0000, true, false),
(@org_id, 'ITEM-0050', 'Water Bottle', 'PRODUCT', 'SKU-000050', 'Premium Water Bottle - Limited Edition', 'pcs', 30.0000, 18.0000, @sales_account_id, @purchase_account_id, @inventory_account_id, 1350.0000, 50.0000, true, true);

-- =====================================================
-- INVOICES (20 invoices with items)
-- =====================================================
INSERT INTO invoices (organization_id, customer_id, invoice_number, invoice_date, due_date, status,
                      subtotal, tax_amount, total_amount, paid_amount, balance_amount,
                      billing_address, shipping_address, notes, terms_and_conditions) VALUES
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0001' AND organization_id = @org_id), 'INV-000001', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL 0 DAY), 'SENT', 1500.0000, 150.0000, 1650.0000, 0.0000, 1650.0000, '101 Customer Street, New York', '201 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0002' AND organization_id = @org_id), 'INV-000002', DATE_SUB(CURDATE(), INTERVAL 25 DAY), DATE_SUB(CURDATE(), INTERVAL -5 DAY), 'DRAFT', 2200.0000, 220.0000, 2420.0000, 0.0000, 2420.0000, '102 Customer Street, New York', '202 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0003' AND organization_id = @org_id), 'INV-000003', DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL -10 DAY), 'PAID', 1800.0000, 180.0000, 1980.0000, 1980.0000, 0.0000, '103 Customer Street, New York', '203 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0004' AND organization_id = @org_id), 'INV-000004', DATE_SUB(CURDATE(), INTERVAL 45 DAY), DATE_SUB(CURDATE(), INTERVAL -15 DAY), 'OVERDUE', 3500.0000, 350.0000, 3850.0000, 0.0000, 3850.0000, '104 Customer Street, New York', '204 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0005' AND organization_id = @org_id), 'INV-000005', DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_SUB(CURDATE(), INTERVAL -15 DAY), 'SENT', 950.0000, 95.0000, 1045.0000, 0.0000, 1045.0000, '105 Customer Street, New York', '205 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0006' AND organization_id = @org_id), 'INV-000006', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL -20 DAY), 'DRAFT', 2750.0000, 275.0000, 3025.0000, 0.0000, 3025.0000, '106 Customer Street, New York', '206 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0007' AND organization_id = @org_id), 'INV-000007', DATE_SUB(CURDATE(), INTERVAL 60 DAY), DATE_SUB(CURDATE(), INTERVAL -30 DAY), 'PAID', 1200.0000, 120.0000, 1320.0000, 1320.0000, 0.0000, '107 Customer Street, New York', '207 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0008' AND organization_id = @org_id), 'INV-000008', DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_SUB(CURDATE(), INTERVAL -25 DAY), 'SENT', 4200.0000, 420.0000, 4620.0000, 0.0000, 4620.0000, '108 Customer Street, New York', '208 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0009' AND organization_id = @org_id), 'INV-000009', DATE_SUB(CURDATE(), INTERVAL 40 DAY), DATE_SUB(CURDATE(), INTERVAL -10 DAY), 'OVERDUE', 1650.0000, 165.0000, 1815.0000, 0.0000, 1815.0000, '109 Customer Street, New York', '209 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0010' AND organization_id = @org_id), 'INV-000010', DATE_SUB(CURDATE(), INTERVAL 35 DAY), DATE_SUB(CURDATE(), INTERVAL -5 DAY), 'PAID', 3100.0000, 310.0000, 3410.0000, 3410.0000, 0.0000, '110 Customer Street, New York', '210 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0011' AND organization_id = @org_id), 'INV-000011', DATE_SUB(CURDATE(), INTERVAL 28 DAY), DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'SENT', 2400.0000, 240.0000, 2640.0000, 0.0000, 2640.0000, '111 Customer Street, New York', '211 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0012' AND organization_id = @org_id), 'INV-000012', DATE_SUB(CURDATE(), INTERVAL 22 DAY), DATE_SUB(CURDATE(), INTERVAL -8 DAY), 'DRAFT', 1850.0000, 185.0000, 2035.0000, 0.0000, 2035.0000, '112 Customer Street, New York', '212 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0013' AND organization_id = @org_id), 'INV-000013', DATE_SUB(CURDATE(), INTERVAL 55 DAY), DATE_SUB(CURDATE(), INTERVAL -25 DAY), 'PAID', 2950.0000, 295.0000, 3245.0000, 3245.0000, 0.0000, '113 Customer Street, New York', '213 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0014' AND organization_id = @org_id), 'INV-000014', DATE_SUB(CURDATE(), INTERVAL 18 DAY), DATE_SUB(CURDATE(), INTERVAL -12 DAY), 'SENT', 800.0000, 80.0000, 880.0000, 0.0000, 880.0000, '114 Customer Street, New York', '214 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0015' AND organization_id = @org_id), 'INV-000015', DATE_SUB(CURDATE(), INTERVAL 50 DAY), DATE_SUB(CURDATE(), INTERVAL -20 DAY), 'OVERDUE', 5200.0000, 520.0000, 5720.0000, 0.0000, 5720.0000, '115 Customer Street, New York', '215 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0016' AND organization_id = @org_id), 'INV-000016', DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_SUB(CURDATE(), INTERVAL -18 DAY), 'DRAFT', 1100.0000, 110.0000, 1210.0000, 0.0000, 1210.0000, '116 Customer Street, New York', '216 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0017' AND organization_id = @org_id), 'INV-000017', DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_SUB(CURDATE(), INTERVAL -22 DAY), 'SENT', 3600.0000, 360.0000, 3960.0000, 0.0000, 3960.0000, '117 Customer Street, New York', '217 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0018' AND organization_id = @org_id), 'INV-000018', DATE_SUB(CURDATE(), INTERVAL 65 DAY), DATE_SUB(CURDATE(), INTERVAL -35 DAY), 'PAID', 2000.0000, 200.0000, 2200.0000, 2200.0000, 0.0000, '118 Customer Street, New York', '218 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0019' AND organization_id = @org_id), 'INV-000019', DATE_SUB(CURDATE(), INTERVAL 3 DAY), DATE_SUB(CURDATE(), INTERVAL -27 DAY), 'DRAFT', 4800.0000, 480.0000, 5280.0000, 0.0000, 5280.0000, '119 Customer Street, New York', '219 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days'),
(@org_id, (SELECT id FROM customers WHERE customer_code = 'CUST-0020' AND organization_id = @org_id), 'INV-000020', DATE_SUB(CURDATE(), INTERVAL 38 DAY), DATE_SUB(CURDATE(), INTERVAL -8 DAY), 'SENT', 1400.0000, 140.0000, 1540.0000, 0.0000, 1540.0000, '120 Customer Street, New York', '220 Shipping Lane, New York', 'Thank you for your business!', 'Payment due within 30 days');

-- Insert invoice items for each invoice (3 items per invoice)
INSERT INTO invoice_items (invoice_id, item_id, description, quantity, unit_price, discount_amount, tax_amount, line_total, line_order)
SELECT
    i.id,
    it.id,
    it.description,
    3.0000,
    it.sales_price,
    0.0000,
    (3 * it.sales_price * 0.10),
    (3 * it.sales_price),
    1
FROM invoices i
CROSS JOIN (SELECT id, description, sales_price FROM items WHERE organization_id = @org_id LIMIT 1) it
WHERE i.organization_id = @org_id;

-- =====================================================
-- BILLS (20 bills with items)
-- =====================================================
INSERT INTO bills (organization_id, vendor_id, bill_number, bill_date, due_date, status,
                   subtotal, tax_amount, total_amount, paid_amount, balance_amount,
                   reference_number, notes) VALUES
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0001' AND organization_id = @org_id), 'BILL-000001', DATE_SUB(CURDATE(), INTERVAL 25 DAY), DATE_SUB(CURDATE(), INTERVAL 20 DAY), 'DRAFT', 2500.0000, 250.0000, 2750.0000, 0.0000, 2750.0000, 'PO-000001', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0002' AND organization_id = @org_id), 'BILL-000002', DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 25 DAY), 'RECEIVED', 3200.0000, 320.0000, 3520.0000, 0.0000, 3520.0000, 'PO-000002', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0003' AND organization_id = @org_id), 'BILL-000003', DATE_SUB(CURDATE(), INTERVAL 40 DAY), DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'PAID', 1800.0000, 180.0000, 1980.0000, 1980.0000, 0.0000, 'PO-000003', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0004' AND organization_id = @org_id), 'BILL-000004', DATE_SUB(CURDATE(), INTERVAL 55 DAY), DATE_SUB(CURDATE(), INTERVAL -10 DAY), 'OVERDUE', 4100.0000, 410.0000, 4510.0000, 0.0000, 4510.0000, 'PO-000004', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0005' AND organization_id = @org_id), 'BILL-000005', DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_SUB(CURDATE(), INTERVAL -30 DAY), 'DRAFT', 1500.0000, 150.0000, 1650.0000, 0.0000, 1650.0000, 'PO-000005', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0006' AND organization_id = @org_id), 'BILL-000006', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL -15 DAY), 'RECEIVED', 2800.0000, 280.0000, 3080.0000, 0.0000, 3080.0000, 'PO-000006', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0007' AND organization_id = @org_id), 'BILL-000007', DATE_SUB(CURDATE(), INTERVAL 45 DAY), DATE_SUB(CURDATE(), INTERVAL 0 DAY), 'PAID', 3600.0000, 360.0000, 3960.0000, 3960.0000, 0.0000, 'PO-000007', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0008' AND organization_id = @org_id), 'BILL-000008', DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL -35 DAY), 'DRAFT', 2100.0000, 210.0000, 2310.0000, 0.0000, 2310.0000, 'PO-000008', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0009' AND organization_id = @org_id), 'BILL-000009', DATE_SUB(CURDATE(), INTERVAL 50 DAY), DATE_SUB(CURDATE(), INTERVAL -5 DAY), 'OVERDUE', 4500.0000, 450.0000, 4950.0000, 0.0000, 4950.0000, 'PO-000009', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0010' AND organization_id = @org_id), 'BILL-000010', DATE_SUB(CURDATE(), INTERVAL 35 DAY), DATE_SUB(CURDATE(), INTERVAL -10 DAY), 'PAID', 1900.0000, 190.0000, 2090.0000, 2090.0000, 0.0000, 'PO-000010', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0011' AND organization_id = @org_id), 'BILL-000011', DATE_SUB(CURDATE(), INTERVAL 22 DAY), DATE_SUB(CURDATE(), INTERVAL -23 DAY), 'RECEIVED', 2600.0000, 260.0000, 2860.0000, 0.0000, 2860.0000, 'PO-000011', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0012' AND organization_id = @org_id), 'BILL-000012', DATE_SUB(CURDATE(), INTERVAL 18 DAY), DATE_SUB(CURDATE(), INTERVAL -27 DAY), 'DRAFT', 3400.0000, 340.0000, 3740.0000, 0.0000, 3740.0000, 'PO-000012', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0013' AND organization_id = @org_id), 'BILL-000013', DATE_SUB(CURDATE(), INTERVAL 60 DAY), DATE_SUB(CURDATE(), INTERVAL -15 DAY), 'PAID', 2200.0000, 220.0000, 2420.0000, 2420.0000, 0.0000, 'PO-000013', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0014' AND organization_id = @org_id), 'BILL-000014', DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_SUB(CURDATE(), INTERVAL -37 DAY), 'DRAFT', 1700.0000, 170.0000, 1870.0000, 0.0000, 1870.0000, 'PO-000014', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0015' AND organization_id = @org_id), 'BILL-000015', DATE_SUB(CURDATE(), INTERVAL 48 DAY), DATE_SUB(CURDATE(), INTERVAL -3 DAY), 'OVERDUE', 3900.0000, 390.0000, 4290.0000, 0.0000, 4290.0000, 'PO-000015', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0016' AND organization_id = @org_id), 'BILL-000016', DATE_SUB(CURDATE(), INTERVAL 28 DAY), DATE_SUB(CURDATE(), INTERVAL -17 DAY), 'RECEIVED', 2900.0000, 290.0000, 3190.0000, 0.0000, 3190.0000, 'PO-000016', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0017' AND organization_id = @org_id), 'BILL-000017', DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_SUB(CURDATE(), INTERVAL -40 DAY), 'DRAFT', 4200.0000, 420.0000, 4620.0000, 0.0000, 4620.0000, 'PO-000017', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0018' AND organization_id = @org_id), 'BILL-000018', DATE_SUB(CURDATE(), INTERVAL 42 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY), 'PAID', 1600.0000, 160.0000, 1760.0000, 1760.0000, 0.0000, 'PO-000018', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0019' AND organization_id = @org_id), 'BILL-000019', DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_SUB(CURDATE(), INTERVAL -33 DAY), 'RECEIVED', 3100.0000, 310.0000, 3410.0000, 0.0000, 3410.0000, 'PO-000019', 'Purchase order for inventory'),
(@org_id, (SELECT id FROM vendors WHERE vendor_code = 'VEND-0020' AND organization_id = @org_id), 'BILL-000020', DATE_SUB(CURDATE(), INTERVAL 38 DAY), DATE_SUB(CURDATE(), INTERVAL -7 DAY), 'DRAFT', 2400.0000, 240.0000, 2640.0000, 0.0000, 2640.0000, 'PO-000020', 'Purchase order for inventory');

-- Insert bill items for each bill (3 items per bill)
INSERT INTO bill_items (bill_id, item_id, description, quantity, unit_price, discount_amount, tax_amount, line_total, line_order)
SELECT
    b.id,
    it.id,
    it.description,
    5.0000,
    it.purchase_price,
    0.0000,
    (5 * it.purchase_price * 0.10),
    (5 * it.purchase_price),
    1
FROM bills b
CROSS JOIN (SELECT id, description, purchase_price FROM items WHERE organization_id = @org_id LIMIT 1) it
WHERE b.organization_id = @org_id;

-- =====================================================
-- PAYMENTS (30 payments - 15 received, 15 made)
-- =====================================================
SET @cash_account_id = (SELECT id FROM accounts WHERE account_code = '1000' AND organization_id = @org_id);
SET @bank_account_id = (SELECT id FROM accounts WHERE account_code = '1100' AND organization_id = @org_id);

-- Payments Received (15)
INSERT INTO payments (organization_id, payment_type, customer_id, invoice_id, payment_number, payment_date,
                      amount, payment_method, reference_number, notes, account_id) VALUES
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000003' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000003' AND organization_id = @org_id), 'PAY-RCV-000001', DATE_SUB(CURDATE(), INTERVAL 18 DAY), 1980.0000, 'BANK_TRANSFER', 'REF-00000001', 'Payment received for INV-000003', @bank_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000007' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000007' AND organization_id = @org_id), 'PAY-RCV-000002', DATE_SUB(CURDATE(), INTERVAL 55 DAY), 1320.0000, 'CASH', 'REF-00000002', 'Payment received for INV-000007', @cash_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000010' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000010' AND organization_id = @org_id), 'PAY-RCV-000003', DATE_SUB(CURDATE(), INTERVAL 30 DAY), 3410.0000, 'CREDIT_CARD', 'REF-00000003', 'Payment received for INV-000010', @bank_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000013' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000013' AND organization_id = @org_id), 'PAY-RCV-000004', DATE_SUB(CURDATE(), INTERVAL 50 DAY), 3245.0000, 'BANK_TRANSFER', 'REF-00000004', 'Payment received for INV-000013', @cash_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000018' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000018' AND organization_id = @org_id), 'PAY-RCV-000005', DATE_SUB(CURDATE(), INTERVAL 60 DAY), 2200.0000, 'CHECK', 'REF-00000005', 'Payment received for INV-000018', @bank_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000001' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000001' AND organization_id = @org_id), 'PAY-RCV-000006', DATE_SUB(CURDATE(), INTERVAL 25 DAY), 825.0000, 'CASH', 'REF-00000006', 'Partial payment for INV-000001', @cash_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000002' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000002' AND organization_id = @org_id), 'PAY-RCV-000007', DATE_SUB(CURDATE(), INTERVAL 20 DAY), 1210.0000, 'BANK_TRANSFER', 'REF-00000007', 'Partial payment for INV-000002', @bank_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000005' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000005' AND organization_id = @org_id), 'PAY-RCV-000008', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 522.50, 'CREDIT_CARD', 'REF-00000008', 'Partial payment for INV-000005', @cash_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000006' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000006' AND organization_id = @org_id), 'PAY-RCV-000009', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 1512.50, 'BANK_TRANSFER', 'REF-00000009', 'Partial payment for INV-000006', @bank_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000008' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000008' AND organization_id = @org_id), 'PAY-RCV-000010', DATE_SUB(CURDATE(), INTERVAL 2 DAY), 2310.0000, 'CHECK', 'REF-00000010', 'Partial payment for INV-000008', @cash_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000011' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000011' AND organization_id = @org_id), 'PAY-RCV-000011', DATE_SUB(CURDATE(), INTERVAL 22 DAY), 1320.0000, 'CASH', 'REF-00000011', 'Partial payment for INV-000011', @bank_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000012' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000012' AND organization_id = @org_id), 'PAY-RCV-000012', DATE_SUB(CURDATE(), INTERVAL 17 DAY), 1017.50, 'BANK_TRANSFER', 'REF-00000012', 'Partial payment for INV-000012', @cash_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000014' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000014' AND organization_id = @org_id), 'PAY-RCV-000013', DATE_SUB(CURDATE(), INTERVAL 12 DAY), 440.0000, 'CREDIT_CARD', 'REF-00000013', 'Partial payment for INV-000014', @bank_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000017' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000017' AND organization_id = @org_id), 'PAY-RCV-000014', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1980.0000, 'BANK_TRANSFER', 'REF-00000014', 'Partial payment for INV-000017', @cash_account_id),
(@org_id, 'PAYMENT_RECEIVED', (SELECT customer_id FROM invoices WHERE invoice_number = 'INV-000020' AND organization_id = @org_id), (SELECT id FROM invoices WHERE invoice_number = 'INV-000020' AND organization_id = @org_id), 'PAY-RCV-000015', DATE_SUB(CURDATE(), INTERVAL 32 DAY), 770.0000, 'CHECK', 'REF-00000015', 'Partial payment for INV-000020', @bank_account_id);

-- Payments Made (15)
INSERT INTO payments (organization_id, payment_type, vendor_id, bill_id, payment_number, payment_date,
                      amount, payment_method, reference_number, notes, account_id) VALUES
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000003' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000003' AND organization_id = @org_id), 'PAY-MADE-000001', DATE_SUB(CURDATE(), INTERVAL 35 DAY), 1980.0000, 'BANK_TRANSFER', 'CHK-00000001', 'Payment made for BILL-000003', @bank_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000007' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000007' AND organization_id = @org_id), 'PAY-MADE-000002', DATE_SUB(CURDATE(), INTERVAL 40 DAY), 3960.0000, 'CHECK', 'CHK-00000002', 'Payment made for BILL-000007', @cash_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000010' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000010' AND organization_id = @org_id), 'PAY-MADE-000003', DATE_SUB(CURDATE(), INTERVAL 28 DAY), 2090.0000, 'BANK_TRANSFER', 'CHK-00000003', 'Payment made for BILL-000010', @bank_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000013' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000013' AND organization_id = @org_id), 'PAY-MADE-000004', DATE_SUB(CURDATE(), INTERVAL 55 DAY), 2420.0000, 'CHECK', 'CHK-00000004', 'Payment made for BILL-000013', @cash_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000018' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000018' AND organization_id = @org_id), 'PAY-MADE-000005', DATE_SUB(CURDATE(), INTERVAL 38 DAY), 1760.0000, 'BANK_TRANSFER', 'CHK-00000005', 'Payment made for BILL-000018', @bank_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000001' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000001' AND organization_id = @org_id), 'PAY-MADE-000006', DATE_SUB(CURDATE(), INTERVAL 20 DAY), 2750.0000, 'CHECK', 'CHK-00000006', 'Payment made for BILL-000001', @cash_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000002' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000002' AND organization_id = @org_id), 'PAY-MADE-000007', DATE_SUB(CURDATE(), INTERVAL 15 DAY), 3520.0000, 'BANK_TRANSFER', 'CHK-00000007', 'Payment made for BILL-000002', @bank_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000005' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000005' AND organization_id = @org_id), 'PAY-MADE-000008', DATE_SUB(CURDATE(), INTERVAL 8 DAY), 1650.0000, 'CHECK', 'CHK-00000008', 'Payment made for BILL-000005', @cash_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000006' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000006' AND organization_id = @org_id), 'PAY-MADE-000009', DATE_SUB(CURDATE(), INTERVAL 25 DAY), 3080.0000, 'BANK_TRANSFER', 'CHK-00000009', 'Payment made for BILL-000006', @bank_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000008' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000008' AND organization_id = @org_id), 'PAY-MADE-000010', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 2310.0000, 'CHECK', 'CHK-00000010', 'Payment made for BILL-000008', @cash_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000011' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000011' AND organization_id = @org_id), 'PAY-MADE-000011', DATE_SUB(CURDATE(), INTERVAL 18 DAY), 2860.0000, 'BANK_TRANSFER', 'CHK-00000011', 'Payment made for BILL-000011', @bank_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000012' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000012' AND organization_id = @org_id), 'PAY-MADE-000012', DATE_SUB(CURDATE(), INTERVAL 12 DAY), 3740.0000, 'CHECK', 'CHK-00000012', 'Payment made for BILL-000012', @cash_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000014' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000014' AND organization_id = @org_id), 'PAY-MADE-000013', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1870.0000, 'BANK_TRANSFER', 'CHK-00000013', 'Payment made for BILL-000014', @bank_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000016' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000016' AND organization_id = @org_id), 'PAY-MADE-000014', DATE_SUB(CURDATE(), INTERVAL 22 DAY), 3190.0000, 'CHECK', 'CHK-00000014', 'Payment made for BILL-000016', @cash_account_id),
(@org_id, 'PAYMENT_MADE', (SELECT vendor_id FROM bills WHERE bill_number = 'BILL-000019' AND organization_id = @org_id), (SELECT id FROM bills WHERE bill_number = 'BILL-000019' AND organization_id = @org_id), 'PAY-MADE-000015', DATE_SUB(CURDATE(), INTERVAL 7 DAY), 3410.0000, 'BANK_TRANSFER', 'CHK-00000015', 'Payment made for BILL-000019', @bank_account_id);

-- End of seed data migration
