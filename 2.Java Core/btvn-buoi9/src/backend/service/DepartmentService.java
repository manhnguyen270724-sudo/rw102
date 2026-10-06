package backend.service;

import backend.repository.DepartmentRepository;
import backend.repository.IDepartmentRepository;
import entity.Department;

import java.sql.SQLException;
import java.util.List;

public class DepartmentService implements IDepartmentService {

    private final IDepartmentRepository departmentRepository;

    public DepartmentService() {
        this.departmentRepository = new DepartmentRepository();
    }

    public DepartmentService(IDepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getListDepartments() throws SQLException {
        return departmentRepository.getListDepartments();
    }

    @Override
    public List<Department> getDepartmentsByName(String name) throws SQLException {
        if (name == null) {
            name = "";
        }
        return departmentRepository.getDepartmentsByName(name);
    }

    @Override
    public Department getDepartmentById(int id) throws SQLException {
        return departmentRepository.getDepartmentById(id);
    }

    @Override
    public boolean isDepartmentExistsById(int id) throws SQLException {
        return departmentRepository.isDepartmentExistsById(id);
    }

    @Override
    public boolean isDepartmentExistsByName(String name) throws SQLException {
        return departmentRepository.isDepartmentExistsByName(name);
    }

    @Override
    public boolean createDepartment(String departmentName) throws Exception {
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phòng ban không được để trống!");
        }
        if (departmentRepository.isDepartmentExistsByName(departmentName.trim())) {
            throw new IllegalArgumentException("Phòng ban '" + departmentName + "' đã tồn tại trong hệ thống!");
        }
        return departmentRepository.createDepartment(departmentName.trim());
    }

    @Override
    public boolean deleteDepartmentById(int id) throws Exception {
        if (!departmentRepository.isDepartmentExistsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy phòng ban có ID: " + id);
        }
        return departmentRepository.deleteDepartmentById(id);
    }

    @Override
    public boolean updateDepartmentName(int id, String newName) throws Exception {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên phòng ban không được để trống!");
        }
        if (!departmentRepository.isDepartmentExistsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy phòng ban có ID: " + id);
        }
        return departmentRepository.updateDepartmentName(id, newName.trim());
    }
}
