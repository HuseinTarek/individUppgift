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
                         role VARCHAR(50) NOT NULL,
                         address_id BIGINT,
                         FOREIGN KEY (address_id) REFERENCES address(id)
);
INSERT INTO address (street, postal_code, city) VALUES
                                                    ('789 Birch Rd', '33333', 'Malmö'),
                                                    ('321 Pine St', '44444', 'Uppsala'),
                                                    ('654 Elm Blvd', '55555', 'Västerås'),
                                                    ('987 Maple Dr', '66666', 'Örebro'),
                                                    ('147 Cedar Way', '77777', 'Linköping');

INSERT INTO members (first_name, last_name, email, phone, date_of_birth, role, address_id) VALUES
                                                                                               ('Carl', 'Carlsson', 'carl@example.com', '0708765432', '1988-03-15', 'USER', 1),
                                                                                               ('Diana', 'Eriksson', 'diana@example.com', '0709876543', '1992-07-22', 'USER', 2),
                                                                                               ('Erik', 'Larsson', 'erik@example.com', '0701098765', '1987-11-08', 'ADMIN', 2),
                                                                                               ('Frida', 'Nilsson', 'frida@example.com', '0702109876', '1995-02-14', 'USER', 3),
                                                                                               ('Gustav', 'Petersson', 'gustav@example.com', '0703210987', '1983-09-03', 'ADMIN', 4);
