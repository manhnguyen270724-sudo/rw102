package backend.controller;

import entity.Department;

import java.util.List;

public interface IDepartmentController {
    List<Department> getListDepartments() throws Exception;

    List<Department> getDepartmentsByName(String name) throws Exception;

    Department getDepartmentById(int id) throws Exception;

    boolean isDepartmentExistsById(int id) throws Exception;

    boolean createDepartment(String departmentName) throws Exception;

    boolean deleteDepartmentById(int id) throws Exception;

    boolean updateDepartmentName(int id, String newName) throws Exception;

    int importDepartmentsFromCSV(String filePath) throws Exception;
}
