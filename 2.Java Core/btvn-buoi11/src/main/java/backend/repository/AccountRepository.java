package backend.repository;

import entity.Account;
import entity.Department;
import entity.Position;
import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository implements IAccountRepository {

    private Account mapRowToAccount(ResultSet rs) throws SQLException {
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

        return new Account(
                rs.getInt("AccountID"),
                rs.getString("Email"),
                rs.getString("Username"),
                rs.getString("FullName"),
                dept,
                pos,
                cDate
        );
    }

    @Override
    public List<Account> getListAccounts() throws SQLException {
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
                list.add(mapRowToAccount(rs));
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return list;
    }

    @Override
    public List<Account> getAccountsByUsername(String username) throws SQLException {
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
                list.add(mapRowToAccount(rs));
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return list;
    }

    @Override
    public Account getAccountByUsername(String username) throws SQLException {
        String sql = "SELECT a.AccountID, a.Email, a.Username, a.FullName, a.CreateDate, " +
                "       d.DepartmentID, d.DepartmentName, " +
                "       p.PositionID, p.PositionName " +
                "FROM `Account` a " +
                "LEFT JOIN Department d ON a.DepartmentID = d.DepartmentID " +
                "LEFT JOIN `Position` p ON a.PositionID = p.PositionID " +
                "WHERE a.Username = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return null;
    }

    @Override
    public boolean isAccountExistsByUsername(String username) throws SQLException {
        String sql = "SELECT 1 FROM `Account` WHERE Username = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }
    }

    @Override
    public boolean isAccountExistsByEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM `Account` WHERE Email = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }
    }

    @Override
    public boolean createAccount(Account account) throws SQLException {
        String sql = "INSERT INTO `Account` (Email, Username, FullName, DepartmentID, PositionID, CreateDate) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getFullName());

            if (account.getDepartment() != null && account.getDepartment().getDepartmentId() > 0) {
                ps.setInt(4, account.getDepartment().getDepartmentId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            if (account.getPosition() != null && account.getPosition().getPositionId() > 0) {
                ps.setInt(5, account.getPosition().getPositionId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            LocalDate createDate = account.getCreateDate() != null ? account.getCreateDate() : LocalDate.now();
            ps.setDate(6, Date.valueOf(createDate));

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    @Override
    public boolean deleteAccountByUsername(String username) throws SQLException {
        String sql = "DELETE FROM `Account` WHERE Username = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    @Override
    public boolean updateFullnameByUsername(String username, String newFullName) throws SQLException {
        String sql = "UPDATE `Account` SET FullName = ? WHERE Username = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newFullName);
            ps.setString(2, username);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    @Override
    public Account getAccountById(int id) throws SQLException {
        String sql = "SELECT a.AccountID, a.Email, a.Username, a.FullName, a.CreateDate, " +
                "       d.DepartmentID, d.DepartmentName, " +
                "       p.PositionID, p.PositionName " +
                "FROM `Account` a " +
                "LEFT JOIN Department d ON a.DepartmentID = d.DepartmentID " +
                "LEFT JOIN `Position` p ON a.PositionID = p.PositionID " +
                "WHERE a.AccountID = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return null;
    }

    @Override
    public boolean isAccountExistsById(int id) throws SQLException {
        String sql = "SELECT 1 FROM `Account` WHERE AccountID = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }
    }

    @Override
    public boolean deleteAccountById(int id) throws SQLException {
        String sql = "DELETE FROM `Account` WHERE AccountID = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    @Override
    public boolean updateUsernameById(int id, String newUsername) throws SQLException {
        String sql = "UPDATE `Account` SET Username = ? WHERE AccountID = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newUsername);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    @Override
    public int[] importAccounts(List<Account> accounts) throws SQLException {
        if (accounts == null || accounts.isEmpty()) {
            return new int[0];
        }

        String sql = "INSERT INTO `Account` (Email, Username, FullName, DepartmentID, PositionID, CreateDate) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            // Bắt đầu transaction
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);

            for (Account account : accounts) {
                ps.setString(1, account.getEmail());
                ps.setString(2, account.getUsername());
                ps.setString(3, account.getFullName());

                if (account.getDepartment() != null && account.getDepartment().getDepartmentId() > 0) {
                    ps.setInt(4, account.getDepartment().getDepartmentId());
                } else {
                    ps.setNull(4, Types.INTEGER);
                }

                if (account.getPosition() != null && account.getPosition().getPositionId() > 0) {
                    ps.setInt(5, account.getPosition().getPositionId());
                } else {
                    ps.setNull(5, Types.INTEGER);
                }

                LocalDate createDate = account.getCreateDate() != null ? account.getCreateDate() : LocalDate.now();
                ps.setDate(6, Date.valueOf(createDate));

                // Thêm vào batch
                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            // Commit transaction khi tất cả các bản ghi được thêm thành công
            conn.commit();
            return results;

        } catch (SQLException e) {
            // Rollback transaction nếu có bất kỳ lỗi nào xảy ra
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Lỗi khi rollback transaction: " + ex.getMessage());
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignored) {
                }
            }
            DatabaseUtils.close(ps, conn);
        }
    }
}
