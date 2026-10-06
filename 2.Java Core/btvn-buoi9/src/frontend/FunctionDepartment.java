package frontend;

import backend.controller.DepartmentController;
import backend.controller.IDepartmentController;
import entity.Department;
import utils.ScannerUtils;

import java.util.List;
import java.util.Scanner;

public class FunctionDepartment {

    private final Scanner sc;
    private final IDepartmentController departmentController;

    public FunctionDepartment(Scanner sc) {
        this.sc = sc;
        this.departmentController = new DepartmentController();
    }

    public FunctionDepartment(Scanner sc, IDepartmentController departmentController) {
        this.sc = sc;
        this.departmentController = departmentController;
    }

    // Helper: in bảng Department
    public void inBangDepartment(List<Department> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Không có phòng ban nào.");
            return;
        }

        System.out.println("+--------+------------------------------------------+");
        System.out.printf("|%8s|%42s|\n", "ID", "Tên phòng ban");
        System.out.println("+--------+------------------------------------------+");

        for (Department dept : list) {
            System.out.printf("|%8d|%42s|\n", dept.getDepartmentId(), dept.getDepartmentName());
        }

        System.out.println("+--------+------------------------------------------+");
    }

    // 1. Hiển thị department
    public void hienThiDepartment() {
        System.out.println("==== HIỂN THỊ TOÀN BỘ PHÒNG BAN ====");
        try {
            List<Department> list = departmentController.getListDepartments();
            inBangDepartment(list);
        } catch (Exception e) {
            System.err.println("Lỗi hiển thị phòng ban: " + e.getMessage());
        }
    }

    // 2. Tìm kiếm department theo tên
    public void timKiemDepartmentTheoTen() {
        System.out.println("==== TÌM KIẾM PHÒNG BAN THEO TÊN ====");
        System.out.print("Nhập tên phòng ban cần tìm: ");
        String ten = sc.nextLine().trim();

        try {
            List<Department> list = departmentController.getDepartmentsByName(ten);
            if (list.isEmpty()) {
                System.out.println("Không tìm thấy phòng ban nào khớp với: " + ten);
            } else {
                inBangDepartment(list);
            }
        } catch (Exception e) {
            System.err.println("Lỗi tìm kiếm: " + e.getMessage());
        }
    }

    // 3. Thêm mới department
    public void themMoiDepartment() {
        System.out.println("==== THÊM MỚI PHÒNG BAN ====");
        System.out.print("Nhập tên phòng ban: ");
        String ten = sc.nextLine().trim();

        try {
            boolean success = departmentController.createDepartment(ten);
            if (success) {
                System.out.println("Thêm mới phòng ban thành công!");
            } else {
                System.out.println("Thêm mới phòng ban thất bại!");
            }
        } catch (Exception e) {
            System.err.println("Lỗi thêm phòng ban: " + e.getMessage());
        }
    }

    // 4. Xóa department theo id
    public void xoaDepartmentTheoId() {
        System.out.println("==== XÓA PHÒNG BAN THEO ID ====");
        System.out.print("Nhập ID phòng ban cần xóa: ");
        int id;
        try {
            id = ScannerUtils.inputInt(sc, "ID phải là số nguyên!");
        } catch (Exception e) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        try {
            boolean success = departmentController.deleteDepartmentById(id);
            if (success) {
                System.out.println("Xóa phòng ban thành công!");
            } else {
                System.out.println("Không tìm thấy phòng ban có ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Lỗi xóa phòng ban (có thể do đang có nhân viên thuộc phòng ban này): " + e.getMessage());
        }
    }

    // 5. Update tên phòng ban theo id
    public void updateTenPhongBanTheoId() {
        System.out.println("==== CẬP NHẬT TÊN PHÒNG BAN THEO ID ====");
        System.out.print("Nhập ID phòng ban cần cập nhật: ");
        int id;
        try {
            id = ScannerUtils.inputInt(sc, "ID phải là số nguyên!");
        } catch (Exception e) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        System.out.print("Nhập tên mới của phòng ban: ");
        String tenMoi = sc.nextLine().trim();

        try {
            boolean success = departmentController.updateDepartmentName(id, tenMoi);
            if (success) {
                System.out.println("Cập nhật tên phòng ban thành công!");
            } else {
                System.out.println("Không tìm thấy phòng ban có ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Lỗi cập nhật phòng ban: " + e.getMessage());
        }
    }
}
