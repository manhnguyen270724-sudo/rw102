DROP DATABASE IF EXISTS testing_system_assignment_3;

CREATE DATABASE testing_system_assignment_3;

USE testing_system_assignment_3;


-- TABLE 1: department
-- departmentid: định danh của phòng ban (auto increment)
-- departmentname: tên đầy đủ của phòng ban (VD: sale, marketing, …)

CREATE TABLE department (
    departmentid INT AUTO_INCREMENT PRIMARY KEY,
    departmentname VARCHAR(50) NOT NULL
);


-- TABLE 2: position
-- positionid: định danh của chức vụ (auto increment)
-- positionname: tên chức vụ (Dev, Test, Scrum Master, PM)

CREATE TABLE position (
    positionid INT AUTO_INCREMENT PRIMARY KEY,
    positionname VARCHAR(50) NOT NULL
);


-- TABLE 3: account
-- accountid: định danh của User (auto increment)
-- email: địa chỉ email
-- username: tên đăng nhập
-- fullname: tên đầy đủ
-- departmentid: phòng ban của user trong hệ thống
-- positionid: chức vụ của User
-- createdate: ngày tạo tài khoản

CREATE TABLE account (
    accountid INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    departmentid INT,
    positionid INT,
    createdate DATE,

    FOREIGN KEY (departmentid)
        REFERENCES department(departmentid),

    FOREIGN KEY (positionid)
        REFERENCES position (positionid)
);


-- TABLE 4: group
-- groupid: định danh của nhóm (auto increment)
-- groupname: tên nhóm
-- creatorid: id của người tạo group
-- createdate: ngày tạo group

CREATE TABLE `group` (
    groupid INT AUTO_INCREMENT PRIMARY KEY,
    groupname VARCHAR(100) NOT NULL,
    creatorid INT,
    createdate DATE,

    FOREIGN KEY (creatorid)
        REFERENCES account(accountid)
);


-- TABLE 5: groupaccount
-- groupid: định danh của nhóm
-- accountid: định danh của User
-- joindate: ngày user tham gia vào nhóm

CREATE TABLE groupaccount (
    groupid INT,
    accountid INT,
    joindate DATE,

    PRIMARY KEY (groupid, accountid),

    FOREIGN KEY (groupid)
        REFERENCES `group`(groupid),

    FOREIGN KEY (accountid)
        REFERENCES account(accountid)
);


-- TABLE 6: typequestion
-- typeid: định danh của loại câu hỏi (auto increment)
-- typename: tên của loại câu hỏi (Essay, Multiple-Choice)

CREATE TABLE typequestion (
    typeid INT AUTO_INCREMENT PRIMARY KEY,
    typename VARCHAR(50) NOT NULL
);


-- TABLE 7: categoryquestion
-- categoryid: định danh của chủ đề câu hỏi (auto increment)
-- categoryname: tên của chủ đề câu hỏi (Java, .NET, SQL, Postman, Ruby, …)

CREATE TABLE categoryquestion (
    categoryid INT AUTO_INCREMENT PRIMARY KEY,
    categoryname VARCHAR(100) NOT NULL
);


-- TABLE 8: question
-- questionid: định danh của câu hỏi (auto increment)
-- content: nội dung của câu hỏi
-- categoryid: định danh của chủ đề câu hỏi
-- typeid: định danh của loại câu hỏi
-- creatorid: id của người tạo câu hỏi
-- createdate: ngày tạo câu hỏi

CREATE TABLE question (
    questionid INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    categoryid INT,
    typeid INT,
    creatorid INT,
    createdate DATE,

    FOREIGN KEY (categoryid)
        REFERENCES categoryquestion(categoryid),

    FOREIGN KEY (typeid)
        REFERENCES typequestion(typeid),

    FOREIGN KEY (creatorid)
        REFERENCES account(accountid)
);


-- TABLE 9: answer
-- answerid: định danh của câu trả lời (auto increment)
-- content: nội dung của câu trả lời
-- questionid: định danh của câu hỏi
-- iscorrect: câu trả lời này đúng hay sai

CREATE TABLE answer (
    answerid INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    questionid INT,
    iscorrect BOOLEAN,

    FOREIGN KEY (questionid)
        REFERENCES question(questionid)
);


-- TABLE 10: exam
-- examid: định danh của đề thi (auto increment)
-- code: mã đề thi
-- title: tiêu đề của đề thi
-- categoryid: định danh của chủ đề thi
-- duration: thời gian thi
-- creatorid: id của người tạo đề thi
-- createdate: ngày tạo đề thi

CREATE TABLE exam (
    examid INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    title VARCHAR(100) NOT NULL,
    categoryid INT,
    duration INT,
    creatorid INT,
    createdate DATE,

    FOREIGN KEY (categoryid)
        REFERENCES categoryquestion(categoryid),

    FOREIGN KEY (creatorid)
        REFERENCES account(accountid)
);


-- TABLE 11: examquestion
-- examid: định danh của đề thi
-- questionid: định danh của câu hỏi

CREATE TABLE examquestion (
    examid INT,
    questionid INT,

    PRIMARY KEY (examid, questionid),

    FOREIGN KEY (examid)
        REFERENCES exam(examid),

    FOREIGN KEY (questionid)
        REFERENCES question(questionid)
);



-- THÊM DỮ LIỆU MẪU (INSERT DATA)

-- 1. Department
INSERT INTO department (departmentname)
VALUES
('Sale'),
('Marketing'),
('IT'),
('HR'),
('Accounting'),
('Customer Service'),
('Research and Development'),
('Administration'),
('Finance'),
('Quality Assurance');


-- 2. Position
INSERT INTO position (positionname)
VALUES
('Developer'),
('Tester'),
('Scrum Master'),
('Project Manager'),
('HR'),
('Business Analyst'),
('Team Leader'),
('Technical Leader'),
('DevOps Engineer'),
('Product Owner');


-- 3. Account
INSERT INTO account (email, username, fullname, departmentid, positionid, createdate)
VALUES
('nguyenvanan@gmail.com', 'an.nguyen', 'Nguyen Van An', 1, 1, '2019-10-10'),
('trthihuyen@gmail.com', 'huyen.tran', 'Tran Thi Huyen', 2, 2, '2019-12-15'),
('levanthanh@gmail.com', 'thanh.le', 'Le Van Thanh', 3, 3, '2019-11-20'),
('phamthimai@gmail.com', 'mai.pham', 'Pham Thi Mai', 3, 4, '2019-12-18'),
('hoangminhduc@gmail.com', 'duc.hoang', 'Hoang Minh Duc', 1, 5, '2019-09-05'),
('vuthithao@gmail.com', 'thao.vu', 'Vu Thi Thao', 4, 6, '2019-12-22'),
('buihoanganh@gmail.com', 'anh.bui', 'Bui Hoang Anh', 5, 7, '2019-10-15'),
('ngoquangvu@gmail.com', 'vu.ngo', 'Ngo Quang Vu', 2, 8, '2019-08-11'),
('duongthilan@gmail.com', 'lan.duong', 'Duong Thi Lan', 6, 9, '2019-12-01'),
('dinhtienhoang@gmail.com', 'hoang.dinh', 'Dinh Tien Hoang', 3, 10, '2019-12-19');


-- 4. Group
INSERT INTO `group` (groupname, creatorid, createdate)
VALUES
('Java Beginner', 1, '2019-11-01'),
('SQL Master', 2, '2019-12-10'),
('Frontend Devs', 3, '2019-12-15'),
('Tester Pro', 4, '2019-12-18'),
('PM Club', 5, '2019-10-01'),
('Data Science', 6, '2019-09-12'),
('Cloud Architecture', 7, '2019-12-21'),
('DevOps Team', 8, '2019-11-30'),
('English Club', 9, '2019-12-05'),
('AI Research', 10, '2019-12-25');


-- 5. GroupAccount
INSERT INTO groupaccount (groupid, accountid, joindate)
VALUES
(1, 1, '2019-11-05'),
(1, 2, '2019-11-06'),
(1, 3, '2019-11-10'),
(2, 2, '2019-12-12'),
(2, 4, '2019-12-15'),
(3, 3, '2019-12-16'),
(3, 5, '2019-12-18'),
(4, 1, '2019-12-19'),
(4, 6, '2019-12-20'),
(5, 7, '2019-10-05'),
(5, 8, '2019-10-10'),
(6, 9, '2019-09-15'),
(6, 10, '2019-09-20'),
(7, 1, '2019-12-22'),
(7, 3, '2019-12-23'),
(8, 2, '2019-12-02'),
(8, 4, '2019-12-03'),
(9, 5, '2019-12-08'),
(9, 6, '2019-12-09'),
(10, 7, '2019-12-26'),
(10, 8, '2019-12-27'),
(2, 1, '2019-12-11');


-- 6. TypeQuestion
INSERT INTO typequestion (typename)
VALUES
('Essay'),
('Multiple-Choice'),
('True/False'),
('Fill-in-the-blank'),
('Matching'),
('Short Answer'),
('Coding'),
('Practical'),
('Oral'),
('Survey');


-- 7. CategoryQuestion
INSERT INTO categoryquestion (categoryname)
VALUES
('Java'),
('.NET'),
('SQL'),
('Postman'),
('Ruby'),
('Python'),
('C++'),
('HTML/CSS'),
('JavaScript'),
('DevOps');


-- 8. Question
INSERT INTO question (content, categoryid, typeid, creatorid, createdate)
VALUES
('What is Java Virtual Machine?', 1, 2, 1, '2019-10-10'),
('Explain Primary Key in SQL.', 3, 1, 2, '2019-11-15'),
('câu hỏi về C# basic', 2, 2, 3, '2019-12-01'),
('How to write GET request in Postman?', 4, 2, 4, '2019-12-10'),
('What is Ruby on Rails?', 5, 1, 5, '2019-10-20'),
('câu hỏi về Python syntax', 6, 2, 6, '2019-12-05'),
('Difference between GET and POST?', 4, 3, 7, '2019-12-11'),
('Explain normalization in database.', 3, 1, 8, '2019-11-01'),
('What is inheritance in OOP?', 1, 3, 9, '2019-12-14'),
('What is Docker container?', 10, 2, 10, '2019-12-18'),
('câu hỏi về HTML tags', 8, 2, 1, '2019-10-25'),
('What is closure in JavaScript?', 9, 1, 2, '2019-12-02');


-- 9. Answer
INSERT INTO answer (content, questionid, iscorrect)
VALUES
('Answer 1 for Q1', 1, 1), 
('Answer 2 for Q1', 1, 0),
('Ans 1 for Q2', 2, 0), 
('Ans 2 for Q2', 2, 1), 
('Ans 3 for Q2', 2, 0), 
('Ans 4 for Q2', 2, 0),
('Ans 1 for Q3', 3, 1), 
('Ans 2 for Q3', 3, 0),
('Ans 1 for Q4', 4, 1), 
('Ans 2 for Q4', 4, 0), 
('Ans 3 for Q4', 4, 0),
('Ans 1 for Q5', 5, 1),
('Ans 1 for Q6', 6, 1), 
('Ans 2 for Q6', 6, 0),
('Ans 1 for Q7', 7, 1), 
('Ans 2 for Q7', 7, 0), 
('Ans 3 for Q7', 7, 0),
('Ans 1 for Q8', 8, 1), 
('Ans 2 for Q8', 8, 0), 
('Ans 3 for Q8', 8, 0), 
('Ans 4 for Q8', 8, 1),
('Ans 1 for Q9', 9, 1),
('Ans 1 for Q10', 10, 1), 
('Ans 2 for Q10', 10, 0),
('Ans 1 for Q11', 11, 1),
('Ans 1 for Q12', 12, 1), 
('Ans 2 for Q12', 12, 0),
('Extra 1', 1, 0), 
('Extra 2', 3, 0), 
('Extra 3', 5, 0), 
('Extra 4', 6, 0),
('Extra 5', 9, 0), 
('Extra 6', 10, 0), 
('Extra 7', 11, 0), 
('Extra 8', 12, 0),
('Extra 9', 4, 0), 
('Extra 10', 7, 0), 
('Extra 11', 8, 0), 
('Extra 12', 2, 0);


-- 10. Exam
INSERT INTO exam (code, title, categoryid, duration, creatorid, createdate)
VALUES
('EX_001', 'Midterm Java', 1, 45, 1, '2019-10-15'),
('EX_002', 'Final SQL Exam', 3, 60, 2, '2019-12-10'),
('EX_003', 'DotNet Test', 2, 90, 3, '2019-11-20'),
('EX_004', 'Postman Basic', 4, 30, 4, '2019-12-12'),
('EX_005', 'Ruby Advanced', 5, 60, 5, '2019-12-18'),
('EX_006', 'Python Test', 6, 45, 6, '2019-10-01'),
('EX_007', 'Database Exam', 3, 120, 7, '2019-12-05'),
('EX_008', 'DevOps Check', 10, 60, 8, '2019-12-19'),
('EX_009', 'JS Exam', 9, 90, 9, '2019-11-11'),
('EX_010', 'HTML CSS Test', 8, 45, 10, '2019-09-30');


-- 11. ExamQuestion
INSERT INTO examquestion (examid, questionid)
VALUES
(1, 1), 
(1, 9),
(2, 2), 
(2, 8),
(3, 3),
(4, 4), 
(4, 7),
(5, 5),
(6, 6),
(7, 2),
(8, 10),
(9, 12),
(10, 11);



-- KIỂM TRA DỮ LIỆU & CÁC CÂU LỆNH TRUY VẤN



-- Question 2: Lấy ra tất cả các phòng ban

SELECT * 
FROM department;


-- Question 3: Lấy ra id của phòng ban "Sale"

SELECT departmentid 
FROM department 
WHERE departmentname = 'Sale';


-- Question 4: Lấy ra thông tin account có full name dài nhất

SELECT * 
FROM account 
WHERE LENGTH(fullname) = (
    SELECT MAX(LENGTH(fullname)) 
    FROM account
);


-- Question 5: Lấy ra thông tin account có full name dài nhất và thuộc phòng ban có id = 3

SELECT * 
FROM account 
WHERE departmentid = 3 
AND LENGTH(fullname) = (
    SELECT MAX(LENGTH(fullname)) 
    FROM account 
    WHERE departmentid = 3
);


-- Question 6: Lấy ra tên group đã tham gia trước ngày 20/12/2019

SELECT g.groupname 
FROM `group` g 
JOIN groupaccount ga 
    ON g.groupid = ga.groupid 
WHERE ga.joindate < '2019-12-20';


-- Question 7: Lấy ra ID của question có >= 4 câu trả lời

SELECT questionid 
FROM answer 
GROUP BY questionid 
HAVING COUNT(answerid) >= 4;


-- Question 8: Lấy ra các mã đề thi có thời gian thi >= 60 phút và được tạo trước ngày 20/12/2019

SELECT code 
FROM exam 
WHERE duration >= 60 
AND createdate < '2019-12-20';


-- Question 9: Lấy ra 5 group được tạo gần đây nhất

SELECT * 
FROM `group` 
ORDER BY createdate DESC 
LIMIT 5;


-- Question 10: Đếm số nhân viên thuộc department có id = 2

SELECT COUNT(*) AS NumberOfEmployee 
FROM account 
WHERE departmentid = 2;


-- Question 11: Lấy ra nhân viên có tên bắt đầu bằng chữ "D" và kết thúc bằng chữ "o"

SELECT * 
FROM account 
WHERE fullname LIKE 'D%o';


-- Question 12: Xóa tất cả các exam được tạo trước ngày 20/12/2019

DELETE FROM exam 
WHERE createdate < '2019-12-20';


-- Question 13: Xóa tất cả các question có nội dung bắt đầu bằng từ "câu hỏi"

DELETE FROM question 
WHERE content LIKE 'câu hỏi%';


-- Question 14: Update thông tin của account có id = 5 thành tên "Nguyễn Bá Lộc" và email thành loc.nguyenba@vti.com.vn

UPDATE account 
SET fullname = 'Nguyễn Bá Lộc', 
    email = 'loc.nguyenba@vti.com.vn' 
WHERE accountid = 5;

-- Kiểm tra lại thông tin AccountID = 5
SELECT * 
FROM account 
WHERE accountid = 5;


-- Question 15: Update account có id = 5 sẽ thuộc group có id = 4

INSERT INTO groupaccount (groupid, accountid, joindate) 
VALUES (4, 5, CURDATE());

-- Kiểm tra lại AccountID = 5 thuộc GroupID = 4
SELECT * 
FROM groupaccount 
WHERE accountid = 5 
AND groupid = 4;