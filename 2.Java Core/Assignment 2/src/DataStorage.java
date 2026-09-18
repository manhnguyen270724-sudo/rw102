import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DataStorage - noi khoi tao va luu tru du lieu mau
 * dung chung cho tat ca cac exercise
 */
public class DataStorage {

    // ===================== Department =====================
    public static Department dept1 = new Department(1, "Sale");
    public static Department dept2 = new Department(2, "Marketing");
    public static Department dept3 = new Department(3, "IT");

    public static List<Department> departments = new ArrayList<>(
            List.of(dept1, dept2, dept3));

    // ===================== Account =====================
    // Account thu 2 (acc2) khong co phong ban de test Question 1
    public static Account acc1 = new Account(1, "NguyenVanA@gmail.com", "nguyenvana", "Nguyen Van A",
            dept1, Position.DEV, LocalDate.of(2024, 1, 10));
    public static Account acc2 = new Account(2, "NguyenVanB@gmail.com", "nguyenvanb", "Nguyen Van B",
            null, Position.TEST, LocalDate.of(2024, 2, 15));
    public static Account acc3 = new Account(3, "NguyenVanC@gmail.com", "nguyenvanc", "Nguyen Van C",
            dept3, Position.SCRUM_MASTER, LocalDate.of(2024, 3, 20));
    public static Account acc4 = new Account(4, "NguyenVanD@gmail.com", "nguyenvand", "Nguyen Van D",
            dept2, Position.PM, LocalDate.of(2024, 4, 25));

    public static List<Account> accounts = new ArrayList<>(
            List.of(acc1, acc2, acc3, acc4));

    // ===================== Group =====================
    public static Group group1 = new Group(1, "Java Fresher", acc1, LocalDate.of(2024, 5, 1));
    public static Group group2 = new Group(2, "C# Fresher", acc2, LocalDate.of(2024, 5, 5));
    public static Group group3 = new Group(3, "Testing Fresher", acc3, LocalDate.of(2024, 5, 10));

    public static List<Group> groups = new ArrayList<>(
            List.of(group1, group2, group3));

    // ===================== GroupAccount =====================
    // Nhom thu 1 (group1) co 3 thanh vien -> phuc vu Question 5
    // acc2 tham gia 2 group -> phuc vu Question 2 va Question 6
    public static List<GroupAccount> groupAccounts = new ArrayList<>(List.of(
            new GroupAccount(group1, acc1, LocalDate.of(2024, 5, 1)),
            new GroupAccount(group1, acc2, LocalDate.of(2024, 5, 2)),
            new GroupAccount(group1, acc3, LocalDate.of(2024, 5, 3)),
            new GroupAccount(group2, acc2, LocalDate.of(2024, 5, 6)),
            new GroupAccount(group3, acc4, LocalDate.of(2024, 5, 11))
    ));

    // ===================== CategoryQuestion =====================
    public static CategoryQuestion cate1 = new CategoryQuestion(1, "Java");
    public static CategoryQuestion cate2 = new CategoryQuestion(2, "SQL");

    public static List<CategoryQuestion> categoryQuestions = new ArrayList<>(
            List.of(cate1, cate2));

    // ===================== Exam =====================
    public static Exam exam1 = new Exam(1, "JAVA01", "Kiem tra kien thuc Java co ban",
            cate1, 60, acc1, LocalDateTime.of(2020, 4, 24, 11, 16, 20));
    public static Exam exam2 = new Exam(2, "SQL01", "Kiem tra kien thuc SQL",
            cate2, 45, acc3, LocalDateTime.of(2021, 8, 15, 9, 30, 5));

    public static List<Exam> exams = new ArrayList<>(
            List.of(exam1, exam2));

    /**
     * Lay danh sach group ma mot account dang tham gia
     */
    public static List<Group> getGroupsOfAccount(Account account) {
        List<Group> result = new ArrayList<>();
        for (GroupAccount ga : groupAccounts) {
            if (ga.getAccount() != null && ga.getAccount().getAccountId() == account.getAccountId()) {
                result.add(ga.getGroup());
            }
        }
        return result;
    }

    /**
     * Dem so luong account trong mot group
     */
    public static int countAccountsInGroup(Group group) {
        int count = 0;
        for (GroupAccount ga : groupAccounts) {
            if (ga.getGroup() != null && ga.getGroup().getGroupId() == group.getGroupId()) {
                count++;
            }
        }
        return count;
    }
}
