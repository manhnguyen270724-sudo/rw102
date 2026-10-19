package backend.controller;

import backend.service.DepartmentService;
import backend.service.IDepartmentService;
import entity.Department;

import java.util.List;

public class DepartmentController implements IDepartmentController {

    private final IDepartmentService departmentService;

    public DepartmentController() {
        this.departmentService = new DepartmentService();
    }

    public DepartmentController(IDepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public List<Department> getListDepartments() throws Exception {
        return departmentService.getListDepartments();
    }

    @Override
    public List<Department> getDepartmentsByName(String name) throws Exception {
        return departmentService.getDepartmentsByName(name);
    }

    @Override
    public Department getDepartmentById(int id) throws Exception {
        return departmentService.getDepartmentById(id);
    }

    @Override
    public boolean isDepartmentExistsById(int id) throws Exception {
        return departmentService.isDepartmentExistsById(id);
    }

    @Override
    public boolean createDepartment(String departmentName) throws Exception {
        return departmentService.createDepartment(departmentName);
    }

    @Override
    public boolean deleteDepartmentById(int id) throws Exception {
        return departmentService.deleteDepartmentById(id);
    }

    @Override
    public boolean updateDepartmentName(int id, String newName) throws Exception {
        return departmentService.updateDepartmentName(id, newName);
    }

    @Override
    public int importDepartmentsFromCSV(String filePath) throws Exception {
        return departmentService.importDepartmentsFromCSV(filePath);
    }
}
