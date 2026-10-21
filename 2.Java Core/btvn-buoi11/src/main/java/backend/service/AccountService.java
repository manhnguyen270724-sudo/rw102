package backend.service;

import backend.repository.AccountRepository;
import backend.repository.DepartmentRepository;
import backend.repository.IAccountRepository;
import backend.repository.IDepartmentRepository;
import backend.repository.IPositionRepository;
import backend.repository.PositionRepository;
import entity.Account;
import entity.Department;
import entity.ImportResult;
import entity.Position;
import utils.ScannerUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    public ImportResult importAccountsFromCSV(String filePath) throws Exception {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Đường dẫn file CSV không được để trống!");
        }

        File file = new File(filePath.trim());
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("Không tìm thấy file CSV tại đường dẫn: " + filePath);
        }

        List<FailedRow> failedRows = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        List<Account> accountsToImport = new ArrayList<>();
        Set<String> csvEmails = new HashSet<>();
        Set<String> csvUsernames = new HashSet<>();
        int totalRows = 0;

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

                totalRows++;
                List<String> rowErrors = new ArrayList<>();

                // Tách cột theo dấu phẩy (dùng -1 để giữ lại các cột trống ở cuối)
                String[] parts = line.split(",", -1);
                if (parts.length < 5) {
                    rowErrors.add("Thiếu dữ liệu! Cần tối thiểu 5 cột: Email, Username, FullName, DepartmentID, PositionID (hiện có " + parts.length + " cột)");
                    String rawEmail = parts.length > 0 ? parts[0].trim().replace("\"", "") : "";
                    String rawUsername = parts.length > 1 ? parts[1].trim().replace("\"", "") : "";
                    String rawFullName = parts.length > 2 ? parts[2].trim().replace("\"", "") : "";
                    String rawDeptId = parts.length > 3 ? parts[3].trim().replace("\"", "") : "";
                    String rawPosId = parts.length > 4 ? parts[4].trim().replace("\"", "") : "";
                    String rawDate = parts.length > 5 ? parts[5].trim().replace("\"", "") : "";
                    String reason = String.join("; ", rowErrors);
                    failedRows.add(new FailedRow(lineNumber, rawEmail, rawUsername, rawFullName, rawDeptId, rawPosId, rawDate, reason));
                    errorMessages.add("Dòng " + lineNumber + ": " + reason);
                    continue;
                }

                String email = parts[0].trim().replace("\"", "");
                String username = parts[1].trim().replace("\"", "");
                String fullName = parts[2].trim().replace("\"", "");
                String deptIdStr = parts[3].trim().replace("\"", "");
                String posIdStr = parts[4].trim().replace("\"", "");
                String dateStr = parts.length > 5 ? parts[5].trim().replace("\"", "") : "";

                // 1. Kiểm tra Email
                if (email.isEmpty()) {
                    rowErrors.add("Email không được để trống");
                } else if (!ScannerUtils.isValidEmail(email)) {
                    rowErrors.add("Email '" + email + "' không đúng định dạng tiêu chuẩn");
                } else if (csvEmails.contains(email.toLowerCase())) {
                    rowErrors.add("Email '" + email + "' bị trùng lặp trong file CSV");
                } else if (accountRepository.isAccountExistsByEmail(email)) {
                    rowErrors.add("Email '" + email + "' đã tồn tại trong CSDL");
                }

                // 2. Kiểm tra Username
                if (username.isEmpty()) {
                    rowErrors.add("Username không được để trống");
                } else if (username.contains(" ")) {
                    rowErrors.add("Username không được chứa khoảng trắng");
                } else if (username.length() < 5 || username.length() > 50) {
                    rowErrors.add("Username '" + username + "' phải từ 5-50 ký tự");
                } else if (csvUsernames.contains(username.toLowerCase())) {
                    rowErrors.add("Username '" + username + "' bị trùng lặp trong file CSV");
                } else if (accountRepository.isAccountExistsByUsername(username)) {
                    rowErrors.add("Username '" + username + "' đã tồn tại trong CSDL");
                }

                // 3. Kiểm tra FullName
                if (fullName.isEmpty()) {
                    rowErrors.add("FullName không được để trống");
                } else if (fullName.length() < 3 || fullName.length() > 50) {
                    rowErrors.add("FullName '" + fullName + "' phải từ 3-50 ký tự");
                }

                // 4. Kiểm tra khóa ngoại là số: DepartmentID
                int deptId = 0;
                if (deptIdStr.isEmpty()) {
                    rowErrors.add("DepartmentID không được để trống");
                } else {
                    try {
                        deptId = Integer.parseInt(deptIdStr);
                        if (deptId <= 0) {
                            rowErrors.add("DepartmentID phải là số nguyên dương lớn hơn 0");
                        } else if (!departmentRepository.isDepartmentExistsById(deptId)) {
                            rowErrors.add("DepartmentID = " + deptId + " không tồn tại trong hệ thống");
                        }
                    } catch (NumberFormatException e) {
                        rowErrors.add("Khóa ngoại DepartmentID '" + deptIdStr + "' phải là số nguyên");
                    }
                }

                // 5. Kiểm tra khóa ngoại là số: PositionID
                int posId = 0;
                if (posIdStr.isEmpty()) {
                    rowErrors.add("PositionID không được để trống");
                } else {
                    try {
                        posId = Integer.parseInt(posIdStr);
                        if (posId <= 0) {
                            rowErrors.add("PositionID phải là số nguyên dương lớn hơn 0");
                        } else if (!positionRepository.isPositionExistsById(posId)) {
                            rowErrors.add("PositionID = " + posId + " không tồn tại trong hệ thống");
                        }
                    } catch (NumberFormatException e) {
                        rowErrors.add("Khóa ngoại PositionID '" + posIdStr + "' phải là số nguyên");
                    }
                }

                // 6. Kiểm tra CreateDate (nếu có)
                LocalDate createDate = LocalDate.now();
                if (!dateStr.isEmpty()) {
                    try {
                        createDate = LocalDate.parse(dateStr);
                    } catch (Exception e) {
                        rowErrors.add("Ngày tạo '" + dateStr + "' không đúng định dạng (yyyy-MM-dd)");
                    }
                }

                if (rowErrors.isEmpty()) {
                    // Dòng hợp lệ: lưu vào danh sách chờ import và cập nhật danh sách email/username đã duyệt trong CSV
                    csvEmails.add(email.toLowerCase());
                    csvUsernames.add(username.toLowerCase());
                    Department dept = new Department(deptId, null);
                    Position pos = new Position(posId, null);
                    Account acc = new Account(0, email, username, fullName, dept, pos, createDate);
                    accountsToImport.add(acc);
                } else {
                    // Dòng có lỗi: đưa vào danh sách dòng lỗi để xuất file
                    String reason = String.join("; ", rowErrors);
                    failedRows.add(new FailedRow(lineNumber, email, username, fullName, deptIdStr, posIdStr, dateStr, reason));
                    errorMessages.add("Dòng " + lineNumber + ": " + reason);
                }
            }
        }

        if (totalRows == 0) {
            throw new IllegalArgumentException("File CSV không có bản ghi nào để import!");
        }

        // 1. Thực hiện import batch trong transaction cho các bản ghi hợp lệ
        int successCount = 0;
        if (!accountsToImport.isEmpty()) {
            int[] results = accountRepository.importAccounts(accountsToImport);
            successCount = results.length;
        }

        // 2. Xuất file lỗi nếu có bản ghi không hợp lệ
        String errorFilePath = null;
        if (!failedRows.isEmpty()) {
            errorFilePath = exportErrorAccountsToCSV(filePath, failedRows);
        } else {
            // Nếu không có dòng lỗi nào, dọn dẹp file lỗi cũ nếu tồn tại
            String oldErrorFilePath = generateErrorFilePath(filePath);
            File oldErrorFile = new File(oldErrorFilePath);
            if (oldErrorFile.exists()) {
                oldErrorFile.delete();
            }
        }

        return new ImportResult(totalRows, successCount, failedRows.size(), errorFilePath, errorMessages);
    }

    /**
     * Tạo đường dẫn file lỗi từ đường dẫn file gốc (ví dụ: accounts.csv -> accounts_errors.csv)
     */
    private String generateErrorFilePath(String originalFilePath) {
        File file = new File(originalFilePath);
        File parent = file.getParentFile();
        String name = file.getName();
        String baseName = name;
        String extension = ".csv";
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex > 0) {
            baseName = name.substring(0, dotIndex);
            extension = name.substring(dotIndex);
        }
        String errorFileName = baseName + "_errors" + extension;
        return parent != null ? new File(parent, errorFileName).getPath() : errorFileName;
    }

    /**
     * Xuất danh sách các dòng Account lỗi ra file CSV kèm cột nguyên nhân lỗi.
     */
    private String exportErrorAccountsToCSV(String originalFilePath, List<FailedRow> failedRows) throws IOException {
        String errorFilePath = generateErrorFilePath(originalFilePath);
        File errorFile = new File(errorFilePath);

        // Đảm bảo thư mục cha tồn tại
        File parentDir = errorFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(errorFile), StandardCharsets.UTF_8))) {
            // Ghi BOM UTF-8 để Excel hiển thị tiếng Việt có dấu chuẩn xác
            bw.write("\uFEFF");
            // Ghi Header
            bw.write("Email,Username,FullName,DepartmentID,PositionID,CreateDate,ErrorReason");
            bw.newLine();

            for (FailedRow row : failedRows) {
                String line = String.join(",",
                        escapeCsv(row.getEmail()),
                        escapeCsv(row.getUsername()),
                        escapeCsv(row.getFullName()),
                        escapeCsv(row.getDeptIdStr()),
                        escapeCsv(row.getPosIdStr()),
                        escapeCsv(row.getDateStr()),
                        escapeCsv(row.getReason())
                );
                bw.write(line);
                bw.newLine();
            }
        }
        return errorFile.getAbsolutePath();
    }

    /**
     * Chuẩn hóa escape trường dữ liệu CSV theo chuẩn RFC 4180.
     */
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Lớp lưu trữ thông tin dòng bị lỗi để xuất ra file CSV lỗi.
     */
    private static class FailedRow {
        private final int lineNumber;
        private final String email;
        private final String username;
        private final String fullName;
        private final String deptIdStr;
        private final String posIdStr;
        private final String dateStr;
        private final String reason;

        public FailedRow(int lineNumber, String email, String username, String fullName,
                         String deptIdStr, String posIdStr, String dateStr, String reason) {
            this.lineNumber = lineNumber;
            this.email = email;
            this.username = username;
            this.fullName = fullName;
            this.deptIdStr = deptIdStr;
            this.posIdStr = posIdStr;
            this.dateStr = dateStr;
            this.reason = reason;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public String getEmail() {
            return email;
        }

        public String getUsername() {
            return username;
        }

        public String getFullName() {
            return fullName;
        }

        public String getDeptIdStr() {
            return deptIdStr;
        }

        public String getPosIdStr() {
            return posIdStr;
        }

        public String getDateStr() {
            return dateStr;
        }

        public String getReason() {
            return reason;
        }
    }
}
