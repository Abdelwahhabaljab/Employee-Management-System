-- Création des rôles
INSERT INTO role (name) VALUES ('ADMIN');
INSERT INTO role (name) VALUES ('HR');
INSERT INTO role (name) VALUES ('MANAGER');

-- Création d'un utilisateur ADMIN avec son rôle directement
INSERT INTO user (username, password, role_id)
VALUES
('admin', '$2a$12$2xSIX31XHrFsMvcvbBmAYOMBmAgjnSTo9h2qlT38OPoE2tA9vXyJm',
(SELECT id FROM role WHERE name = 'ADMIN'));  -- Mot de passe: "admin123" (chiffré)

-- Création d'un utilisateur HR avec son rôle directement
INSERT INTO user (username, password, role_id)
VALUES
('hr_user', '$2a$12$EzPqAnN9hMahue4GpMTiIO3fEUfbc8aXO3aZIG/pslqqRHGCYz.hy',
(SELECT id FROM role WHERE name = 'HR'));  -- Mot de passe: "hr123" (chiffré)

-- Création d'un utilisateur MANAGER avec son rôle directement
INSERT INTO user (username, password, role_id)
VALUES
('manager_user', '$2a$12$YyIRb.uaOWbFJe6YylN3IOQk9udcrYrvb2bwm7.k/C1pNaTYMnyk6',
(SELECT id FROM role WHERE name = 'MANAGER'));  -- Mot de passe: "manager123" (chiffré)

-- Création des employés
INSERT INTO employee (full_name, employee_id, job_title, department, hire_date, employment_status, email, phone_number, address)
VALUES
('Alice Dupont', 'E001', 'Software Engineer', 'IT', '2022-01-15', 'Active', 'alice.dupont@email.com', '0123456789', '10 Rue de Paris, Paris, France'),
('Bob Martin', 'E002', 'HR Manager', 'Human Resources', '2021-03-22', 'Active', 'bob.martin@email.com', '0987654321', '25 Avenue des Champs, Paris, France');

-- Création de la table de journalisation des actions
CREATE TABLE audit_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL,
    details TEXT,
    performed_by VARCHAR(100),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
: