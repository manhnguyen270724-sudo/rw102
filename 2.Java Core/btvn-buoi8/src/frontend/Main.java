package frontend;

import backend.IQLAccount;
import backend.IQLDepartment;
import backend.QLAccount;
import backend.QLDepartment;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        IQLAccount iqlAccount = new QLAccount(sc);
        IQLDepartment iqlDepartment = new QLDepartment(sc);

        while (true) {
            System.out.println("\n==== Mời bạn chọn chức năng ====");
            System.out.println("1. Quản lý Account.");
            System.out.println("2. Quản lý Department.");
            System.out.println("3. Thoát khỏi chương trình.");
            System.out.print("Lựa chọn của bạn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    menuAccount(sc, iqlAccount);
                    break;

                case "2":
                    menuDepartment(sc, iqlDepartment);
                    break;

                case "3":
                    System.out.println("Thoát.");
                    System.exit(0);

                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }

    private static void menuAccount(Scanner sc, IQLAccount iqlAccount) {
        while (true) {
            System.out.println("\n==== QUẢN LÝ ACCOUNT ====");
            System.out.println("1. Hiển thị toàn bộ account.");
            System.out.println("2. Tìm kiếm account theo username.");
            System.out.println("3. Thêm mới account.");
            System.out.println("4. Xóa acccount theo username.");
            System.out.println("5. Update fullname theo username.");
            System.out.println("6. Quay lại menu chính.");
            System.out.print("Lựa chọn của bạn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    iqlAccount.hienThiToanBoAccount();
                    break;

                case "2":
                    iqlAccount.timKiemAccountTheoUsername();
                    break;

                case "3":
                    iqlAccount.themMoiAccount();
                    break;

                case "4":
                    iqlAccount.xoaAccountTheoUsername();
                    break;

                case "5":
                    iqlAccount.updateFullnameTheoUsername();
                    break;

                case "6":
                    return;

                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }

    private static void menuDepartment(Scanner sc, IQLDepartment iqlDepartment) {
        while (true) {
            System.out.println("\n==== QUẢN LÝ DEPARTMENT ====");
            System.out.println("1. Hiển thị department.");
            System.out.println("2. Tìm kiếm department theo tên.");
            System.out.println("3. Thêm mới department.");
            System.out.println("4. Xóa department theo id.");
            System.out.println("5. Update tên phòng ban theo id.");
            System.out.println("6. Quay lại menu chính.");
            System.out.print("Lựa chọn của bạn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    iqlDepartment.hienThiDepartment();
                    break;

                case "2":
                    iqlDepartment.timKiemDepartmentTheoTen();
                    break;

                case "3":
                    iqlDepartment.themMoiDepartment();
                    break;

                case "4":
                    iqlDepartment.xoaDepartmentTheoId();
                    break;

                case "5":
                    iqlDepartment.updateTenPhongBanTheoId();
                    break;

                case "6":
                    return;

                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }
}
