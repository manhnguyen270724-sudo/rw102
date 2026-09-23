import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DataStorage - noi khoi tao va luu tru du lieu mau dung chung
 * cho cac exercise trong Assignment 3
 */
public class DataStorage {

    // ===================== Department =====================
    // 3 department dung chung, phuc vu Exercise 5 - Question 1, 2, 4, 5
    public static Department dept1 = new Department(1, "Sale");
    public static Department dept2 = new Department(2, "Marketing");
    public static Department dept3 = new Department(3, "Sale"); // trung TEN voi dept1
                                                                  // -> de test equals() o Question 5

    public static List<Department> departments = new ArrayList<>(
            List.of(dept1, dept2, dept3));

    // ===================== Account =====================
    public static Account acc1 = new Account(1, "NguyenVanA@gmail.com", "nguyenvana", "Nguyen Van A",
            dept1, Position.DEV, LocalDate.of(2024, 1, 10));
    public static Account acc2 = new Account(2, "NguyenVanB@gmail.com", "nguyenvanb", "Nguyen Van B",
            dept2, Position.TEST, LocalDate.of(2024, 2, 15));

    public static List<Account> accounts = new ArrayList<>(
            List.of(acc1, acc2));

    // ===================== Group =====================
    // Chu y: co 1 group ten dung la "Java" (group4) va cac group KHAC co CHUA
    // chu "Java" (group1, group2) -> de phan biet ro Question 8 (chua "Java")
    // va Question 9 (ten dung la "Java") o Exercise 4
    public static Group group1 = new Group(1, "Java Fresher", acc1, LocalDate.of(2024, 5, 1));
    public static Group group2 = new Group(2, "Java Basic", acc2, LocalDate.of(2024, 5, 2));
    public static Group group3 = new Group(3, "C# Fresher", acc1, LocalDate.of(2024, 5, 3));
    public static Group group4 = new Group(4, "Java", acc2, LocalDate.of(2024, 5, 4));

    public static List<Group> groups = new ArrayList<>(
            List.of(group1, group2, group3, group4));
}
