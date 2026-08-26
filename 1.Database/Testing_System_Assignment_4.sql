USE Testing_System_Assignment_1;
DROP VIEW IF EXISTS v_account_sale;
CREATE VIEW v_account_sale AS
SELECT
    a.AccountID,
    a.Email,
    a.Username,
    a.FullName,
    a.DepartmentID,
    a.PositionID,
    a.CreateDate
FROM `Account` a
WHERE a.DepartmentID = (
        SELECT d.DepartmentID
        FROM Department d
        WHERE d.DepartmentName = 'Sale'
      );

-- Kiểm tra
SELECT * FROM v_account_sale;
-- Question 2: Tạo view chứa thông tin các account tham gia nhiều group nhất
DROP VIEW IF EXISTS v_account_most_group;

CREATE VIEW v_account_most_group AS
WITH group_count AS (
    SELECT
        ga.AccountID,
        COUNT(ga.GroupID) AS TotalGroup
    FROM GroupAccount ga
    GROUP BY ga.AccountID
)
SELECT
    a.AccountID,
    a.Email,
    a.Username,
    a.FullName,
    gc.TotalGroup
FROM group_count gc
JOIN `Account` a
     ON a.AccountID = gc.AccountID
WHERE gc.TotalGroup = (
        SELECT MAX(TotalGroup) FROM group_count
      );

-- Kiểm tra
SELECT * FROM v_account_most_group;


-- Question 3: Tạo view chứa các câu hỏi có content quá dài (> 300 từ)

DROP VIEW IF EXISTS v_question_too_long;

CREATE VIEW v_question_too_long AS
SELECT
    q.QuestionID,
    q.Content,
    q.CategoryID,
    q.TypeID,
    q.CreatorID,
    q.CreateDate
FROM Question q
WHERE (LENGTH(TRIM(q.Content))
       - LENGTH(REPLACE(TRIM(q.Content), ' ', ''))
       + 1) > 300;

-- Xem các câu hỏi bị coi là quá dài trước khi xóa
SELECT * FROM v_question_too_long;

-- Xóa dữ liệu ở các bảng con trước (do ràng buộc khóa ngoại)
DELETE FROM Answer
WHERE QuestionID IN (SELECT QuestionID FROM v_question_too_long);

DELETE FROM ExamQuestion
WHERE QuestionID IN (SELECT QuestionID FROM v_question_too_long);

-- Xóa câu hỏi thông qua view
DELETE FROM v_question_too_long;

-- Kiểm tra lại (kết quả phải rỗng)
SELECT * FROM v_question_too_long;


-- Question 4: Tạo view chứa danh sách các phòng ban có nhiều nhân viên nhất
DROP VIEW IF EXISTS v_department_most_account;

CREATE VIEW v_department_most_account AS
WITH dept_count AS (
    SELECT
        a.DepartmentID,
        COUNT(a.AccountID) AS TotalAccount
    FROM `Account` a
    WHERE a.DepartmentID IS NOT NULL
    GROUP BY a.DepartmentID
)
SELECT
    d.DepartmentID,
    d.DepartmentName,
    dc.TotalAccount
FROM dept_count dc
JOIN Department d
     ON d.DepartmentID = dc.DepartmentID
WHERE dc.TotalAccount = (
        SELECT MAX(TotalAccount) FROM dept_count
      );

-- Kiểm tra
SELECT * FROM v_department_most_account;


-- Question 5: Tạo view chứa tất cả các câu hỏi do user họ Nguyễn tạo

CREATE VIEW v_question_by_nguyen AS
SELECT
    q.QuestionID,
    q.Content,
    q.CategoryID,
    q.TypeID,
    q.CreatorID,
    q.CreateDate
FROM Question q
WHERE q.CreatorID IN (
        SELECT a.AccountID
        FROM `Account` a
        WHERE a.FullName LIKE 'Nguyen%'
      );

-- Kiểm tra
SELECT * FROM v_question_by_nguyen;


-- KIỂM TRA TOÀN BỘ VIEW ĐÃ TẠO

SHOW FULL TABLES
WHERE Table_type = 'VIEW';