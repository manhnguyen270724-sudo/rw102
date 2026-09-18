import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Exercise 5: Input from console
 *
 * Ghi chu ve Scanner:
 *   scanner.nextInt()     -> doc so nguyen
 *   scanner.nextDouble()  -> doc so thuc
 *   scanner.nextLine()    -> doc ca dong (dung cho chuoi co dau cach)
 *
 * Luu y quan trong: sau khi dung nextInt()/nextDouble(), can goi them
 * nextLine() de "an" ky tu Enter con sot lai, neu khong nextLine() tiep theo
 * se doc duoc chuoi rong.
 */
public class Exercise5 {

    private final Scanner scanner = ConsoleUtils.SCANNER;
    private final Random random = new Random();

    /**
     * Question 1: Cho phep nguoi dung nhap 3 so nguyen
     */
    public void question1() {
        System.out.println("===== Question 1 =====");
        int[] numbers = new int[3];

        for (int i = 0; i < numbers.length; i++) {
            System.out.print("Moi ban nhap so nguyen thu " + (i + 1) + ": ");
            numbers[i] = scanner.nextInt();
        }
        scanner.nextLine(); // an ky tu Enter con sot lai

        System.out.println("Ba so nguyen ban vua nhap la: "
                + numbers[0] + ", " + numbers[1] + ", " + numbers[2]);
        System.out.println();
    }

    /**
     * Question 2: Cho phep nguoi dung nhap 2 so thuc
     */
    public void question2() {
        System.out.println("===== Question 2 =====");

        System.out.print("Moi ban nhap so thuc thu 1: ");
        double firstNumber = scanner.nextDouble();
        System.out.print("Moi ban nhap so thuc thu 2: ");
        double secondNumber = scanner.nextDouble();
        scanner.nextLine();

        System.out.printf("Hai so thuc ban vua nhap la: %.2f va %.2f%n", firstNumber, secondNumber);
        System.out.println();
    }

    /**
     * Question 3: Cho phep nguoi dung nhap ho va ten
     */
    public void question3() {
        System.out.println("===== Question 3 =====");

        System.out.print("Moi ban nhap ho va ten: ");
        String fullName = scanner.nextLine();

        System.out.println("Ho va ten ban vua nhap la: " + fullName);
        System.out.println();
    }

    /**
     * Question 4: Cho phep nguoi dung nhap ngay sinh nhat
     */
    public void question4() {
        System.out.println("===== Question 4 =====");
        LocalDate birthday = inputDate("Moi ban nhap ngay sinh nhat (dd/MM/yyyy): ");

        System.out.println("Ngay sinh nhat cua ban la: "
                + birthday.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println();
    }

    /**
     * Question 5: Cho phep nguoi dung tao account
     * Voi Position, nguoi dung nhap 1 2 3 4 -> chuong trinh chuyen thanh enum
     */
    public Account question5() {
        System.out.println("===== Question 5: Tao Account =====");

        System.out.print("Nhap email: ");
        String email = scanner.nextLine();

        System.out.print("Nhap username: ");
        String username = scanner.nextLine();

        System.out.print("Nhap full name: ");
        String fullName = scanner.nextLine();

        // Chon phong ban tu danh sach co san
        System.out.println("Danh sach phong ban:");
        for (Department department : DataStorage.departments) {
            System.out.println("\t" + department.getDepartmentId() + ". " + department.getDepartmentName());
        }
        System.out.print("Nhap id phong ban: ");
        int departmentId = inputInt();
        Department department = findDepartmentById(departmentId);

        // Chon position, chuyen so thanh enum
        Position position = inputPosition();

        // Tao account moi, id = so luong account hien tai + 1
        int newAccountId = DataStorage.accounts.size() + 1;
        Account newAccount = new Account(newAccountId, email, username, fullName,
                department, position, LocalDate.now());
        DataStorage.accounts.add(newAccount);

        System.out.println("Tao account thanh cong: " + newAccount);
        System.out.println();
        return newAccount;
    }

    /**
     * Question 6: Cho phep nguoi dung tao department
     */
    public Department question6() {
        System.out.println("===== Question 6: Tao Department =====");

        System.out.print("Nhap ten phong ban: ");
        String departmentName = scanner.nextLine();

        int newDepartmentId = DataStorage.departments.size() + 1;
        Department newDepartment = new Department(newDepartmentId, departmentName);
        DataStorage.departments.add(newDepartment);

        System.out.println("Tao department thanh cong: " + newDepartment);
        System.out.println();
        return newDepartment;
    }

    /**
     * Question 7: Nhap so chan tu console
     * Neu nhap so le thi yeu cau nhap lai cho den khi dung
     */
    public void question7() {
        System.out.println("===== Question 7 =====");
        int number;

        while (true) {
            System.out.print("Moi ban nhap vao 1 so chan: ");
            number = inputInt();

            if (number % 2 == 0) {
                break;
            }
            System.out.println("So ban nhap la so le. Moi ban nhap lai!");
        }

        System.out.println("So chan ban vua nhap la: " + number);
        System.out.println();
    }

    /**
     * Question 8 + 10 + 11: Menu chuc nang chinh cua chuong trinh
     *
     * Buoc 1: In ra menu chuc nang
     * Buoc 2: Thuc hien chuc nang tuong ung voi so nguoi dung nhap
     *         1 -> tao account, 2 -> tao department,
     *         3 -> them group vao account, 4 -> them account vao group ngau nhien
     * Buoc 3: Hoi nguoi dung co muon tiep tuc khong
     */
    public void question8() {
        System.out.println("===== Question 8 + 10 + 11: Menu chuc nang =====");

        while (true) {
            // --- Buoc 1: in menu ---
            System.out.println("Moi ban nhap vao chuc nang muon su dung:");
            System.out.println("\t1. Tao account");
            System.out.println("\t2. Tao department");
            System.out.println("\t3. Them group vao account");
            System.out.println("\t4. Them account vao 1 group ngau nhien");
            System.out.print("Lua chon cua ban: ");
            int choice = inputInt();

            // --- Buoc 2: thuc hien chuc nang ---
            switch (choice) {
                case 1:
                    question5();
                    break;
                case 2:
                    question6();
                    break;
                case 3:
                    question9();
                    break;
                case 4:
                    question11();
                    break;
                default:
                    System.out.println("Moi ban nhap lai!\n");
                    continue; // quay lai Buoc 1, khong hoi tiep tuc
            }

            // --- Buoc 3: hoi co muon lam tiep khong ---
            System.out.print("Ban co muon thuc hien chuc nang khac khong? (c/k): ");
            String answer = scanner.nextLine().trim();

            if (!answer.equalsIgnoreCase("c")) {
                System.out.println("Ket thuc chuong trinh. Cam on ban!");
                return; // dung lenh return de ket thuc chuong trinh
            }
            System.out.println();
        }
    }

    /**
     * Question 9: Them group vao account
     *
     * Buoc 1: In ra danh sach username
     * Buoc 2: Nguoi dung nhap username
     * Buoc 3: In ra danh sach ten group
     * Buoc 4: Nguoi dung nhap ten group
     * Buoc 5: Them account vao group da chon
     */
    public void question9() {
        System.out.println("===== Question 9: Them group vao account =====");

        // --- Buoc 1: in danh sach username ---
        System.out.println("Danh sach username:");
        for (Account account : DataStorage.accounts) {
            System.out.println("\t- " + account.getUsername());
        }

        // --- Buoc 2: nhap username ---
        System.out.print("Nhap username cua account: ");
        String username = scanner.nextLine().trim();
        Account selectedAccount = findAccountByUsername(username);

        if (selectedAccount == null) {
            System.out.println("Khong tim thay account co username nay!\n");
            return;
        }

        // --- Buoc 3: in danh sach group ---
        System.out.println("Danh sach group:");
        for (Group group : DataStorage.groups) {
            System.out.println("\t- " + group.getGroupName());
        }

        // --- Buoc 4: nhap ten group ---
        System.out.print("Nhap ten group: ");
        String groupName = scanner.nextLine().trim();
        Group selectedGroup = findGroupByName(groupName);

        if (selectedGroup == null) {
            System.out.println("Khong tim thay group co ten nay!\n");
            return;
        }

        // --- Buoc 5: them account vao group ---
        addAccountToGroup(selectedAccount, selectedGroup);
        System.out.println();
    }

    /**
     * Question 11: Them account vao 1 group ngau nhien
     *
     * Buoc 1: In ra danh sach username
     * Buoc 2: Nguoi dung nhap username
     * Buoc 3: Chuong trinh chon ngau nhien 1 group
     * Buoc 4: Them account vao group vua chon
     */
    public void question11() {
        System.out.println("===== Question 11: Them account vao group ngau nhien =====");

        // --- Buoc 1: in danh sach username ---
        System.out.println("Danh sach username:");
        for (Account account : DataStorage.accounts) {
            System.out.println("\t- " + account.getUsername());
        }

        // --- Buoc 2: nhap username ---
        System.out.print("Nhap username cua account: ");
        String username = scanner.nextLine().trim();
        Account selectedAccount = findAccountByUsername(username);

        if (selectedAccount == null) {
            System.out.println("Khong tim thay account co username nay!\n");
            return;
        }

        // --- Buoc 3: chon ngau nhien 1 group ---
        int randomIndex = random.nextInt(DataStorage.groups.size());
        Group randomGroup = DataStorage.groups.get(randomIndex);
        System.out.println("Chuong trinh da chon ngau nhien group: " + randomGroup.getGroupName());

        // --- Buoc 4: them account vao group ---
        addAccountToGroup(selectedAccount, randomGroup);
        System.out.println();
    }

    // ==================================================================
    // Cac method ho tro
    // ==================================================================

    /**
     * Doc 1 so nguyen tu console, neu nhap sai kieu thi yeu cau nhap lai
     */
    private int inputInt() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Ban phai nhap vao 1 so nguyen. Moi nhap lai: ");
            }
        }
    }

    /**
     * Doc 1 ngay tu console theo dinh dang dd/MM/yyyy
     */
    private LocalDate inputDate(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print(message);

        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.print("Sai dinh dang. Moi ban nhap lai (dd/MM/yyyy): ");
            }
        }
    }

    /**
     * Cho nguoi dung chon Position bang cach nhap so, sau do chuyen thanh enum
     */
    private Position inputPosition() {
        System.out.println("Danh sach chuc vu:");
        System.out.println("\t1. Dev");
        System.out.println("\t2. Test");
        System.out.println("\t3. Scrum Master");
        System.out.println("\t4. PM");

        while (true) {
            System.out.print("Nhap so tuong ung voi chuc vu: ");
            int choice = inputInt();

            switch (choice) {
                case 1:
                    return Position.DEV;
                case 2:
                    return Position.TEST;
                case 3:
                    return Position.SCRUM_MASTER;
                case 4:
                    return Position.PM;
                default:
                    System.out.println("Lua chon khong hop le. Moi ban nhap lai!");
            }
        }
    }

    /**
     * Them account vao group, co kiem tra truong hop da tham gia roi
     */
    private void addAccountToGroup(Account account, Group group) {
        // Kiem tra account da co trong group chua, tranh them trung
        for (GroupAccount groupAccount : DataStorage.groupAccounts) {
            if (groupAccount.getAccount().getAccountId() == account.getAccountId()
                    && groupAccount.getGroup().getGroupId() == group.getGroupId()) {
                System.out.println("Account " + account.getUsername()
                        + " da co trong group " + group.getGroupName() + " roi!");
                return;
            }
        }

        DataStorage.groupAccounts.add(new GroupAccount(group, account, LocalDate.now()));
        System.out.println("Da them account " + account.getUsername()
                + " vao group " + group.getGroupName() + " thanh cong!");

        // In lai danh sach group hien tai cua account de kiem tra
        List<Group> groupsOfAccount = DataStorage.getGroupsOfAccount(account);
        System.out.print("Cac group hien tai cua " + account.getUsername() + ": ");
        for (int i = 0; i < groupsOfAccount.size(); i++) {
            System.out.print(groupsOfAccount.get(i).getGroupName());
            if (i < groupsOfAccount.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    /**
     * Tim department theo id
     */
    private Department findDepartmentById(int departmentId) {
        for (Department department : DataStorage.departments) {
            if (department.getDepartmentId() == departmentId) {
                return department;
            }
        }
        return null;
    }

    /**
     * Tim account theo username (khong phan biet hoa thuong)
     */
    private Account findAccountByUsername(String username) {
        for (Account account : DataStorage.accounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Tim group theo ten (khong phan biet hoa thuong)
     */
    private Group findGroupByName(String groupName) {
        for (Group group : DataStorage.groups) {
            if (group.getGroupName().equalsIgnoreCase(groupName)) {
                return group;
            }
        }
        return null;
    }
}
