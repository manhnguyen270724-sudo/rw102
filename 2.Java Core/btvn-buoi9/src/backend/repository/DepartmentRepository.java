package backend.repository;

import entity.Department;
import utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository implements IDepartmentRepository {

    @Override
    public List<Department> getListDepartments() throws SQLException {
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
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return list;
    }

    @Override
    public List<Department> getDepartmentsByName(String name) throws SQLException {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT DepartmentID, DepartmentName FROM Department WHERE DepartmentName LIKE ? ORDER BY DepartmentID ASC";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Department(rs.getInt("DepartmentID"), rs.getString("DepartmentName")));
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return list;
    }

    @Override
    public Department getDepartmentById(int id) throws SQLException {
        String sql = "SELECT DepartmentID, DepartmentName FROM Department WHERE DepartmentID = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Department(rs.getInt("DepartmentID"), rs.getString("DepartmentName"));
            }
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }

        return null;
    }

    @Override
    public boolean isDepartmentExistsById(int id) throws SQLException {
        String sql = "SELECT 1 FROM Department WHERE DepartmentID = ?";

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
    public boolean isDepartmentExistsByName(String name) throws SQLException {
        String sql = "SELECT 1 FROM Department WHERE DepartmentName = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            DatabaseUtils.close(rs, ps, conn);
        }
    }

    @Override
    public boolean createDepartment(String departmentName) throws SQLException {
        String sql = "INSERT INTO Department (DepartmentName) VALUES (?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, departmentName);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }

    @Override
    public boolean deleteDepartmentById(int id) throws SQLException {
        String sql = "DELETE FROM Department WHERE DepartmentID = ?";

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
    public boolean updateDepartmentName(int id, String newName) throws SQLException {
        String sql = "UPDATE Department SET DepartmentName = ? WHERE DepartmentID = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DatabaseUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } finally {
            DatabaseUtils.close(ps, conn);
        }
    }
}
