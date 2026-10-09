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

        // 1. Nhập username: unique, 5 - 50 kí tự
        String username;
        while (true) {
            System.out.print("Nhập username (5 - 50 kí tự): ");
            username = sc.nextLine().trim();
            if (username.length() < 5 || username.length() > 50) {
                System.out.println(">> Lỗi: Username phải có độ dài từ 5 đến 50 kí tự. Vui lòng nhập lại!");
                continue;
            }
            try {
                if (accountController.isAccountExistsByUsername(username)) {
                    System.out.println(">> Lỗi: Username '" + username + "' đã tồn tại trong hệ thống. Vui lòng nhập lại!");
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Lỗi kiểm tra username: " + e.getMessage());
                return;
            }
            break;
        }

        // 2. Nhập email: unique, 5 - 50 kí tự, đúng định dạng email
        String email;
        while (true) {
            System.out.print("Nhập email (5 - 50 kí tự, đúng định dạng): ");
            email = sc.nextLine().trim();
            if (email.length() < 5 || email.length() > 50) {
                System.out.println(">> Lỗi: Email phải có độ dài từ 5 đến 50 kí tự. Vui lòng nhập lại!");
                continue;
            }
            if (!utils.ScannerUtils.isValidEmail(email)) {
                System.out.println(">> Lỗi: Email không đúng định dạng (VD: example@gmail.com). Vui lòng nhập lại!");
                continue;
            }
            try {
                if (accountController.isAccountExistsByEmail(email)) {
                    System.out.println(">> Lỗi: Email '" + email + "' đã tồn tại trong hệ thống. Vui lòng nhập lại!");
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Lỗi kiểm tra email: " + e.getMessage());
                return;
            }
            break;
        }

        // 3. Nhập fullName: 5 - 50 kí tự
        String fullName;
        while (true) {
            System.out.print("Nhập họ và tên (5 - 50 kí tự): ");
            fullName = sc.nextLine().trim();
            if (fullName.length() < 5 || fullName.length() > 50) {
                System.out.println(">> Lỗi: Họ và tên phải có độ dài từ 5 đến 50 kí tự. Vui lòng nhập lại!");
                continue;
            }
            break;
        }

        // 4. Load ra toàn bộ department có trong hệ thống, nhập id tương ứng, sai thì báo lỗi -> nhập lại
        System.out.println("\n--- Danh sách phòng ban có trong hệ thống ---");
        hienThiDanhSachDepartmentDonGian();
        int deptId;
        while (true) {
            System.out.print("Nhập ID phòng ban tương ứng: ");
            deptId = utils.ScannerUtils.inputInt(sc, ">> Lỗi: ID phòng ban phải là số nguyên!");
            try {
                if (!departmentController.isDepartmentExistsById(deptId)) {
                    System.out.println(">> Lỗi: Phòng ban có ID " + deptId + " không tồn tại trong hệ thống. Vui lòng nhập lại!");
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Lỗi kiểm tra phòng ban: " + e.getMessage());
                return;
            }
            break;
        }

        // 5. Load ra toàn bộ position có trong hệ thống, nhập id tương ứng, sai thì báo lỗi -> nhập lại
        System.out.println("\n--- Danh sách chức vụ có trong hệ thống ---");
        hienThiDanhSachPositionDonGian();
        int posId;
        while (true) {
            System.out.print("Nhập ID chức vụ tương ứng: ");
            posId = utils.ScannerUtils.inputInt(sc, ">> Lỗi: ID chức vụ phải là số nguyên!");
            try {
                if (!positionController.isPositionExistsById(posId)) {
                    System.out.println(">> Lỗi: Chức vụ có ID " + posId + " không tồn tại trong hệ thống. Vui lòng nhập lại!");
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Lỗi kiểm tra chức vụ: " + e.getMessage());
                return;
            }
            break;
        }

        Department department = new Department(deptId, null);
        Position position = new Position(posId, null);

        Account newAcc = new Account(email, username, fullName, department, position);

        try {
            boolean success = accountController.createAccount(newAcc);
            if (success) {
                System.out.println("\n===> Thêm mới account thành công!");
            } else {
                System.out.println("\n===> Thêm mới account thất bại!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi thêm account: " + e.getMessage());
        }
    }

    // 4. Xóa account theo id (id: check là số, tồn tại)
    public void xoaAccountTheoId() {
        System.out.println("==== XÓA ACCOUNT THEO ID ====");
        int id;
        while (true) {
            System.out.print("Nhập vào ID cần xóa: ");
            id = utils.ScannerUtils.inputInt(sc, ">> Lỗi: ID phải là số nguyên!");
            try {
                if (!accountController.isAccountExistsById(id)) {
                    System.out.println(">> Lỗi: Không tồn tại account có ID " + id + " trong DB. Vui lòng nhập lại!");
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Lỗi kiểm tra account: " + e.getMessage());
                return;
            }
            break;
        }

        try {
            boolean success = accountController.deleteAccountById(id);
            if (success) {
                System.out.println("\n===> Xóa account có ID " + id + " thành công!");
            } else {
                System.out.println("\n===> Xóa account thất bại!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi xóa account: " + e.getMessage());
        }
    }

    // 5. Update username theo id (id: check là số, tồn tại; username: phải chưa tồn tại trong DB, 5 - 50 kí tự)
    public void updateUsernameTheoId() {
        System.out.println("==== UPDATE USERNAME THEO ID ====");

        // 1. Nhập ID: check là số, tồn tại
        int id;
        while (true) {
            System.out.print("Nhập vào ID account cần cập nhật: ");
            id = utils.ScannerUtils.inputInt(sc, ">> Lỗi: ID phải là số nguyên!");
            try {
                if (!accountController.isAccountExistsById(id)) {
                    System.out.println(">> Lỗi: Không tồn tại account có ID " + id + " trong DB. Vui lòng nhập lại!");
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Lỗi kiểm tra account: " + e.getMessage());
                return;
            }
            break;
        }

        // 2. Nhập username: phải chưa tồn tại trong DB, 5 - 50 kí tự
        String newUsername;
        while (true) {
            System.out.print("Nhập username mới (5 - 50 kí tự): ");
            newUsername = sc.nextLine().trim();
            if (newUsername.length() < 5 || newUsername.length() > 50) {
                System.out.println(">> Lỗi: Username phải có độ dài từ 5 đến 50 kí tự. Vui lòng nhập lại!");
                continue;
            }
            try {
                if (accountController.isAccountExistsByUsername(newUsername)) {
                    System.out.println(">> Lỗi: Username '" + newUsername + "' đã tồn tại trong DB. Vui lòng nhập lại!");
                    continue;
                }
            } catch (Exception e) {
                System.err.println("Lỗi kiểm tra username: " + e.getMessage());
                return;
            }
            break;
        }

        try {
            boolean success = accountController.updateUsernameById(id, newUsername);
            if (success) {
                System.out.println("\n===> Cập nhật username thành công cho account ID " + id + "!");
            } else {
                System.out.println("\n===> Cập nhật username thất bại!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi cập nhật username: " + e.getMessage());
        }
    }

    // Giữ phương thức cũ để tương thích
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

    // Giữ phương thức cũ để tương thích
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
