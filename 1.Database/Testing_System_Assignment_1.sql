DROP DATABASE IF EXISTS Testing_System_Assignment_1;

CREATE DATABASE Testing_System_Assignment_1;

USE Testing_System_Assignment_1;


-- TABLE 1: Department
-- DepartmentID: định danh của phòng ban (auto increment)
-- DepartmentName: tên đầy đủ của phòng ban (VD: sale, marketing, …)

CREATE TABLE Department (
    DepartmentID INT AUTO_INCREMENT PRIMARY KEY,
    DepartmentName VARCHAR(50) NOT NULL
);


-- TABLE 2: Position
-- PositionID: định danh của chức vụ (auto increment)
-- PositionName: tên chức vụ (Dev, Test, Scrum Master, PM)

CREATE TABLE `Position` (
    PositionID INT AUTO_INCREMENT PRIMARY KEY,
    PositionName VARCHAR(50) NOT NULL
);


-- TABLE 3: Account
-- AccountID: định danh của User (auto increment)
-- Email: địa chỉ email
-- Username: tên đăng nhập
-- FullName: tên đầy đủ
-- DepartmentID: phòng ban của user trong hệ thống
-- PositionID: chức vụ của User
-- CreateDate: ngày tạo tài khoản

CREATE TABLE `Account` (
    AccountID INT AUTO_INCREMENT PRIMARY KEY,
    Email VARCHAR(100) NOT NULL,
    Username VARCHAR(50) NOT NULL,
    FullName VARCHAR(100) NOT NULL,
    DepartmentID INT,
    PositionID INT,
    CreateDate DATE,

    FOREIGN KEY (DepartmentID)
        REFERENCES Department(DepartmentID),

    FOREIGN KEY (PositionID)
        REFERENCES `Position`(PositionID)
);


-- TABLE 4: Group
-- GroupID: định danh của nhóm (auto increment)
-- GroupName: tên nhóm
-- CreatorID: id của người tạo group
-- CreateDate: ngày tạo group

CREATE TABLE `Group` (
    GroupID INT AUTO_INCREMENT PRIMARY KEY,
    GroupName VARCHAR(100) NOT NULL,
    CreatorID INT,
    CreateDate DATE,

    FOREIGN KEY (CreatorID)
        REFERENCES `Account`(AccountID)
);


-- TABLE 5: GroupAccount
-- GroupID: định danh của nhóm
-- AccountID: định danh của User
-- JoinDate: ngày user tham gia vào nhóm
--
CREATE TABLE GroupAccount (
    GroupID INT,
    AccountID INT,
    JoinDate DATE,

    PRIMARY KEY (GroupID, AccountID),

    FOREIGN KEY (GroupID)
        REFERENCES `Group`(GroupID),

    FOREIGN KEY (AccountID)
        REFERENCES `Account`(AccountID)
);


-- TABLE 6: TypeQuestion
-- TypeID: định danh của loại câu hỏi (auto increment)
-- TypeName: tên của loại câu hỏi (Essay, Multiple-Choice)

CREATE TABLE TypeQuestion (
    TypeID INT AUTO_INCREMENT PRIMARY KEY,
    TypeName VARCHAR(50) NOT NULL
);


-- TABLE 7: CategoryQuestion
-- CategoryID: định danh của chủ đề câu hỏi (auto increment)
-- CategoryName: tên của chủ đề câu hỏi
-- (Java, .NET, SQL, Postman, Ruby, …)

CREATE TABLE CategoryQuestion (
    CategoryID INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(100) NOT NULL
);


-- TABLE 8: Question
-- QuestionID: định danh của câu hỏi (auto increment)
-- Content: nội dung của câu hỏi
-- CategoryID: định danh của chủ đề câu hỏi
-- TypeID: định danh của loại câu hỏi
-- CreatorID: id của người tạo câu hỏi
-- CreateDate: ngày tạo câu hỏi

CREATE TABLE Question (
    QuestionID INT AUTO_INCREMENT PRIMARY KEY,
    Content TEXT NOT NULL,
    CategoryID INT,
    TypeID INT,
    CreatorID INT,
    CreateDate DATE,

    FOREIGN KEY (CategoryID)
        REFERENCES CategoryQuestion(CategoryID),

    FOREIGN KEY (TypeID)
        REFERENCES TypeQuestion(TypeID),

    FOREIGN KEY (CreatorID)
        REFERENCES `Account`(AccountID)
);


-- TABLE 9: Answer
-- AnswerID: định danh của câu trả lời (auto increment)
-- Content: nội dung của câu trả lời
-- QuestionID: định danh của câu hỏi
-- isCorrect: câu trả lời này đúng hay sai

CREATE TABLE Answer (
    AnswerID INT AUTO_INCREMENT PRIMARY KEY,
    Content TEXT NOT NULL,
    QuestionID INT,
    isCorrect BOOLEAN,

    FOREIGN KEY (QuestionID)
        REFERENCES Question(QuestionID)
);


-- TABLE 10: Exam
-- ExamID: định danh của đề thi (auto increment)
-- Code: mã đề thi
-- Title: tiêu đề của đề thi
-- CategoryID: định danh của chủ đề thi
-- Duration: thời gian thi
-- CreatorID: id của người tạo đề thi
-- CreateDate: ngày tạo đề thi

CREATE TABLE Exam (
    ExamID INT AUTO_INCREMENT PRIMARY KEY,
    Code VARCHAR(50) NOT NULL,
    Title VARCHAR(100) NOT NULL,
    CategoryID INT,
    Duration INT,
    CreatorID INT,
    CreateDate DATE,

    FOREIGN KEY (CategoryID)
        REFERENCES CategoryQuestion(CategoryID),

    FOREIGN KEY (CreatorID)
        REFERENCES `Account`(AccountID)
);

-- TABLE 11: ExamQuestion
-- ExamID: định danh của đề thi
-- QuestionID: định danh của câu hỏi

CREATE TABLE ExamQuestion (
    ExamID INT,
    QuestionID INT,

    PRIMARY KEY (ExamID, QuestionID),

    FOREIGN KEY (ExamID)
        REFERENCES Exam(ExamID),

    FOREIGN KEY (QuestionID)
        REFERENCES Question(QuestionID)
);



-- INSERT DATA




-- Department
-- Thêm dữ liệu phòng ban

INSERT INTO Department (DepartmentName)
VALUES
('Sale'),
('Marketing'),
('IT'),
('HR'),
('Accounting');


-- Position
-- Thêm dữ liệu chức vụ

INSERT INTO `Position` (PositionName)
VALUES
('Developer'),
('Tester'),
('Scrum Master'),
('Project Manager'),
('HR');


-- Account
-- Thêm dữ liệu User

INSERT INTO `Account`
(Email, Username, FullName, DepartmentID, PositionID, CreateDate)
VALUES
('nguyenvana@gmail.com', 'nguyenvana', 'Nguyen Van A', 3, 1, '2026-01-10'),
('tranthib@gmail.com', 'tranthib', 'Tran Thi B', 3, 2, '2026-01-15'),
('levanc@gmail.com', 'levanc', 'Le Van C', 1, 4, '2026-02-01'),
('phamthid@gmail.com', 'phamthid', 'Pham Thi D', 2, 3, '2026-02-10'),
('hoangvane@gmail.com', 'hoangvane', 'Hoang Van E', 4, 5, '2026-02-15'),
('nguyenminhf@gmail.com', 'nguyenminhf', 'Nguyen Minh F', 3, 1, '2026-03-01');


-- Group
-- Thêm dữ liệu nhóm
-- CreatorID là AccountID của người tạo nhóm

INSERT INTO `Group`
(GroupName, CreatorID, CreateDate)
VALUES
('Java Team', 1, '2026-03-01'),
('Testing Team', 2, '2026-03-05'),
('SQL Team', 3, '2026-03-10'),
('Web Development', 4, '2026-03-15');


-- GroupAccount
-- Thêm User vào các Group

INSERT INTO GroupAccount
(GroupID, AccountID, JoinDate)
VALUES
(1, 1, '2026-03-01'),
(1, 2, '2026-03-02'),
(1, 6, '2026-03-03'),

(2, 2, '2026-03-05'),
(2, 5, '2026-03-06'),

(3, 1, '2026-03-10'),
(3, 3, '2026-03-11'),
(3, 6, '2026-03-12'),

(4, 4, '2026-03-15'),
(4, 6, '2026-03-16');


-- TypeQuestion
-- Thêm loại câu hỏi

INSERT INTO TypeQuestion (TypeName)
VALUES
('Essay'),
('Multiple-Choice');


-- CategoryQuestion
-- Thêm chủ đề câu hỏi

INSERT INTO CategoryQuestion (CategoryName)
VALUES
('Java'),
('.NET'),
('SQL'),
('Postman'),
('Ruby'),
('HTML/CSS'),
('JavaScript');


-- Question
-- Thêm dữ liệu câu hỏi

INSERT INTO Question
(Content, CategoryID, TypeID, CreatorID, CreateDate)
VALUES
('What is Java?', 1, 1, 1, '2026-03-01'),

('Which keyword is used to create a class in Java?', 1, 2, 1, '2026-03-02'),

('What is SQL?', 3, 1, 3, '2026-03-03'),

('Which command is used to retrieve data from a table?', 3, 2, 3, '2026-03-04'),

('What is Postman used for?', 4, 1, 2, '2026-03-05'),

('Which language is used to create web page structure?', 6, 2, 4, '2026-03-06'),

('Which keyword declares a variable in JavaScript?', 7, 2, 1, '2026-03-07'),

('What is .NET?', 2, 1, 1, '2026-03-08');


-- Answer
-- Thêm các câu trả lời cho Question
-- isCorrect:
-- TRUE  = câu trả lời đúng
-- FALSE = câu trả lời sai

INSERT INTO Answer
(Content, QuestionID, isCorrect)
VALUES

('Java is a programming language.', 1, TRUE),
('Java is a database.', 1, FALSE),
('Java is an operating system.', 1, FALSE),

('class', 2, TRUE),
('function', 2, FALSE),
('object', 2, FALSE),
('create', 2, FALSE),

('SQL is a language used to work with databases.', 3, TRUE),
('SQL is a programming language for games.', 3, FALSE),
('SQL is an operating system.', 3, FALSE),

('SELECT', 4, TRUE),
('INSERT', 4, FALSE),
('UPDATE', 4, FALSE),
('DELETE', 4, FALSE),

('API testing', 5, TRUE),
('Image editing', 5, FALSE),
('Video editing', 5, FALSE),

('HTML', 6, TRUE),
('CSS', 6, FALSE),
('Java', 6, FALSE),
('Python', 6, FALSE),

('let', 7, TRUE),
('int', 7, FALSE),
('string', 7, FALSE),
('define', 7, FALSE),

('.NET is a software development platform.', 8, TRUE),
('.NET is a database.', 8, FALSE),
('.NET is an operating system.', 8, FALSE);


-- Exam
-- Thêm dữ liệu đề thi
-- Duration: thời gian làm bài, tính bằng phút

INSERT INTO Exam
(Code, Title, CategoryID, Duration, CreatorID, CreateDate)
VALUES
('EX001', 'Java Basic Test', 1, 60, 1, '2026-03-10'),

('EX002', 'SQL Basic Test', 3, 45, 3, '2026-03-11'),

('EX003', 'Web Basic Test', 6, 30, 4, '2026-03-12');


-- ExamQuestion
-- Thêm câu hỏi vào từng đề thi

INSERT INTO ExamQuestion
(ExamID, QuestionID)
VALUES
(1, 1),
(1, 2),
(1, 8),

(2, 3),
(2, 4),

(3, 6),
(3, 7);



-- KIỂM TRA DỮ LIỆU


SELECT * FROM Department;

SELECT * FROM `Position`;

SELECT * FROM `Account`;

SELECT * FROM `Group`;

SELECT * FROM GroupAccount;

SELECT * FROM TypeQuestion;

SELECT * FROM CategoryQuestion;

SELECT * FROM Question;

SELECT * FROM Answer;

SELECT * FROM Exam;

SELECT * FROM ExamQuestion;