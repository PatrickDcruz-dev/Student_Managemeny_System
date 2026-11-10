CREATE DATABASE IF NOT EXISTS student_management;
USE student_management;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    year INT NOT NULL,
    marks DOUBLE
);

INSERT IGNORE INTO users (username, password_hash) VALUES
('admin', '$2a$12$K8dZgZlJ2E4yZ3G0fJ8pueVpZzO0WbZzKj6s8uK7c1g5d0x1y2z3a');

INSERT IGNORE INTO students (student_id, name, email, department, year, marks) VALUES
(1, 'John Doe', 'john@example.com', 'CSE', 3, 89.50),
(2, 'Priya Sharma', 'priya@example.com', 'ECE', 2, 82.00),
(3, 'Arjun Rao', 'arjun@example.com', 'IT', 3, 91.00),
(4, 'Meera Nair', 'meera@example.com', 'CSE', 1, 78.50),
(5, 'Karan Verma', 'karan@example.com', 'ME', 2, 80.00);
