create database employecrud;
use employecrud;
CREATE TABLE employee (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    address VARCHAR(255),
    date_of_birth DATE
);
Select * from employee;
INSERT INTO employee (first_name, last_name, phone_number, address, date_of_birth)
VALUES
    ('Abhi', 'Das', '1234567890', '123 Main St, Apt 4B,Bengaluru', '1990-05-15'),
    ('Joy', 'Kumar', '9876543210', '456 Elm St, Suite 202, Hyderabad', '1995-12-10');

