USE testing_system_assignment_3;

-- EXERCISE 1: JOIN

-- Question 1: Danh sách nhân viên và thông tin phòng ban của họ
SELECT a.accountid, a.fullname, a.email, d.departmentname
FROM account a
JOIN department d ON a.departmentid = d.departmentid;


-- Question 2: Account được tạo sau ngày 20/12/2010
SELECT *
FROM account
WHERE createdate > '2010-12-20';


-- Question 3: Tất cả các Developer
SELECT a.*
FROM account a
JOIN position p ON a.positionid = p.positionid
WHERE p.positionname = 'Developer';


-- Question 4: Danh sách các phòng ban có > 3 nhân viên
SELECT d.departmentid, d.departmentname, COUNT(a.accountid) AS numEmployee
FROM department d
JOIN account a ON d.departmentid = a.departmentid
GROUP BY d.departmentid, d.departmentname
HAVING COUNT(a.accountid) > 3;


-- Question 5: Danh sách câu hỏi được sử dụng trong đề thi nhiều nhất
SELECT eq.questionid, COUNT(eq.examid) AS numExamsUsed
FROM examquestion eq
GROUP BY eq.questionid
HAVING COUNT(eq.examid) = (
    SELECT MAX(cnt)
    FROM (
        SELECT COUNT(examid) AS cnt
        FROM examquestion
        GROUP BY questionid
    ) t
);


-- Question 6: Thống kê mỗi Category Question được sử dụng trong bao nhiêu Question
SELECT c.categoryid, c.categoryname, COUNT(q.questionid) AS numQuestions
FROM categoryquestion c
LEFT JOIN question q ON c.categoryid = q.categoryid
GROUP BY c.categoryid, c.categoryname;


-- Question 7: Thống kê mỗi Question được sử dụng trong bao nhiêu Exam
SELECT q.questionid, q.content, COUNT(eq.examid) AS numExams
FROM question q
LEFT JOIN examquestion eq ON q.questionid = eq.questionid
GROUP BY q.questionid, q.content;


-- Question 8: Question có nhiều câu trả lời nhất
SELECT questionid, COUNT(answerid) AS numAnswers
FROM answer
GROUP BY questionid
HAVING COUNT(answerid) = (
    SELECT MAX(cnt)
    FROM (
        SELECT COUNT(answerid) AS cnt
        FROM answer
        GROUP BY questionid
    ) t
);


-- Question 9: Thống kê số lượng account trong mỗi group
SELECT g.groupid, g.groupname, COUNT(ga.accountid) AS numAccounts
FROM `group` g
LEFT JOIN groupaccount ga ON g.groupid = ga.groupid
GROUP BY g.groupid, g.groupname;


-- Question 10: Tìm chức vụ có ít người nhất
SELECT p.positionid, p.positionname, COUNT(a.accountid) AS numAccounts
FROM position p
LEFT JOIN account a ON p.positionid = a.positionid
GROUP BY p.positionid, p.positionname
ORDER BY numAccounts ASC
LIMIT 1;


-- Question 11: Thống kê mỗi phòng ban có bao nhiêu Dev, Test, Scrum Master, PM
SELECT
    d.departmentid,
    d.departmentname,
    SUM(CASE WHEN p.positionname = 'Developer' THEN 1 ELSE 0 END)     AS numDev,
    SUM(CASE WHEN p.positionname = 'Tester' THEN 1 ELSE 0 END)        AS numTester,
    SUM(CASE WHEN p.positionname = 'Scrum Master' THEN 1 ELSE 0 END)  AS numScrumMaster,
    SUM(CASE WHEN p.positionname = 'Project Manager' THEN 1 ELSE 0 END) AS numPM
FROM department d
LEFT JOIN account a ON d.departmentid = a.departmentid
LEFT JOIN position p ON a.positionid = p.positionid
GROUP BY d.departmentid, d.departmentname;


-- Question 12: Thông tin chi tiết của câu hỏi (basic info, loại câu hỏi, người tạo, câu trả lời)
SELECT
    q.questionid,
    q.content        AS questionContent,
    q.createdate,
    t.typename        AS questionType,
    c.categoryname    AS category,
    acc.fullname      AS creator,
    ans.content        AS answerContent,
    ans.iscorrect
FROM question q
LEFT JOIN typequestion t     ON q.typeid = t.typeid
LEFT JOIN categoryquestion c ON q.categoryid = c.categoryid
LEFT JOIN account acc        ON q.creatorid = acc.accountid
LEFT JOIN answer ans         ON q.questionid = ans.questionid
ORDER BY q.questionid;


-- Question 13: Số lượng câu hỏi của mỗi loại (tự luận / trắc nghiệm / ...)
SELECT t.typeid, t.typename, COUNT(q.questionid) AS numQuestions
FROM typequestion t
LEFT JOIN question q ON t.typeid = q.typeid
GROUP BY t.typeid, t.typename;


-- Question 14 & 15: Group không có account nào (câu hỏi bị lặp trong đề bài)
SELECT g.*
FROM `group` g
LEFT JOIN groupaccount ga ON g.groupid = ga.groupid
WHERE ga.accountid IS NULL;


-- Question 16: Question không có answer nào
SELECT q.*
FROM question q
LEFT JOIN answer a ON q.questionid = a.questionid
WHERE a.answerid IS NULL;


-- EXERCISE 2: UNION

-- Question 17
-- a) Account thuộc nhóm 1     b) Account thuộc nhóm 2     c) Ghép, loại trùng
SELECT a.*
FROM account a
JOIN groupaccount ga ON a.accountid = ga.accountid
WHERE ga.groupid = 1

UNION

SELECT a.*
FROM account a
JOIN groupaccount ga ON a.accountid = ga.accountid
WHERE ga.groupid = 2;


-- Question 18
-- a) Group > 5 thành viên     b) Group < 7 thành viên     c) Ghép
SELECT g.groupid, g.groupname, COUNT(ga.accountid) AS numMembers
FROM `group` g
JOIN groupaccount ga ON g.groupid = ga.groupid
GROUP BY g.groupid, g.groupname
HAVING COUNT(ga.accountid) > 5

UNION

SELECT g.groupid, g.groupname, COUNT(ga.accountid) AS numMembers
FROM `group` g
JOIN groupaccount ga ON g.groupid = ga.groupid
GROUP BY g.groupid, g.groupname
HAVING COUNT(ga.accountid) < 7;