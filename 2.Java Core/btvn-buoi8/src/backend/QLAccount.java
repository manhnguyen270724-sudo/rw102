package backend;

import entity.Account;
import entity.Department;
import entity.Position;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLAccount implements IQLAccount {

    private Scanner sc;

    public QLAccount() {
        this.sc = new Scanner(System.in);
    }

    public QLAccount(Scanner sc) {
        this.sc = sc;
    }

    // Helper: in bảng danh sách Account
    private void inBangAccount(List<Account> list) {
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
    @Override
    public void hienThiToanBoAccount() {
        System.out.println("==== HIỂN THỊ TOÀN BỘ ACCOUNT ====");
        List<Account> list = new ArrayList<>();
        String sql = "SELECT a.AccountID, a.Email, a.Username, a.FullName, a.CreateDate, " +
                "       d.DepartmentID, d.DepartmentName, " +
                "       p.PositionID, p.PositionName " +
                "FROM `Account` a " +
                "LEFT JOIN Department d ON a.DepartmentID = d.DepartmentID " +
                "LEFT JOIN `Position` p ON a.PositionID = p.PositionID " +
                "ORDER BY a.AccountID ASC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Department dept = null;
                int dId = rs.getInt("DepartmentID");
                if (!rs.wasNull()) {
                    dept = new Department(dId, rs.getString("DepartmentName"));
                }

                Position pos = null;
                int pId = rs.getInt("PositionID");
                if (!rs.wasNull()) {
                    pos = new Position(pId, rs.getString("PositionName"));
                }

                Date sqlDate = rs.getDate("CreateDate");
                LocalDate cDate = sqlDate != null ? sqlDate.toLocalDate() : null;

                Account acc = new Account(
                        rs.getInt("AccountID"),
                        rs.getString("Email"),
                        rs.getString("Username"),
                        rs.getString("FullName"),
                        dept,
                        pos,
                        cDate
                );
                list.add(acc);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn: " + e.getMessage());
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        inBangAccount(list);
    }

    // 2. Tìm kiếm account theo username
    @Override
    public void timKiemAccountTheoUsername() {
        System.out.println("==== TÌM KIẾM ACCOUNT THEO USERNAME ====");
        System.out.print("Nhập username cần tìm: ");
        String username = sc.nextLine().trim();

        List<Account> list = new ArrayList<>();
        String sql = "SELECT a.AccountID, a.Email, a.Username, a.FullName, a.CreateDate, " +
                "       d.DepartmentID, d.DepartmentName, " +
                "       p.PositionID, p.PositionName " +
                "FROM `Account` a " +
                "LEFT JOIN Department d ON a.DepartmentID = d.DepartmentID " +
                "LEFT JOIN `Position` p ON a.PositionID = p.PositionID " +
                "WHERE a.Username LIKE ? " +
                "ORDER BY a.AccountID ASC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + username + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Department dept = null;
                int dId = rs.getInt("DepartmentID");
                if (!rs.wasNull()) {
                    dept = new Department(dId, rs.getString("DepartmentName"));
                }

                Position pos = null;
                int pId = rs.getInt("PositionID");
                if (!rs.wasNull()) {
                    pos = new Position(pId, rs.getString("PositionName"));
                }

                Date sqlDate = rs.getDate("CreateDate");
                LocalDate cDate = sqlDate != null ? sqlDate.toLocalDate() : null;

                list.add(new Account(
                        rs.getInt("AccountID"),
                        rs.getString("Email"),
                        rs.getString("Username"),
                        rs.getString("FullName"),
                        dept,
                        pos,
                        cDate
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tìm kiếm: " + e.getMessage());
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        if (list.isEmpty()) {
            System.out.println("Không tìm thấy account nào có username: " + username);
        } else {
            inBangAccount(list);
        }
    }

    // 3. Thêm mới account
    @Override
    public void themMoiAccount() {
        System.out.println("==== THÊM MỚI ACCOUNT ====");
        System.out.print("Nhập username: ");
        String username = sc.nextLine().trim();

        System.out.print("Nhập email: ");
        String email = sc.nextLine().trim();

        System.out.print("Nhập họ và tên: ");
        String fullName = sc.nextLine().trim();

        // Hiển thị phòng ban
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

        // Hiển thị chức vụ
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

        String sql = "INSERT INTO `Account` (Email, Username, FullName, DepartmentID, PositionID, CreateDate) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, fullName);

            if (deptId > 0) {
                ps.setInt(4, deptId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            if (posId > 0) {
                ps.setInt(5, posId);
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setDate(6, Date.valueOf(LocalDate.now()));

            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Thêm mới account thành công!");
            } else {
                System.out.println("Thêm mới account thất bại!");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi thêm account: " + e.getMessage());
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    // 4. Xóa acccount theo username
    @Override
    public void xoaAccountTheoUsername() {
        System.out.println("==== XÓA ACCOUNT THEO USERNAME ====");
        System.out.print("Nhập username cần xóa: ");
        String username = sc.nextLine().trim();

        String sql = "DELETE FROM `Account` WHERE Username = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Xóa account thành công!");
            } else {
                System.out.println("Không tìm thấy account với username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi xóa account: " + e.getMessage());
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    // 5. Update fullname theo username
    @Override
    public void updateFullnameTheoUsername() {
        System.out.println("==== UPDATE FULLNAME THEO USERNAME ====");
        System.out.print("Nhập username cần cập nhật: ");
        String username = sc.nextLine().trim();

        System.out.print("Nhập FullName mới: ");
        String newFullName = sc.nextLine().trim();

        String sql = "UPDATE `Account` SET FullName = ? WHERE Username = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newFullName);
            ps.setString(2, username);
            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.println("Cập nhật FullName thành công!");
            } else {
                System.out.println("Không tìm thấy account với username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật: " + e.getMessage());
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    // Tiện ích hiển thị Department đơn giản để người dùng chọn khi thêm mới Account
    private void hienThiDanhSachDepartmentDonGian() {
        String sql = "SELECT DepartmentID, DepartmentName FROM Department ORDER BY DepartmentID ASC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("+--------+--------------------------------+");
            System.out.printf("|%8s|%32s|\n", "ID", "Tên phòng ban");
            System.out.println("+--------+--------------------------------+");
            while (rs.next()) {
                System.out.printf("|%8d|%32s|\n", rs.getInt("DepartmentID"), rs.getString("DepartmentName"));
            }
            System.out.println("+--------+--------------------------------+");
        } catch (SQLException ignored) {
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }
    }

    // Tiện ích hiển thị Position đơn giản để người dùng chọn khi thêm mới Account
    private void hienThiDanhSachPositionDonGian() {
        String sql = "SELECT PositionID, PositionName FROM `Position` ORDER BY PositionID ASC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            System.out.println("+--------+--------------------------------+");
            System.out.printf("|%8s|%32s|\n", "ID", "Tên chức vụ");
            System.out.println("+--------+--------------------------------+");
            while (rs.next()) {
                System.out.printf("|%8d|%32s|\n", rs.getInt("PositionID"), rs.getString("PositionName"));
            }
            System.out.println("+--------+--------------------------------+");
        } catch (SQLException ignored) {
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }
    }
}
