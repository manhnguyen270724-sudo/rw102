package frontend;

import backend.controller.AccountController;
import backend.controller.DepartmentController;
import backend.controller.IAccountController;
import backend.controller.IDepartmentController;
import backend.controller.IPositionController;
import backend.controller.PositionController;
import entity.Account;
import entity.Department;
import entity.Position;

import java.util.List;
import java.util.Scanner;

public class FunctionAccount {

    private final Scanner sc;
    private final IAccountController accountController;
    private final IDepartmentController departmentController;
    private final IPositionController positionController;

    public FunctionAccount(Scanner sc) {
        this.sc = sc;
        this.accountController = new AccountController();
        this.departmentController = new DepartmentController();
        this.positionController = new PositionController();
    }

    public FunctionAccount(Scanner sc, IAccountController accountController,
                           IDepartmentController departmentController, IPositionController positionController) {
        this.sc = sc;
        this.accountController = accountController;
        this.departmentController = departmentController;
        this.positionController = positionController;
    }

    // Helper: in bảng danh sách Account
    public void inBangAccount(List<Account> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Không có tài khoản nào.");
            return;
        }

        System.out.println("+--------+---------------------------+---------------------+---------------------+----------------------+----------------------+------------+");
        System.out.printf("|%8s|%27s|%21s|%21s|%22s|%22s|%12s|\n",
                "ID", "Email", "Username", "Họ và tên", "Phòng ban", "Chức vụ", "Ngày tạo");
        System.out.println("+--------+---------------------------+---------------------+---------------------+----------------------+----------------------+------------+");

        for (Account a : list) {
            String deptName = (a.getDepartment() != null && a.getDepartment().getDepartmentName() != null)
                    ? a.getDepartment().getDepartmentName() : "-";
            String posName = (a.getPosition() != null && a.getPosition().getPositionName() != null)
                    ? a.getPosition().getPositionName() : "-";
            String dateStr = a.getCreateDate() != null ? a.getCreateDate().toString() : "-";

            System.out.printf("|%8d|%27s|%21s|%21s|%22s|%22s|%12s|\n",
                    a.getAccountId(),
                    a.getEmail(),
                    a.getUsername(),
                    a.getFullName(),
                    deptName,
                    posName,
                    dateStr
            );
        }

        System.out.println("+--------+---------------------------+---------------------+---------------------+----------------------+----------------------+------------+");
    }

    // 1. Hiển thị toàn bộ account
    public void hienThiToanBoAccount() {
        System.out.println("==== HIỂN THỊ TOÀN BỘ ACCOUNT ====");
        try {
            List<Account> list = accountController.getListAccounts();
            inBangAccount(list);
        } catch (Exception e) {
            System.err.println("Lỗi hiển thị account: " + e.getMessage());
        }
    }

    // 2. Tìm kiếm account theo username
    public void timKiemAccountTheoUsername() {
        System.out.println("==== TÌM KIẾM ACCOUNT THEO USERNAME ====");
        System.out.print("Nhập username cần tìm: ");
        String username = sc.nextLine().trim();

        try {
            List<Account> list = accountController.getAccountsByUsername(username);
            if (list.isEmpty()) {
                System.out.println("Không tìm thấy account nào có username: " + username);
            } else {
                inBangAccount(list);
            }
        } catch (Exception e) {
            System.err.println("Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    // 3. Thêm mới account
    public void themMoiAccount() {
        System.out.println("==== THÊM MỚI ACCOUNT ====");
        System.out.print("Nhập username: ");
        String username = sc.nextLine().trim();

        System.out.print("Nhập email: ");
        String email = sc.nextLine().trim();

        System.out.print("Nhập họ và tên: ");
        String fullName = sc.nextLine().trim();

        // Hiển thị danh sách phòng ban để chọn
        System.out.println("--- Danh sách phòng ban ---");
        hienThiDanhSachDepartmentDonGian();
        System.out.print("Nhập ID phòng ban (hoặc 0 nếu không có): ");
        int deptId = 0;
        try {
            deptId = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
        }

        // Hiển thị danh sách chức vụ để chọn
        System.out.println("--- Danh sách chức vụ ---");
        hienThiDanhSachPositionDonGian();
        System.out.print("Nhập ID chức vụ (hoặc 0 nếu không có): ");
        int posId = 0;
        try {
            posId = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            sc.nextLine();
        }

        Department department = (deptId > 0) ? new Department(deptId, null) : null;
        Position position = (posId > 0) ? new Position(posId, null) : null;

        Account newAcc = new Account(email, username, fullName, department, position);

        try {
            boolean success = accountController.createAccount(newAcc);
            if (success) {
                System.out.println("Thêm mới account thành công!");
            } else {
                System.out.println("Thêm mới account thất bại!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi thêm account: " + e.getMessage());
        }
    }

    // 4. Xóa account theo username
    public void xoaAccountTheoUsername() {
        System.out.println("==== XÓA ACCOUNT THEO USERNAME ====");
        System.out.print("Nhập username cần xóa: ");
        String username = sc.nextLine().trim();

        try {
            boolean success = accountController.deleteAccountByUsername(username);
            if (success) {
                System.out.println("Xóa account thành công!");
            } else {
                System.out.println("Không tìm thấy account với username: " + username);
            }
        } catch (Exception e) {
            System.err.println("Lỗi xóa account: " + e.getMessage());
        }
    }

    // 5. Update fullname theo username
    public void updateFullnameTheoUsername() {
        System.out.println("==== UPDATE FULLNAME THEO USERNAME ====");
        System.out.print("Nhập username cần cập nhật: ");
        String username = sc.nextLine().trim();

        System.out.print("Nhập FullName mới: ");
        String newFullName = sc.nextLine().trim();

        try {
            boolean success = accountController.updateFullnameByUsername(username, newFullName);
            if (success) {
                System.out.println("Cập nhật FullName thành công!");
            } else {
                System.out.println("Không tìm thấy account với username: " + username);
            }
        } catch (Exception e) {
            System.err.println("Lỗi cập nhật: " + e.getMessage());
        }
    }

    // Tiện ích hiển thị Department đơn giản để người dùng chọn khi thêm mới Account
    private void hienThiDanhSachDepartmentDonGian() {
        try {
            List<Department> list = departmentController.getListDepartments();
            System.out.println("+--------+--------------------------------+");
            System.out.printf("|%8s|%32s|\n", "ID", "Tên phòng ban");
            System.out.println("+--------+--------------------------------+");
            for (Department d : list) {
                System.out.printf("|%8d|%32s|\n", d.getDepartmentId(), d.getDepartmentName());
            }
            System.out.println("+--------+--------------------------------+");
        } catch (Exception ignored) {
        }
    }

    // Tiện ích hiển thị Position đơn giản để người dùng chọn khi thêm mới Account
    private void hienThiDanhSachPositionDonGian() {
        try {
            List<Position> list = positionController.getListPositions();
            System.out.println("+--------+--------------------------------+");
            System.out.printf("|%8s|%32s|\n", "ID", "Tên chức vụ");
            System.out.println("+--------+--------------------------------+");
            for (Position p : list) {
                System.out.printf("|%8d|%32s|\n", p.getPositionId(), p.getPositionName());
            }
            System.out.println("+--------+--------------------------------+");
        } catch (Exception ignored) {
        }
    }
}
