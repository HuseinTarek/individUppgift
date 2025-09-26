DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS address;

CREATE TABLE address (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         street VARCHAR(255) NOT NULL,
                         postal_code VARCHAR(50) NOT NULL,
                         city VARCHAR(100) NOT NULL
);

CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         phone VARCHAR(50),
                         date_of_birth DATE NOT NULL,
                         roll VARCHAR(50) NOT NULL,
                         address_id BIGINT,
                         FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO address (street, postal_code, city) VALUES
                                                    ('123 Main St', '11111', 'Stockholm'),
                                                    ('456 Oak Ave', '22222', 'Gothenburg');

INSERT INTO members (first_name, last_name, email, phone, date_of_birth, roll, address_id) VALUES
                                                                                               ('Alice', 'Andersson', 'alice@example.com', '0701234567', '1985-04-12', 'USER', 1),
                                                                                               ('Bob', 'Berg', 'bob@example.com', '0707654321', '1990-08-30', 'ADMIN', 2);
