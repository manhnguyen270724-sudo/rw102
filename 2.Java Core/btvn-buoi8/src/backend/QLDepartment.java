package backend;

import entity.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLDepartment implements IQLDepartment {

    private Scanner sc;

    public QLDepartment() {
        this.sc = new Scanner(System.in);
    }

    public QLDepartment(Scanner sc) {
        this.sc = sc;
    }

    // Helper: in bảng Department
    private void inBangDepartment(List<Department> list) {
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
    @Override
    public void hienThiDepartment() {
        System.out.println("==== HIỂN THỊ TOÀN BỘ PHÒNG BAN ====");
        List<Department> list = new ArrayList<>();
        String sql = "SELECT DepartmentID, DepartmentName FROM Department ORDER BY DepartmentID ASC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Department(rs.getInt("DepartmentID"), rs.getString("DepartmentName")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        inBangDepartment(list);
    }

    // 2. Tìm kiếm department theo tên
    @Override
    public void timKiemDepartmentTheoTen() {
        System.out.println("==== TÌM KIẾM PHÒNG BAN THEO TÊN ====");
        System.out.print("Nhập tên phòng ban cần tìm: ");
        String ten = sc.nextLine().trim();

        List<Department> list = new ArrayList<>();
        String sql = "SELECT DepartmentID, DepartmentName FROM Department WHERE DepartmentName LIKE ? ORDER BY DepartmentID ASC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + ten + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Department(rs.getInt("DepartmentID"), rs.getString("DepartmentName")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm: " + e.getMessage());
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        if (list.isEmpty()) {
            System.out.println("Không tìm thấy phòng ban nào khớp với: " + ten);
        } else {
            inBangDepartment(list);
        }
    }

    // 3. Thêm mới department
    @Override
    public void themMoiDepartment() {
        System.out.println("==== THÊM MỚI PHÒNG BAN ====");
        System.out.print("Nhập tên phòng ban: ");
        String ten = sc.nextLine().trim();

        if (ten.isEmpty()) {
            System.out.println("Tên phòng ban không được để trống!");
            return;
        }

        String sql = "INSERT INTO Department (DepartmentName) VALUES (?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, ten);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Thêm mới phòng ban thành công!");
            } else {
                System.out.println("Thêm mới phòng ban thất bại!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi thêm phòng ban: " + e.getMessage());
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    // 4. Xóa department theo id
    @Override
    public void xoaDepartmentTheoId() {
        System.out.println("==== XÓA PHÒNG BAN THEO ID ====");
        System.out.print("Nhập ID phòng ban cần xóa: ");
        int id;
        try {
            id = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("ID phải là số nguyên!");
            sc.nextLine();
            return;
        }

        String sql = "DELETE FROM Department WHERE DepartmentID = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Xóa phòng ban thành công!");
            } else {
                System.out.println("Không tìm thấy phòng ban có ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi xóa phòng ban (có thể do đang có nhân viên thuộc phòng ban này): " + e.getMessage());
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    // 5. Update tên phòng ban theo id
    @Override
    public void updateTenPhongBanTheoId() {
        System.out.println("==== CẬP NHẬT TÊN PHÒNG BAN THEO ID ====");
        System.out.print("Nhập ID phòng ban cần cập nhật: ");
        int id;
        try {
            id = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("ID phải là số nguyên!");
            sc.nextLine();
            return;
        }

        System.out.print("Nhập tên mới của phòng ban: ");
        String tenMoi = sc.nextLine().trim();
        if (tenMoi.isEmpty()) {
            System.out.println("Tên phòng ban không được để trống!");
            return;
        }

        String sql = "UPDATE Department SET DepartmentName = ? WHERE DepartmentID = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, tenMoi);
            ps.setInt(2, id);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Cập nhật tên phòng ban thành công!");
            } else {
                System.out.println("Không tìm thấy phòng ban có ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật phòng ban: " + e.getMessage());
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }
}
