package frontend;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        FunctionAccount functionAccount = new FunctionAccount(sc);
        FunctionDepartment functionDepartment = new FunctionDepartment(sc);

        while (true) {
            System.out.println("\n==== Mời bạn chọn chức năng ====");
            System.out.println("1. Quản lý Account.");
            System.out.println("2. Quản lý Department.");
            System.out.println("3. Thoát khỏi chương trình.");
            System.out.print("Lựa chọn của bạn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    menuAccount(sc, functionAccount);
                    break;

                case "2":
                    menuDepartment(sc, functionDepartment);
                    break;

                case "3":
                    System.out.println("Thoát chương trình.");
                    System.exit(0);

                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }

    private static void menuAccount(Scanner sc, FunctionAccount functionAccount) {
        while (true) {
            System.out.println("\n==== QUẢN LÝ ACCOUNT ====");
            System.out.println("1. Hiển thị toàn bộ account.");
            System.out.println("2. Tìm kiếm account theo username.");
            System.out.println("3. Thêm mới account (Validation đầy đủ).");
            System.out.println("4. Xóa account theo id.");
            System.out.println("5. Update username theo id.");
            System.out.println("6. Quay lại menu chính.");
            System.out.print("Lựa chọn của bạn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    functionAccount.hienThiToanBoAccount();
                    break;

                case "2":
                    functionAccount.timKiemAccountTheoUsername();
                    break;

                case "3":
                    functionAccount.themMoiAccount();
                    break;

                case "4":
                    functionAccount.xoaAccountTheoId();
                    break;

                case "5":
                    functionAccount.updateUsernameTheoId();
                    break;

                case "6":
                    return;

                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }

    private static void menuDepartment(Scanner sc, FunctionDepartment functionDepartment) {
        while (true) {
            System.out.println("\n==== QUẢN LÝ DEPARTMENT ====");
            System.out.println("1. Hiển thị department.");
            System.out.println("2. Tìm kiếm department theo tên.");
            System.out.println("3. Thêm mới department.");
            System.out.println("4. Xóa department theo id.");
            System.out.println("5. Update tên phòng ban theo id.");
            System.out.println("6. Quay lại menu chính.");
            System.out.print("Lựa chọn của bạn: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    functionDepartment.hienThiDepartment();
                    break;

                case "2":
                    functionDepartment.timKiemDepartmentTheoTen();
                    break;

                case "3":
                    functionDepartment.themMoiDepartment();
                    break;

                case "4":
                    functionDepartment.xoaDepartmentTheoId();
                    break;

                case "5":
                    functionDepartment.updateTenPhongBanTheoId();
                    break;

                case "6":
                    return;

                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }
}
