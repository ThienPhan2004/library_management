INSERT INTO roles(name) VALUES
('ADMIN'),
('STAFF');

INSERT INTO users(username, password, role_id) VALUES
('admin', '123', 1),
('staff1', '123', 2);

INSERT INTO students(student_code, full_name, class, phone) VALUES
('SV001', 'Nguyen Van A', 'CNTT1', '0901111111'),
('SV002', 'Tran Thi B', 'CNTT2', '0902222222');

INSERT INTO suppliers(name, phone, address, email) VALUES
('NXB Kim Dong', '0123456789', 'Hanoi', 'kimdong@example.com'),
('NXB Tre', '0987654321', 'HCM', 'tre@example.com');

INSERT INTO suppliers(name, phone, address, email) VALUES
('NXB Giao Duc', '0111111111', 'Hanoi', 'giaoduc@example.com'),
('NXB Lao Dong', '0222222222', 'Hanoi', 'laodong@example.com'),
('NXB Van Hoc', '0333333333', 'HCM', 'vanhoc@example.com'),
('NXB Tuoi Tre', '0444444444', 'HCM', 'tuoitre@example.com');

INSERT INTO book_titles(title, author, category, supplier_id, publish_year) VALUES
('Doraemon', 'Fujiko', 'Comic', 1, 2000),
('Harry Potter', 'J.K Rowling', 'Fantasy', 2, 2005);

INSERT INTO book_titles(title, author, category, supplier_id, publish_year) VALUES
('Conan', 'Gosho Aoyama', 'Comic', 1, 2001),
('One Piece', 'Oda Eiichiro', 'Comic', 1, 2003),
('Dragon Ball', 'Akira Toriyama', 'Comic', 1, 1998),

('Toi thay hoa vang tren co xanh', 'Nguyen Nhat Anh', 'Novel', 4, 2010),
('Mat biec', 'Nguyen Nhat Anh', 'Novel', 4, 2008),

('Dac Nhan Tam', 'Dale Carnegie', 'Skill', 3, 1995),
('Nha Gia Kim', 'Paulo Coelho', 'Novel', 3, 2001),

('Clean Code', 'Robert C. Martin', 'Programming', 2, 2008),
('The Pragmatic Programmer', 'Andrew Hunt', 'Programming', 2, 1999);

INSERT INTO books(title_id, status, location) VALUES
(1, 'AVAILABLE', 'A1'),
(1, 'AVAILABLE', 'A2'),
(2, 'AVAILABLE', 'B1'),
(2, 'AVAILABLE', 'B2');

INSERT INTO books(title_id, status, location) VALUES
-- Conan (id = 3)
(3, 'AVAILABLE', 'C1'),
(3, 'AVAILABLE', 'C2'),
(3, 'AVAILABLE', 'C3'),

-- One Piece (id = 4)
(4, 'AVAILABLE', 'C4'),
(4, 'AVAILABLE', 'C5'),

-- Dragon Ball (id = 5)
(5, 'AVAILABLE', 'C6'),

-- Toi thay hoa vang (id = 6)
(6, 'AVAILABLE', 'D1'),
(6, 'AVAILABLE', 'D2'),

-- Mat biec (id = 7)
(7, 'AVAILABLE', 'D3'),

-- Dac Nhan Tam (id = 8)
(8, 'AVAILABLE', 'E1'),
(8, 'AVAILABLE', 'E2'),

-- Nha Gia Kim (id = 9)
(9, 'AVAILABLE', 'E3'),

-- Clean Code (id = 10)
(10, 'AVAILABLE', 'F1'),
(10, 'AVAILABLE', 'F2'),

-- Pragmatic Programmer (id = 11)
(11, 'AVAILABLE', 'F3');

INSERT INTO borrow_orders(user_id, student_id, borrow_date, due_date, status) VALUES
(2, 1, '2026-03-01', '2026-03-10', 'BORROWING');

INSERT INTO borrow_details(order_id, book_id) VALUES
(1, 1),
(1, 2);

INSERT INTO returns(order_id, return_date) VALUES
(1, '2026-03-10');