package backend.repository;

import entity.Department;

import java.sql.SQLException;
import java.util.List;

public interface IDepartmentRepository {
    List<Department> getListDepartments() throws SQLException;

    List<Department> getDepartmentsByName(String name) throws SQLException;

    Department getDepartmentById(int id) throws SQLException;

    boolean isDepartmentExistsById(int id) throws SQLException;

    boolean isDepartmentExistsByName(String name) throws SQLException;

    boolean createDepartment(String departmentName) throws SQLException;

    boolean deleteDepartmentById(int id) throws SQLException;

    boolean updateDepartmentName(int id, String newName) throws SQLException;
}
