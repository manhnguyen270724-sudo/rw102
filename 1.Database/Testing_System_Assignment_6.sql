USE testing_system_assignment_3;
/*
   CAU 1: Khong cho tao Group co ngay tao (createdate) truoc hon 1 nam
   so voi hien tai.
   */
DROP TRIGGER IF EXISTS trg_group_check_createdate;

DELIMITER $$
CREATE TRIGGER trg_group_check_createdate
BEFORE INSERT ON `group`
FOR EACH ROW
BEGIN
    -- Neu ngay tao nho hon (curdate - 1 nam) => qua cu => chan lai
    IF NEW.createdate < DATE_SUB(CURDATE(), INTERVAL 1 YEAR) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Ngay tao Group khong duoc truoc hon 1 nam so voi hien tai';
    END IF;
END$$
DELIMITER ;


/* 
   CAU 2: Khong cho them user vao department "Sale" nua.
   Khi INSERT account co departmentid = id cua "Sale" thi bao loi.

   CAU 6: Khi tao account ma KHONG dien departmentid, tu dong gan vao
   phong ban "waiting Department" (khong dung DEFAULT cua cot).

   CAU 8: Chuan hoa du lieu cot gender: "Nam"/"Nu"/"Chua xac dinh"
   (nguoi dung nhap) => "M"/"F"/"U" (dung format trong database).


    */
ALTER TABLE account ADD COLUMN IF NOT EXISTS gender VARCHAR(20) NULL;

DROP TRIGGER IF EXISTS trg_account_before_insert;

DELIMITER $$
CREATE TRIGGER trg_account_before_insert
BEFORE INSERT ON account
FOR EACH ROW
BEGIN
    DECLARE waitingDeptId INT;
    DECLARE saleDeptId INT;

    -- ---------- CAU 6: khong nhap departmentid -> gan "waiting Department" ----------
    IF NEW.departmentid IS NULL THEN
        SELECT departmentid INTO waitingDeptId
        FROM department
        WHERE departmentname = 'waiting Department'
        LIMIT 1;

        -- Neu phong ban "waiting Department" chua ton tai thi tao moi
        IF waitingDeptId IS NULL THEN
            INSERT INTO department(departmentname) VALUES ('waiting Department');
            SET waitingDeptId = LAST_INSERT_ID();
        END IF;

        SET NEW.departmentid = waitingDeptId;
    END IF;

    -- ---------- CAU 2: chan them user vao department "Sale" ----------
    SELECT departmentid INTO saleDeptId
    FROM department
    WHERE departmentname = 'Sale'
    LIMIT 1;

    IF NEW.departmentid = saleDeptId THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Department "Sale" cannot add more user';
    END IF;

    -- ---------- CAU 8: chuan hoa gender ----------
    IF NEW.gender = 'Nam' THEN
        SET NEW.gender = 'M';
    ELSEIF NEW.gender = 'Nu' OR NEW.gender = 'Nữ' THEN
        SET NEW.gender = 'F';
    ELSEIF NEW.gender = 'Chua xac dinh' OR NEW.gender = 'Chưa xác định' THEN
        SET NEW.gender = 'U';
    END IF;
END$$
DELIMITER ;

-- Trigger rieng cho UPDATE, vi UPDATE la 1 su kien khac voi INSERT
-- (CAU 8 chi noi ve viec "nhap vao" nhung neu nguoi dung SUA gender
-- sau nay thi cung nen chuan hoa lai cho dung).
DROP TRIGGER IF EXISTS trg_account_gender_before_update;

DELIMITER $$
CREATE TRIGGER trg_account_gender_before_update
BEFORE UPDATE ON account
FOR EACH ROW
BEGIN
    IF NEW.gender = 'Nam' THEN
        SET NEW.gender = 'M';
    ELSEIF NEW.gender = 'Nu' OR NEW.gender = 'Nữ' THEN
        SET NEW.gender = 'F';
    ELSEIF NEW.gender = 'Chua xac dinh' OR NEW.gender = 'Chưa xác định' THEN
        SET NEW.gender = 'U';
    END IF;
END$$
DELIMITER ;


/*
   CAU 3: 1 Group co nhieu nhat 5 user (dem so dong trong groupaccount
   co cung groupid, neu da bang 5 thi khong cho them nua).
  */
DROP TRIGGER IF EXISTS trg_groupaccount_max5;

DELIMITER $$
CREATE TRIGGER trg_groupaccount_max5
BEFORE INSERT ON groupaccount
FOR EACH ROW
BEGIN
    DECLARE memberCount INT;

    SELECT COUNT(*) INTO memberCount
    FROM groupaccount
    WHERE groupid = NEW.groupid;

    IF memberCount >= 5 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Group nay da du 5 thanh vien, khong the them nua';
    END IF;
END$$
DELIMITER ;


/* 
   CAU 4: 1 bai thi (exam) co nhieu nhat 10 question (dem so dong trong
   examquestion co cung examid).
    */
DROP TRIGGER IF EXISTS trg_examquestion_max10;

DELIMITER $$
CREATE TRIGGER trg_examquestion_max10
BEFORE INSERT ON examquestion
FOR EACH ROW
BEGIN
    DECLARE questionCount INT;

    SELECT COUNT(*) INTO questionCount
    FROM examquestion
    WHERE examid = NEW.examid;

    IF questionCount >= 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bai thi nay da du 10 cau hoi, khong the them nua';
    END IF;
END$$
DELIMITER ;


/* 
   CAU 5: Khong cho xoa account co email = 'admin@gmail.com'.
   Cac account khac thi cho xoa, va phai xoa luon tat ca du lieu lien quan
   
*/
DROP TRIGGER IF EXISTS trg_account_before_delete;

DELIMITER $$
CREATE TRIGGER trg_account_before_delete
BEFORE DELETE ON account
FOR EACH ROW
BEGIN
    IF OLD.email = 'admin@gmail.com' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Khong the xoa tai khoan admin';
    ELSE
        -- 1. Xoa answer cua cac question do user nay tao
        DELETE a FROM answer a
        INNER JOIN question q ON a.questionid = q.questionid
        WHERE q.creatorid = OLD.accountid;

        -- 2. Xoa examquestion cua cac question do user nay tao
        DELETE eq FROM examquestion eq
        INNER JOIN question q ON eq.questionid = q.questionid
        WHERE q.creatorid = OLD.accountid;

        -- 3. Xoa examquestion cua cac exam do user nay tao
        DELETE eq FROM examquestion eq
        INNER JOIN exam e ON eq.examid = e.examid
        WHERE e.creatorid = OLD.accountid;

        -- 4. Xoa cac question do user nay tao (sau khi da xoa answer/examquestion lien quan)
        DELETE FROM question WHERE creatorid = OLD.accountid;

        -- 5. Xoa cac exam do user nay tao
        DELETE FROM exam WHERE creatorid = OLD.accountid;

        -- 6. Xoa groupaccount cua cac group do user nay tao (thanh vien cua group do)
        DELETE ga FROM groupaccount ga
        INNER JOIN `group` g ON ga.groupid = g.groupid
        WHERE g.creatorid = OLD.accountid;

        -- 7. Xoa cac group do user nay tao
        DELETE FROM `group` WHERE creatorid = OLD.accountid;

        -- 8. Xoa cac ban ghi groupaccount ma user nay la thanh vien (o group khac)
        DELETE FROM groupaccount WHERE accountid = OLD.accountid;
    END IF;
END$$
DELIMITER ;


/* 
   CAU 7: 1 question chi duoc toi da 4 answers, trong do toi da 2 dap an
   dung (iscorrect = 1).
   */
DROP TRIGGER IF EXISTS trg_answer_max4_2correct;

DELIMITER $$
CREATE TRIGGER trg_answer_max4_2correct
BEFORE INSERT ON answer
FOR EACH ROW
BEGIN
    DECLARE totalAnswers INT;
    DECLARE correctAnswers INT;

    -- Kiem tra tong so answer cua question nay
    SELECT COUNT(*) INTO totalAnswers
    FROM answer
    WHERE questionid = NEW.questionid;

    IF totalAnswers >= 4 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Question nay da du 4 answers, khong the them nua';
    END IF;

    -- Neu answer moi la dap an dung thi kiem tra da co bao nhieu dap an dung
    IF NEW.iscorrect = 1 THEN
        SELECT COUNT(*) INTO correctAnswers
        FROM answer
        WHERE questionid = NEW.questionid AND iscorrect = 1;

        IF correctAnswers >= 2 THEN
            SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Question nay da du 2 dap an dung, khong the them nua';
        END IF;
    END IF;
END$$
DELIMITER ;


/*
   CAU 9: Khong cho xoa bai thi (exam) moi tao duoc chua du 2 ngay
   (tinh tu createdate den hien tai).
   */
DROP TRIGGER IF EXISTS trg_exam_no_delete_2days;

DELIMITER $$
CREATE TRIGGER trg_exam_no_delete_2days
BEFORE DELETE ON exam
FOR EACH ROW
BEGIN
    IF DATEDIFF(CURDATE(), OLD.createdate) < 2 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bai thi moi tao chua du 2 ngay, khong the xoa';
    END IF;
END$$
DELIMITER ;


/*
   CAU 10: NOI DUNG BI THIEU / BI CAT TRONG DE BAI
*/
DROP TRIGGER IF EXISTS trg_question_no_update_if_used;
DROP TRIGGER IF EXISTS trg_question_no_delete_if_used;

DELIMITER $$
CREATE TRIGGER trg_question_no_update_if_used
BEFORE UPDATE ON question
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1 FROM examquestion WHERE questionid = OLD.questionid
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Question dang duoc su dung trong bai thi, khong the sua';
    END IF;
END$$

CREATE TRIGGER trg_question_no_delete_if_used
BEFORE DELETE ON question
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1 FROM examquestion WHERE questionid = OLD.questionid
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Question dang duoc su dung trong bai thi, khong the xoa';
    END IF;
END$$
DELIMITER ;