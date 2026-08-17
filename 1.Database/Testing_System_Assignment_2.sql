DROP DATABASE IF EXISTS Testing_System_Assignment_2;

CREATE DATABASE Testing_System_Assignment_2;

USE Testing_System_Assignment_2;


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



-- -- TABLE 1: Department
-- Thêm dữ liệu phòng ban

INSERT INTO Department (DepartmentName)
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


-- TABLE 2: Position
-- Thêm dữ liệu chức vụ

INSERT INTO `Position` (PositionName)
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


-- KIỂM TRA DỮ LIỆU


-- Question 1: Thêm ít nhất 10 record vào mỗi table

-- Department: 10 records
SELECT COUNT(*) AS NumberOfDepartment
FROM Department;

-- Position: 10 records
SELECT COUNT(*) AS NumberOfPosition
FROM `Position`;

-- Account: 10 records
SELECT COUNT(*) AS NumberOfAccount
FROM `Account`;

-- Group: 10 records
SELECT COUNT(*) AS NumberOfGroup
FROM `Group`;

-- GroupAccount: 22 records
SELECT COUNT(*) AS NumberOfGroupAccount
FROM GroupAccount;

-- TypeQuestion: 10 records
SELECT COUNT(*) AS NumberOfTypeQuestion
FROM TypeQuestion;

-- CategoryQuestion: 10 records
SELECT COUNT(*) AS NumberOfCategoryQuestion
FROM CategoryQuestion;

-- Question: 12 records
SELECT COUNT(*) AS NumberOfQuestion
FROM Question;

-- Answer: 39 records
SELECT COUNT(*) AS NumberOfAnswer
FROM Answer;

-- Exam: 10 records
SELECT COUNT(*) AS NumberOfExam
FROM Exam;

-- ExamQuestion: 13 records
SELECT COUNT(*) AS NumberOfExamQuestion
FROM ExamQuestion;


-- Question 2: Lấy ra tất cả các phòng ban

SELECT *
FROM Department;


-- Question 3: Lấy ra id của phòng ban "Sale"

SELECT DepartmentID
FROM Department
WHERE DepartmentName = 'Sale';


-- Question 4: Lấy ra thông tin account có full name dài nhất

SELECT *
FROM `Account`
WHERE LENGTH(FullName) = (
    SELECT MAX(LENGTH(FullName))
    FROM `Account`
);


-- Question 5: Lấy ra thông tin account có full name dài nhất
-- và thuộc phòng ban có id = 3

SELECT *
FROM `Account`
WHERE DepartmentID = 3
AND LENGTH(FullName) = (
    SELECT MAX(LENGTH(FullName))
    FROM `Account`
    WHERE DepartmentID = 3
);


-- Question 6: Lấy ra tên group đã tham gia trước ngày 20/12/2019

SELECT g.GroupName
FROM `Group` g
JOIN GroupAccount ga
    ON g.GroupID = ga.GroupID
WHERE ga.JoinDate < '2019-12-20';


-- Question 7: Lấy ra ID của question có >= 4 câu trả lời

SELECT QuestionID
FROM Answer
GROUP BY QuestionID
HAVING COUNT(AnswerID) >= 4;


-- Question 8: Lấy ra các mã đề thi có thời gian thi >= 60 phút
-- và được tạo trước ngày 20/12/2019

SELECT Code
FROM Exam
WHERE Duration >= 60
AND CreateDate < '2019-12-20';


-- Question 9: Lấy ra 5 group được tạo gần đây nhất

SELECT *
FROM `Group`
ORDER BY CreateDate DESC
LIMIT 5;


-- Question 10: Đếm số nhân viên thuộc department có id = 2

SELECT COUNT(*) AS NumberOfEmployee
FROM `Account`
WHERE DepartmentID = 2;


-- Question 11: Lấy ra nhân viên có tên bắt đầu bằng chữ "D"
-- và kết thúc bằng chữ "o"

SELECT *
FROM `Account`
WHERE FullName LIKE 'D%o';


-- Question 12: Xóa tất cả các exam được tạo trước ngày 20/12/2019

DELETE FROM Exam
WHERE CreateDate < '2019-12-20';


-- Question 13: Xóa tất cả các question có nội dung bắt đầu bằng từ "câu hỏi"

DELETE FROM Question
WHERE Content LIKE 'câu hỏi%';


-- Question 14: Update thông tin của account có id = 5
-- thành tên "Nguyễn Bá Lộc"
-- và email thành loc.nguyenba@vti.com.vn

UPDATE `Account`
SET FullName = 'Nguyễn Bá Lộc',
    Email = 'loc.nguyenba@vti.com.vn'
WHERE AccountID = 5;


-- Kiểm tra lại thông tin AccountID = 5

SELECT *
FROM `Account`
WHERE AccountID = 5;


-- Question 15: Update account có id = 5 sẽ thuộc group có id = 4

INSERT INTO GroupAccount
(GroupID, AccountID, JoinDate)
VALUES
(4, 5, CURDATE());


-- Kiểm tra lại AccountID = 5 thuộc GroupID = 4

SELECT *
FROM GroupAccount
WHERE AccountID = 5
AND GroupID = 4;
