USE Testing_System_Assignment_3;

-- Question 1
-- Nhập tên phòng ban -> in ra tất cả account thuộc phòng ban đó
DROP PROCEDURE IF EXISTS sp_GetAccountsByDepartment;

DELIMITER //
CREATE PROCEDURE sp_GetAccountsByDepartment(IN p_DepartmentName VARCHAR(50))
BEGIN
    SELECT a.*
    FROM `Account` a
    JOIN Department d ON a.DepartmentID = d.DepartmentID
    WHERE d.DepartmentName = p_DepartmentName;
END //
DELIMITER ;

-- CALL sp_GetAccountsByDepartment('IT');
-- Question 2
-- In ra số lượng account trong mỗi group
DROP PROCEDURE IF EXISTS sp_CountAccountsPerGroup;

DELIMITER //
CREATE PROCEDURE sp_CountAccountsPerGroup()
BEGIN
    SELECT g.GroupID, g.GroupName, COUNT(ga.AccountID) AS AccountCount
    FROM `Group` g
    LEFT JOIN GroupAccount ga ON g.GroupID = ga.GroupID
    GROUP BY g.GroupID, g.GroupName;
END //
DELIMITER ;

-- CALL sp_CountAccountsPerGroup();


-- Question 3
-- Thống kê mỗi type question có bao nhiêu question được tạo trong tháng hiện tại
DROP PROCEDURE IF EXISTS sp_CountQuestionsByTypeThisMonth;

DELIMITER //
CREATE PROCEDURE sp_CountQuestionsByTypeThisMonth()
BEGIN
    SELECT t.TypeID, t.TypeName, COUNT(q.QuestionID) AS QuestionCount
    FROM TypeQuestion t
    LEFT JOIN Question q
        ON t.TypeID = q.TypeID
        AND MONTH(q.CreateDate) = MONTH(CURDATE())
        AND YEAR(q.CreateDate) = YEAR(CURDATE())
    GROUP BY t.TypeID, t.TypeName;
END //
DELIMITER ;

-- CALL sp_CountQuestionsByTypeThisMonth();
-- Question 4
-- Trả ra id của type question có nhiều câu hỏi nhất
DROP PROCEDURE IF EXISTS sp_GetTypeWithMostQuestions;

DELIMITER //
CREATE PROCEDURE sp_GetTypeWithMostQuestions(OUT p_TypeID INT)
BEGIN
    SELECT TypeID INTO p_TypeID
    FROM Question
    GROUP BY TypeID
    ORDER BY COUNT(QuestionID) DESC
    LIMIT 1;
END //
DELIMITER ;

-- CALL sp_GetTypeWithMostQuestions(@typeId); SELECT @typeId;


-- Question 5
-- Dùng store ở Question 4 để tìm ra tên của type question
DROP PROCEDURE IF EXISTS sp_GetTypeNameWithMostQuestions;

DELIMITER //
CREATE PROCEDURE sp_GetTypeNameWithMostQuestions()
BEGIN
    DECLARE v_TypeID INT;

    CALL sp_GetTypeWithMostQuestions(v_TypeID);

    SELECT TypeID, TypeName
    FROM TypeQuestion
    WHERE TypeID = v_TypeID;
END //
DELIMITER ;

-- CALL sp_GetTypeNameWithMostQuestions();


-- Question 6
-- Nhập 1 chuỗi -> trả về group có tên chứa chuỗi
-- HOẶC user có username chứa chuỗi
DROP PROCEDURE IF EXISTS sp_SearchGroupOrUser;

DELIMITER //
CREATE PROCEDURE sp_SearchGroupOrUser(IN p_Keyword VARCHAR(100))
BEGIN
    SELECT GroupID, GroupName, NULL AS AccountID, NULL AS Username, 'Group' AS ResultType
    FROM `Group`
    WHERE GroupName LIKE CONCAT('%', p_Keyword, '%')

    UNION ALL

    SELECT NULL, NULL, AccountID, Username, 'User'
    FROM `Account`
    WHERE Username LIKE CONCAT('%', p_Keyword, '%');
END //
DELIMITER ;

-- CALL sp_SearchGroupOrUser('java');


-- Question 7
-- Nhập fullName, email -> tự động gán:
--   username = phần trước @ của email
--   positionID = Developer (default)
--   departmentID = phòng chờ (default, tạo mới nếu chưa có)
-- In ra kết quả tạo thành công
DROP PROCEDURE IF EXISTS sp_CreateAccount;

DELIMITER //
CREATE PROCEDURE sp_CreateAccount(IN p_FullName VARCHAR(100), IN p_Email VARCHAR(100))
BEGIN
    DECLARE v_Username VARCHAR(50);
    DECLARE v_PositionID INT;
    DECLARE v_DepartmentID INT;

    SET v_Username = SUBSTRING_INDEX(p_Email, '@', 1);

    SELECT PositionID INTO v_PositionID
    FROM `Position`
    WHERE PositionName = 'Developer'
    LIMIT 1;

    SELECT DepartmentID INTO v_DepartmentID
    FROM Department
    WHERE DepartmentName = 'Cho viec'
    LIMIT 1;

    IF v_DepartmentID IS NULL THEN
        INSERT INTO Department (DepartmentName) VALUES ('Cho viec');
        SET v_DepartmentID = LAST_INSERT_ID();
    END IF;

    INSERT INTO `Account` (Email, Username, FullName, DepartmentID, PositionID, CreateDate)
    VALUES (p_Email, v_Username, p_FullName, v_DepartmentID, v_PositionID, CURDATE());

    SELECT CONCAT('Tao tai khoan thanh cong: ', v_Username) AS Result;
END //
DELIMITER ;

-- CALL sp_CreateAccount('Nguyen Van X', 'nguyenvanx@gmail.com');


-- Question 8
-- Nhập 'Essay' hoặc 'Multiple-Choice' -> tìm question thuộc type đó
-- có content dài nhất
DROP PROCEDURE IF EXISTS sp_GetLongestQuestionByType;

DELIMITER //
CREATE PROCEDURE sp_GetLongestQuestionByType(IN p_TypeName VARCHAR(50))
BEGIN
    SELECT q.QuestionID, q.Content, LENGTH(q.Content) AS ContentLength
    FROM Question q
    JOIN TypeQuestion t ON q.TypeID = t.TypeID
    WHERE t.TypeName = p_TypeName
    ORDER BY LENGTH(q.Content) DESC
    LIMIT 1;
END //
DELIMITER ;

-- CALL sp_GetLongestQuestionByType('Essay');


-- Question 9
-- Xóa exam dựa vào ID (xóa luôn các dòng liên quan ở ExamQuestion
-- để tránh lỗi khóa ngoại)
DROP PROCEDURE IF EXISTS sp_DeleteExamByID;

DELIMITER //
CREATE PROCEDURE sp_DeleteExamByID(IN p_ExamID INT)
BEGIN
    DELETE FROM ExamQuestion WHERE ExamID = p_ExamID;
    DELETE FROM Exam WHERE ExamID = p_ExamID;
END //
DELIMITER ;

-- CALL sp_DeleteExamByID(1);


-- Question 10
-- Tìm các exam được tạo từ 3 năm trước và xóa các exam đó
-- (dùng store ở Question 9), sau đó in số lượng record đã
-- xóa ở các bảng liên quan
DROP PROCEDURE IF EXISTS sp_DeleteOldExams;

DELIMITER //
CREATE PROCEDURE sp_DeleteOldExams()
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE v_ExamID INT;
    DECLARE v_EqCount INT DEFAULT 0;
    DECLARE v_ExamCount INT DEFAULT 0;
    DECLARE v_ExamQuestionCount INT DEFAULT 0;

    DECLARE cur CURSOR FOR
        SELECT ExamID
        FROM Exam
        WHERE CreateDate <= DATE_SUB(CURDATE(), INTERVAL 3 YEAR);

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO v_ExamID;
        IF done THEN
            LEAVE read_loop;
        END IF;

        SELECT COUNT(*) INTO v_EqCount FROM ExamQuestion WHERE ExamID = v_ExamID;
        SET v_ExamQuestionCount = v_ExamQuestionCount + v_EqCount;

        CALL sp_DeleteExamByID(v_ExamID);
        SET v_ExamCount = v_ExamCount + 1;
    END LOOP;

    CLOSE cur;

    SELECT v_ExamCount AS ExamsRemoved, v_ExamQuestionCount AS ExamQuestionsRemoved;
END //
DELIMITER ;

-- CALL sp_DeleteOldExams();


-- Question 11
-- Xóa phòng ban theo tên; các account thuộc phòng ban đó
-- được chuyển về phòng ban default (phòng chờ việc)
DROP PROCEDURE IF EXISTS sp_DeleteDepartment;

DELIMITER //
CREATE PROCEDURE sp_DeleteDepartment(IN p_DepartmentName VARCHAR(50))
BEGIN
    DECLARE v_DepartmentID INT;
    DECLARE v_DefaultDeptID INT;

    SELECT DepartmentID INTO v_DepartmentID
    FROM Department
    WHERE DepartmentName = p_DepartmentName
    LIMIT 1;

    IF v_DepartmentID IS NOT NULL THEN
        SELECT DepartmentID INTO v_DefaultDeptID
        FROM Department
        WHERE DepartmentName = 'Cho viec'
        LIMIT 1;

        IF v_DefaultDeptID IS NULL THEN
            INSERT INTO Department (DepartmentName) VALUES ('Cho viec');
            SET v_DefaultDeptID = LAST_INSERT_ID();
        END IF;

        -- Không xóa nhầm phòng chờ việc thành chính nó
        IF v_DepartmentID <> v_DefaultDeptID THEN
            UPDATE `Account`
            SET DepartmentID = v_DefaultDeptID
            WHERE DepartmentID = v_DepartmentID;

            DELETE FROM Department WHERE DepartmentID = v_DepartmentID;

            SELECT CONCAT('Da xoa phong ban: ', p_DepartmentName) AS Result;
        ELSE
            SELECT 'Khong the xoa phong ban cho viec (default)' AS Result;
        END IF;
    ELSE
        SELECT CONCAT('Khong tim thay phong ban: ', p_DepartmentName) AS Result;
    END IF;
END //
DELIMITER ;

-- CALL sp_DeleteDepartment('Sale');


-- Question 12
-- In ra mỗi tháng có bao nhiêu câu hỏi được tạo trong năm nay
DROP PROCEDURE IF EXISTS sp_CountQuestionsByMonthThisYear;

DELIMITER //
CREATE PROCEDURE sp_CountQuestionsByMonthThisYear()
BEGIN
    SELECT MONTH(CreateDate) AS Month, COUNT(*) AS QuestionCount
    FROM Question
    WHERE YEAR(CreateDate) = YEAR(CURDATE())
    GROUP BY MONTH(CreateDate)
    ORDER BY Month;
END //
DELIMITER ;

-- CALL sp_CountQuestionsByMonthThisYear();


-- Question 13
-- In ra mỗi tháng có bao nhiêu câu hỏi được tạo trong
-- 6 tháng gần đây nhất
DROP PROCEDURE IF EXISTS sp_CountQuestionsLast6Months;

DELIMITER //
CREATE PROCEDURE sp_CountQuestionsLast6Months()
BEGIN
    SELECT DATE_FORMAT(CreateDate, '%Y-%m') AS YearMonth, COUNT(*) AS QuestionCount
    FROM Question
    WHERE CreateDate >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
    GROUP BY DATE_FORMAT(CreateDate, '%Y-%m')
    ORDER BY YearMonth;
END //
DELIMITER ;

-- CALL sp_CountQuestionsLast6Months();