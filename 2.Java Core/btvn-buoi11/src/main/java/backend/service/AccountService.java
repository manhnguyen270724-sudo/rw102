package backend.service;

import backend.repository.AccountRepository;
import backend.repository.DepartmentRepository;
import backend.repository.IAccountRepository;
import backend.repository.IDepartmentRepository;
import backend.repository.IPositionRepository;
import backend.repository.PositionRepository;
import entity.Account;
import entity.Department;
import entity.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final IDepartmentRepository departmentRepository;
    private final IPositionRepository positionRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
        this.departmentRepository = new DepartmentRepository();
        this.positionRepository = new PositionRepository();
    }

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.departmentRepository = new DepartmentRepository();
        this.positionRepository = new PositionRepository();
    }

    public AccountService(IAccountRepository accountRepository,
                          IDepartmentRepository departmentRepository,
                          IPositionRepository positionRepository) {
        this.accountRepository = accountRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public List<Account> getListAccounts() throws SQLException {
        return accountRepository.getListAccounts();
    }

    @Override
    public List<Account> getAccountsByUsername(String username) throws SQLException {
        if (username == null) {
            username = "";
        }
        return accountRepository.getAccountsByUsername(username);
    }

    @Override
    public Account getAccountByUsername(String username) throws SQLException {
        return accountRepository.getAccountByUsername(username);
    }

    @Override
    public boolean isAccountExistsByUsername(String username) throws SQLException {
        return accountRepository.isAccountExistsByUsername(username);
    }

    @Override
    public boolean isAccountExistsByEmail(String email) throws SQLException {
        return accountRepository.isAccountExistsByEmail(email);
    }

    @Override
    public boolean createAccount(Account account) throws Exception {
        if (account == null) {
            throw new IllegalArgumentException("Thông tin tài khoản không hợp lệ!");
        }
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        String username = account.getUsername().trim();
        if (username.length() < 5 || username.length() > 50) {
            throw new IllegalArgumentException("Username phải có độ dài từ 5 đến 50 kí tự!");
        }
        if (accountRepository.isAccountExistsByUsername(username)) {
            throw new IllegalArgumentException("Username '" + username + "' đã tồn tại trong hệ thống!");
        }

        if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống!");
        }
        String email = account.getEmail().trim();
        if (!utils.ScannerUtils.isValidEmail(email)) {
            throw new IllegalArgumentException("Email không đúng định dạng tiêu chuẩn (vd: abc@vti.com.vn)!");
        }
        if (accountRepository.isAccountExistsByEmail(email)) {
            throw new IllegalArgumentException("Email '" + email + "' đã tồn tại trong hệ thống!");
        }

        if (account.getFullName() == null || account.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ và tên không được để trống!");
        }
        if (account.getFullName().trim().length() < 3 || account.getFullName().trim().length() > 50) {
            throw new IllegalArgumentException("Họ và tên phải có độ dài từ 3 đến 50 kí tự!");
        }

        return accountRepository.createAccount(account);
    }

    @Override
    public boolean deleteAccountByUsername(String username) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        if (!accountRepository.isAccountExistsByUsername(username.trim())) {
            throw new IllegalArgumentException("Không tìm thấy account có username: " + username);
        }
        return accountRepository.deleteAccountByUsername(username.trim());
    }

    @Override
    public boolean updateFullnameByUsername(String username, String newFullName) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        if (!accountRepository.isAccountExistsByUsername(username.trim())) {
            throw new IllegalArgumentException("Không tìm thấy account có username: " + username);
        }
        if (newFullName == null || newFullName.trim().isEmpty()) {
            throw new IllegalArgumentException("FullName mới không được để trống!");
        }
        return accountRepository.updateFullnameByUsername(username.trim(), newFullName.trim());
    }

    @Override
    public Account getAccountById(int id) throws SQLException {
        return accountRepository.getAccountById(id);
    }

    @Override
    public boolean isAccountExistsById(int id) throws SQLException {
        return accountRepository.isAccountExistsById(id);
    }

    @Override
    public boolean deleteAccountById(int id) throws Exception {
        if (!accountRepository.isAccountExistsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy account có ID: " + id);
        }
        return accountRepository.deleteAccountById(id);
    }

    @Override
    public boolean updateUsernameById(int id, String newUsername) throws Exception {
        if (newUsername == null || newUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        String username = newUsername.trim();
        if (username.length() < 5 || username.length() > 50) {
            throw new IllegalArgumentException("Username phải có độ dài từ 5 đến 50 kí tự!");
        }
        if (!accountRepository.isAccountExistsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy account có ID: " + id);
        }
        if (accountRepository.isAccountExistsByUsername(username)) {
            throw new IllegalArgumentException("Username '" + username + "' đã tồn tại trong hệ thống!");
        }
        return accountRepository.updateUsernameById(id, username);
    }

    @Override
    public int importAccountsFromCSV(String filePath) throws Exception {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn file CSV không được để trống!");
        }

        File file = new File(filePath.trim());
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Không tìm thấy file CSV tại đường dẫn: " + filePath);
        }

        List<String> errors = new ArrayList<>();
        List<Account> accountsToImport = new ArrayList<>();
        Set<String> csvEmails = new HashSet<>();
        Set<String> csvUsernames = new HashSet<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Bỏ qua dòng trống
                if (line.isEmpty()) {
                    continue;
                }

                // Bỏ qua dòng tiêu đề (Header)
                if (lineNumber == 1 && (line.toLowerCase().contains("email") || line.toLowerCase().contains("username"))) {
                    continue;
                }

                // Tách cột theo dấu phẩy
                String[] parts = line.split(",");
                if (parts.length < 5) {
                    errors.add("Dòng " + lineNumber + ": Thiếu dữ liệu! Cần tối thiểu 5 cột: Email, Username, FullName, DepartmentID, PositionID");
                    continue;
                }

                String email = parts[0].trim().replace("\"", "");
                String username = parts[1].trim().replace("\"", "");
                String fullName = parts[2].trim().replace("\"", "");
                String deptIdStr = parts[3].trim().replace("\"", "");
                String posIdStr = parts[4].trim().replace("\"", "");
                String dateStr = parts.length > 5 ? parts[5].trim().replace("\"", "") : "";

                boolean lineHasError = false;

                // 1. Kiểm tra Email
                if (email.isEmpty()) {
                    errors.add("Dòng " + lineNumber + ": Email không được để trống!");
                    lineHasError = true;
                } else if (!utils.ScannerUtils.isValidEmail(email)) {
                    errors.add("Dòng " + lineNumber + ": Email '" + email + "' không đúng định dạng!");
                    lineHasError = true;
                } else if (csvEmails.contains(email.toLowerCase())) {
                    errors.add("Dòng " + lineNumber + ": Email '" + email + "' bị trùng lặp trong file CSV!");
                    lineHasError = true;
                } else if (accountRepository.isAccountExistsByEmail(email)) {
                    errors.add("Dòng " + lineNumber + ": Email '" + email + "' đã tồn tại trong CSDL!");
                    lineHasError = true;
                }

                // 2. Kiểm tra Username
                if (username.isEmpty()) {
                    errors.add("Dòng " + lineNumber + ": Username không được để trống!");
                    lineHasError = true;
                } else if (username.length() < 5 || username.length() > 50) {
                    errors.add("Dòng " + lineNumber + ": Username '" + username + "' phải từ 5-50 ký tự!");
                    lineHasError = true;
                } else if (csvUsernames.contains(username.toLowerCase())) {
                    errors.add("Dòng " + lineNumber + ": Username '" + username + "' bị trùng lặp trong file CSV!");
                    lineHasError = true;
                } else if (accountRepository.isAccountExistsByUsername(username)) {
                    errors.add("Dòng " + lineNumber + ": Username '" + username + "' đã tồn tại trong CSDL!");
                    lineHasError = true;
                }

                // 3. Kiểm tra FullName
                if (fullName.isEmpty()) {
                    errors.add("Dòng " + lineNumber + ": FullName không được để trống!");
                    lineHasError = true;
                } else if (fullName.length() < 3 || fullName.length() > 50) {
                    errors.add("Dòng " + lineNumber + ": FullName '" + fullName + "' phải từ 3-50 ký tự!");
                    lineHasError = true;
                }

                // 4. Kiểm tra khóa ngoại là số: DepartmentID
                int deptId = 0;
                try {
                    deptId = Integer.parseInt(deptIdStr);
                    if (deptId <= 0) {
                        errors.add("Dòng " + lineNumber + ": DepartmentID phải là số nguyên dương lớn hơn 0!");
                        lineHasError = true;
                    } else if (!departmentRepository.isDepartmentExistsById(deptId)) {
                        errors.add("Dòng " + lineNumber + ": DepartmentID = " + deptId + " không tồn tại trong hệ thống!");
                        lineHasError = true;
                    }
                } catch (NumberFormatException e) {
                    errors.add("Dòng " + lineNumber + ": Khóa ngoại DepartmentID '" + deptIdStr + "' phải là số nguyên!");
                    lineHasError = true;
                }

                // 5. Kiểm tra khóa ngoại là số: PositionID
                int posId = 0;
                try {
                    posId = Integer.parseInt(posIdStr);
                    if (posId <= 0) {
                        errors.add("Dòng " + lineNumber + ": PositionID phải là số nguyên dương lớn hơn 0!");
                        lineHasError = true;
                    } else if (!positionRepository.isPositionExistsById(posId)) {
                        errors.add("Dòng " + lineNumber + ": PositionID = " + posId + " không tồn tại trong hệ thống!");
                        lineHasError = true;
                    }
                } catch (NumberFormatException e) {
                    errors.add("Dòng " + lineNumber + ": Khóa ngoại PositionID '" + posIdStr + "' phải là số nguyên!");
                    lineHasError = true;
                }

                // 6. Kiểm tra CreateDate (nếu có)
                LocalDate createDate = LocalDate.now();
                if (!dateStr.isEmpty()) {
                    try {
                        createDate = LocalDate.parse(dateStr);
                    } catch (Exception e) {
                        errors.add("Dòng " + lineNumber + ": Ngày tạo '" + dateStr + "' không đúng định dạng (yyyy-MM-dd)!");
                        lineHasError = true;
                    }
                }

                if (!lineHasError) {
                    csvEmails.add(email.toLowerCase());
                    csvUsernames.add(username.toLowerCase());
                    Department dept = new Department(deptId, null);
                    Position pos = new Position(posId, null);
                    Account acc = new Account(0, email, username, fullName, dept, pos, createDate);
                    accountsToImport.add(acc);
                }
            }
        }

        // Nếu có lỗi, thông báo toàn bộ lỗi để người dùng sửa file CSV
        if (!errors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Tìm thấy ").append(errors.size()).append(" lỗi trong file CSV:\n");
            for (String err : errors) {
                sb.append("  - ").append(err).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }

        if (accountsToImport.isEmpty()) {
            throw new IllegalArgumentException("File CSV không có bản ghi nào để import!");
        }

        // Thực hiện import batch trong transaction
        int[] results = accountRepository.importAccounts(accountsToImport);
        return results.length;
    }
}
