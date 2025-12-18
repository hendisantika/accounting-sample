-- Flyway Migration V10: Add Test Accounts
-- Compatible with MySQL 9.5.0
-- Date: 18/12/2025 11:00
-- Adds test accounts for different roles

-- Correct BCrypt hash for 'password123' (verified with Spring Security BCryptPasswordEncoder)
SET @password_hash = '$2a$10$g0iCxs5ro9/8moFrPHlmju5OWj1sSvJuCNWXVoDonvj8/NOAW54hO';

-- Get organization ID
SET @org_id = (SELECT id FROM organizations LIMIT 1);

-- Test Accounts
INSERT INTO users (organization_id, email, password_hash, first_name, last_name, phone, role, is_active, is_email_verified) VALUES
(@org_id, 'owner@test.com', @password_hash, 'Test', 'Owner', '+1-555-9001', 'OWNER', true, true),
(@org_id, 'admin@test.com', @password_hash, 'Test', 'Admin', '+1-555-9002', 'ADMIN', true, true),
(@org_id, 'accountant@test.com', @password_hash, 'Test', 'Accountant', '+1-555-9003', 'ACCOUNTANT', true, true),
(@org_id, 'user@test.com', @password_hash, 'Test', 'User', '+1-555-9004', 'USER', true, true),
(@org_id, 'viewer@test.com', @password_hash, 'Test', 'Viewer', '+1-555-9005', 'VIEWER', true, true);
