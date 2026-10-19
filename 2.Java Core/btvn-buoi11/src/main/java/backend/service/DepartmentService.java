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

    @Override
    public int importDepartmentsFromCSV(String filePath) throws Exception {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn file CSV không được để trống!");
        }

        java.io.File file = new java.io.File(filePath.trim());
        if (!file.exists() || !file.isFile()) {
            throw new java.io.FileNotFoundException("Không tìm thấy file CSV tại đường dẫn: " + filePath);
        }

        List<String> errors = new java.util.ArrayList<>();
        List<Department> departmentsToImport = new java.util.ArrayList<>();
        java.util.Set<String> csvDeptNames = new java.util.HashSet<>();

        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(new java.io.FileInputStream(file), java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                // Bỏ qua header
                if (lineNumber == 1 && line.toLowerCase().contains("department")) {
                    continue;
                }

                String[] parts = line.split(",");
                String deptName = parts[0].trim().replace("\"", "");

                if (deptName.isEmpty()) {
                    errors.add("Dòng " + lineNumber + ": Tên phòng ban không được để trống!");
                    continue;
                }

                if (csvDeptNames.contains(deptName.toLowerCase())) {
                    errors.add("Dòng " + lineNumber + ": Phòng ban '" + deptName + "' bị trùng lặp trong file CSV!");
                    continue;
                }

                if (departmentRepository.isDepartmentExistsByName(deptName)) {
                    errors.add("Dòng " + lineNumber + ": Phòng ban '" + deptName + "' đã tồn tại trong CSDL!");
                    continue;
                }

                csvDeptNames.add(deptName.toLowerCase());
                departmentsToImport.add(new Department(deptName));
            }
        }

        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Tìm thấy ").append(errors.size()).append(" lỗi trong file CSV phòng ban:\n");
            for (String err : errors) {
                sb.append("  - ").append(err).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }

        if (departmentsToImport.isEmpty()) {
            throw new IllegalArgumentException("File CSV không có bản ghi phòng ban nào để import!");
        }

        int[] results = departmentRepository.importDepartments(departmentsToImport);
        return results.length;
    }
}
