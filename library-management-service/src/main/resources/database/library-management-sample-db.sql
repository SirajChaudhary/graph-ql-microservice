-- Create Database
DROP DATABASE IF EXISTS librarydb;
CREATE DATABASE librarydb;
USE librarydb;

-- Author
CREATE TABLE authors (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_author_email (email)
) ENGINE=InnoDB;

-- Category
CREATE TABLE categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    PRIMARY KEY (id),
    UNIQUE KEY uk_category_name (name)
) ENGINE=InnoDB;

-- Publisher
CREATE TABLE publishers (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(255),
    website VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_publisher_name (name),
    UNIQUE KEY uk_publisher_email (email)
) ENGINE=InnoDB;

-- Member
CREATE TABLE members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    membership_date DATE,
    active TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY uk_member_email (email)
) ENGINE=InnoDB;

-- Book
CREATE TABLE books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    publication_year INT,
    price DECIMAL(10,2),
    available TINYINT(1) NOT NULL DEFAULT 1,
    author_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    publisher_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_book_isbn (isbn),
    CONSTRAINT fk_book_author
        FOREIGN KEY (author_id) REFERENCES authors(id),
    CONSTRAINT fk_book_category
        FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT fk_book_publisher
        FOREIGN KEY (publisher_id) REFERENCES publishers(id)
) ENGINE=InnoDB;

-- Borrow Record
CREATE TABLE borrow_records (
    id BIGINT NOT NULL AUTO_INCREMENT,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    status ENUM('BORROWED', 'RETURNED') NOT NULL,
    book_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_borrow_book
        FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT fk_borrow_member
        FOREIGN KEY (member_id) REFERENCES members(id)
) ENGINE=InnoDB;

-- Insert Authors
INSERT INTO authors (first_name, last_name, email) VALUES
('Siraj', 'Chaudhary', 'mr.sirajchaudhary@yahoo.com'),
('Joshua', 'Bloch', 'joshua@example.com'),
('Martin', 'Fowler', 'martin@example.com'),
('Robert', 'Martin', 'unclebob@example.com'),
('James', 'Gosling', 'james@example.com'),
('Venkat', 'Subramaniam', 'venkat@example.com'),
('Kathy', 'Sierra', 'kathy@example.com'),
('Gavin', 'King', 'gavin@example.com'),
('Priya', 'Sharma', 'priya@example.com'),
('Ramesh', 'Kumar', 'ramesh@example.com');

-- Insert Categories
INSERT INTO categories (name, description) VALUES
('Java', 'Java Programming'),
('Spring Boot', 'Spring Boot'),
('GraphQL', 'GraphQL APIs'),
('Microservices', 'Microservices'),
('Cloud', 'Cloud Computing'),
('System Design', 'Software Architecture'),
('DevOps', 'Containers and CI/CD'),
('Database', 'SQL and NoSQL');

-- Insert Publishers
INSERT INTO publishers (name, email, website) VALUES
('O''Reilly Media', 'contact@oreilly.com', 'https://www.oreilly.com'),
('Packt Publishing', 'info@packt.com', 'https://www.packtpub.com'),
('Manning Publications', 'info@manning.com', 'https://www.manning.com'),
('Apress', 'info@apress.com', 'https://www.apress.com'),
('Pearson', 'info@pearson.com', 'https://www.pearson.com'),
('Tata McGraw-Hill', 'info@tmh.com', 'https://www.mheducation.com');

-- Insert Members
INSERT INTO members (first_name, last_name, email, membership_date, active) VALUES
('Amit', 'Patel', 'amit@example.com', '2026-01-10', 1),
('Sneha', 'Reddy', 'sneha@example.com', '2026-01-11', 1),
('Rahul', 'Verma', 'rahul@example.com', '2026-01-12', 1),
('John', 'Smith', 'john@example.com', '2026-01-13', 1),
('Emma', 'Wilson', 'emma@example.com', '2026-01-14', 1),
('David', 'Miller', 'david@example.com', '2026-01-15', 1),
('Anjali', 'Gupta', 'anjali@example.com', '2026-01-16', 1),
('Neha', 'Singh', 'neha@example.com', '2026-01-17', 1),
('Siraj', 'Chaudhary', 'siraj.member@example.com', '2026-01-18', 1),
('Priya', 'Nair', 'priya@example.com', '2026-01-19', 1);

-- Insert Books
INSERT INTO books (
    title,
    isbn,
    publication_year,
    price,
    available,
    author_id,
    category_id,
    publisher_id
) VALUES
('Effective Java', '9780134685991', 2018, 49.99, 0, 2, 1, 5),
('Clean Code', '9780132350884', 2008, 44.99, 1, 4, 1, 5),
('Refactoring', '9780201485677', 1999, 54.99, 0, 3, 6, 1),
('Spring in Action', '9781617294945', 2021, 47.99, 1, 6, 2, 3),
('GraphQL in Action', '9781617295683', 2022, 52.99, 0, 10, 3, 3),
('Building Microservices', '9781492034025', 2021, 56.99, 1, 3, 4, 1),
('Head First Java', '9781491910771', 2019, 39.99, 1, 7, 1, 1),
('Java Concurrency in Practice', '9780321349606', 2006, 48.99, 1, 5, 1, 5),
('Kubernetes in Action', '9781617293726', 2020, 51.99, 1, 8, 7, 2),
('Designing Data-Intensive Applications', '9781449373320', 2017, 59.99, 1, 3, 6, 1);

-- Insert Borrow Records
INSERT INTO borrow_records (
    borrow_date,
    due_date,
    return_date,
    status,
    book_id,
    member_id
) VALUES
('2026-07-01', '2026-07-15', NULL, 'BORROWED', 1, 1),
('2026-07-02', '2026-07-16', '2026-07-10', 'RETURNED', 2, 2),
('2026-07-03', '2026-07-17', NULL, 'BORROWED', 3, 3),
('2026-07-04', '2026-07-18', '2026-07-14', 'RETURNED', 4, 4),
('2026-07-05', '2026-07-19', NULL, 'BORROWED', 5, 5);

-- Verification Queries
SELECT * FROM authors;

SELECT * FROM categories;

SELECT * FROM publishers;

SELECT * FROM members;

SELECT * FROM books;

SELECT * FROM borrow_records;