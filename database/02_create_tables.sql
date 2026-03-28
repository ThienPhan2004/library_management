CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role_id INT,
    FOREIGN KEY (role_id) REFERENCES roles(id)
) ENGINE=InnoDB;

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_code VARCHAR(20) UNIQUE NOT NULL,
    full_name VARCHAR(100),
    class VARCHAR(50),
    phone VARCHAR(20)
) ENGINE=InnoDB;

CREATE TABLE suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(255),
    address VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE book_titles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(100),
    category VARCHAR(100),
    supplier_id INT,
    publish_year INT,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
) ENGINE=InnoDB;

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title_id INT,
    status VARCHAR(50), -- AVAILABLE, BORROWED, DAMAGED
    location VARCHAR(100),
    FOREIGN KEY (title_id) REFERENCES book_titles(id)
) ENGINE=InnoDB;

CREATE TABLE borrow_orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    student_id INT,
    borrow_date DATE,
    due_date DATE,
    status VARCHAR(50), -- BORROWING, COMPLETED

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
) ENGINE=InnoDB;

CREATE TABLE borrow_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    book_id INT,

    FOREIGN KEY (order_id) REFERENCES borrow_orders(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
) ENGINE=InnoDB;

CREATE TABLE returns (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    return_date DATE,

    FOREIGN KEY (order_id) REFERENCES borrow_orders(id)
) ENGINE=InnoDB;