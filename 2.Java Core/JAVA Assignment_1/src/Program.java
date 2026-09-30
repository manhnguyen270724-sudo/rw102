import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Program - class chua main() method
 * Khoi tao it nhat 3 doi tuong cho moi table va in ra gia tri
 */
public class Program {

    public static void main(String[] args) {

        // ===================== 1. Department =====================
        Department dept1 = new Department(1, "Sale");
        Department dept2 = new Department(2, "Marketing");
        Department dept3 = new Department(3, "IT");
        List<Department> departments = new ArrayList<>(List.of(dept1, dept2, dept3));

        // ===================== 2. Position =====================
        Position pos1 = new Position(1, "Dev");
        Position pos2 = new Position(2, "Test");
        Position pos3 = new Position(3, "Scrum Master");
        List<Position> positions = new ArrayList<>(List.of(pos1, pos2, pos3));

        // ===================== 3. Account =====================
        Account acc1 = new Account(1, "an.nguyen@gmail.com", "annguyen", "Nguyen Van An",
                dept3, pos1, LocalDate.of(2024, 1, 10));
        Account acc2 = new Account(2, "binh.tran@gmail.com", "binhtran", "Tran Thi Binh",
                dept3, pos2, LocalDate.of(2024, 2, 15));
        Account acc3 = new Account(3, "cuong.le@gmail.com", "cuongle", "Le Van Cuong",
                dept1, pos3, LocalDate.of(2024, 3, 20));
        List<Account> accounts = new ArrayList<>(List.of(acc1, acc2, acc3));

        // ===================== 4. Group =====================
        Group group1 = new Group(1, "Java Backend Team", acc1, LocalDate.of(2024, 4, 1));
        Group group2 = new Group(2, "QA Team", acc2, LocalDate.of(2024, 4, 5));
        Group group3 = new Group(3, "Scrum Team", acc3, LocalDate.of(2024, 4, 10));
        List<Group> groups = new ArrayList<>(List.of(group1, group2, group3));

        // ===================== 5. GroupAccount =====================
        GroupAccount groupAccount1 = new GroupAccount(group1, acc1, LocalDate.of(2024, 4, 1));
        GroupAccount groupAccount2 = new GroupAccount(group1, acc2, LocalDate.of(2024, 4, 2));
        GroupAccount groupAccount3 = new GroupAccount(group2, acc3, LocalDate.of(2024, 4, 6));
        List<GroupAccount> groupAccounts = new ArrayList<>(List.of(groupAccount1, groupAccount2, groupAccount3));

        // ===================== 6. TypeQuestion =====================
        TypeQuestion type1 = new TypeQuestion(1, "Essay");
        TypeQuestion type2 = new TypeQuestion(2, "Multiple-Choice");
        TypeQuestion type3 = new TypeQuestion(3, "True-False");
        List<TypeQuestion> typeQuestions = new ArrayList<>(List.of(type1, type2, type3));

        // ===================== 7. CategoryQuestion =====================
        CategoryQuestion cate1 = new CategoryQuestion(1, "Java");
        CategoryQuestion cate2 = new CategoryQuestion(2, "SQL");
        CategoryQuestion cate3 = new CategoryQuestion(3, "Postman");
        List<CategoryQuestion> categoryQuestions = new ArrayList<>(List.of(cate1, cate2, cate3));

        // ===================== 8. Question =====================
        Question question1 = new Question(1, "Java co bao nhieu kieu du lieu nguyen thuy?",
                cate1, type2, acc1, LocalDate.of(2024, 5, 1));
        Question question2 = new Question(2, "Trinh bay cach hoat dong cua JOIN trong SQL",
                cate2, type1, acc2, LocalDate.of(2024, 5, 2));
        Question question3 = new Question(3, "Postman co the dung de test API hay khong?",
                cate3, type3, acc3, LocalDate.of(2024, 5, 3));
        List<Question> questions = new ArrayList<>(List.of(question1, question2, question3));

        // ===================== 9. Answer =====================
        Answer answer1 = new Answer(1, "8 kieu du lieu nguyen thuy", question1, true);
        Answer answer2 = new Answer(2, "JOIN dung de ket hop du lieu tu nhieu bang", question2, true);
        Answer answer3 = new Answer(3, "Khong, Postman chi dung de gui email", question3, false);
        List<Answer> answers = new ArrayList<>(List.of(answer1, answer2, answer3));

        // ===================== 10. Exam =====================
        Exam exam1 = new Exam(1, "JAVA01", "Kiem tra kien thuc Java co ban",
                cate1, 60, acc1, LocalDate.of(2024, 6, 1));
        Exam exam2 = new Exam(2, "SQL01", "Kiem tra kien thuc SQL",
                cate2, 45, acc2, LocalDate.of(2024, 6, 2));
        Exam exam3 = new Exam(3, "PM01", "Kiem tra kien thuc Postman",
                cate3, 30, acc3, LocalDate.of(2024, 6, 3));
        List<Exam> exams = new ArrayList<>(List.of(exam1, exam2, exam3));

        // ===================== 11. ExamQuestion =====================
        ExamQuestion examQuestion1 = new ExamQuestion(exam1, question1);
        ExamQuestion examQuestion2 = new ExamQuestion(exam2, question2);
        ExamQuestion examQuestion3 = new ExamQuestion(exam3, question3);
        List<ExamQuestion> examQuestions = new ArrayList<>(List.of(examQuestion1, examQuestion2, examQuestion3));

        // ===================== In gia tri ra man hinh =====================
        System.out.println("========== DEPARTMENT ==========");
        for (Department d : departments) {
            System.out.println("Department Name: " + d.getDepartmentName());
        }

        System.out.println("\n========== POSITION ==========");
        for (Position p : positions) {
            System.out.println("Position Name: " + p.getPositionName());
        }

        System.out.println("\n========== ACCOUNT ==========");
        for (Account a : accounts) {
            System.out.println("Full Name: " + a.getFullName() + " - Email: " + a.getEmail());
        }

        System.out.println("\n========== GROUP ==========");
        for (Group g : groups) {
            System.out.println("Group Name: " + g.getGroupName() + " - Creator: " + g.getCreator().getFullName());
        }

        System.out.println("\n========== GROUP ACCOUNT ==========");
        for (GroupAccount ga : groupAccounts) {
            System.out.println(ga.getAccount().getFullName() + " joined " + ga.getGroup().getGroupName()
                    + " on " + ga.getJoinDate());
        }

        System.out.println("\n========== TYPE QUESTION ==========");
        for (TypeQuestion t : typeQuestions) {
            System.out.println("Type Name: " + t.getTypeName());
        }

        System.out.println("\n========== CATEGORY QUESTION ==========");
        for (CategoryQuestion c : categoryQuestions) {
            System.out.println("Category Name: " + c.getCategoryName());
        }

        System.out.println("\n========== QUESTION ==========");
        for (Question q : questions) {
            System.out.println("Content: " + q.getContent() + " | Category: " + q.getCategory().getCategoryName());
        }

        System.out.println("\n========== ANSWER ==========");
        for (Answer a : answers) {
            System.out.println("Content: " + a.getContent() + " | Is Correct: " + a.isCorrect());
        }

        System.out.println("\n========== EXAM ==========");
        for (Exam e : exams) {
            System.out.println("Title: " + e.getTitle() + " | Code: " + e.getCode() + " | Duration: " + e.getDuration());
        }

        System.out.println("\n========== EXAM QUESTION ==========");
        for (ExamQuestion eq : examQuestions) {
            System.out.println("Exam: " + eq.getExam().getTitle() + " -> Question: " + eq.getQuestion().getContent());
        }
    }
}
