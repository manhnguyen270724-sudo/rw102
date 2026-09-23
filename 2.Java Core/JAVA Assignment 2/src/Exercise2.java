import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Exercise 2: System.out.printf
 *
 * Ghi chu ve cac dinh dang hay dung:
 *   %d   -> so nguyen
 *   %,d  -> so nguyen co dau phan cach hang nghin
 *   %f   -> so thuc (mac dinh 6 so thap phan)
 *   %.4f -> so thuc lam tron 4 so thap phan
 *   %s   -> chuoi
 *   %-10s -> chuoi canh le trai, do rong 10 ky tu
 *   %n   -> xuong dong
 */
public class Exercise2 {

    /**
     * Question 1: In ra so nguyen = 5
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        int number = 5;
        System.out.printf("So nguyen vua khai bao: %d%n", number);
        System.out.println();
    }

    /**
     * Question 2: In so nguyen 100000000 thanh dinh dang 100,000,000
     */
    public void question2() {
        System.out.println("===== Question 2 =====");
        int number = 100_000_000;
        // %,d se tu dong chen dau phay phan cach hang nghin
        System.out.printf("So sau khi format: %,d%n", number);
        System.out.println();
    }

    /**
     * Question 3: In so thuc 5.567098 chi lay 4 so sau dau phay
     */
    public void question3() {
        System.out.println("===== Question 3 =====");
        double number = 5.567098;
        System.out.printf("So thuc lam tron 4 chu so: %.4f%n", number);
        System.out.println();
    }

    /**
     * Question 4: In ho va ten hoc sinh theo dinh dang co dau ngoac kep
     */
    public void question4() {
        System.out.println("===== Question 4 =====");
        String fullName = "Nguyen Van A";
        // \" dung de in ra dau ngoac kep
        System.out.printf("Ten toi la \"%s\" va toi dang doc than.%n", fullName);
        System.out.println();
    }

    /**
     * Question 5: Lay thoi gian hien tai va in theo dinh dang
     * 24/04/2020 11h:16p:20s
     */
    public void question5() {
        System.out.println("===== Question 5 =====");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH'h':mm'p':ss's'");
        System.out.printf("Thoi gian hien tai: %s%n", now.format(formatter));
        System.out.println();
    }

    /**
     * Question 6: In thong tin account theo dinh dang table giong trong Database
     */
    public void question6() {
        System.out.println("===== Question 6 =====");

        String rowFormat = "| %-4s | %-25s | %-15s | %-18s |%n";
        String line = "+------+---------------------------+-----------------+--------------------+";

        // Header cua table
        System.out.println(line);
        System.out.printf(rowFormat, "ID", "Email", "Full Name", "Department");
        System.out.println(line);

        // Noi dung cac dong
        for (Account account : DataStorage.accounts) {
            String departmentName = (account.getDepartment() == null)
                    ? "NULL"
                    : account.getDepartment().getDepartmentName();

            System.out.printf(rowFormat,
                    account.getAccountId(),
                    account.getEmail(),
                    account.getFullName(),
                    departmentName);
        }
        System.out.println(line);
        System.out.println();
    }
}
