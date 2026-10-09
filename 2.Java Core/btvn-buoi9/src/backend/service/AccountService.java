package backend.service;

import backend.repository.AccountRepository;
import backend.repository.IAccountRepository;
import entity.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

        if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống!");
        }
        String email = account.getEmail().trim();
        if (email.length() < 5 || email.length() > 50) {
            throw new IllegalArgumentException("Email phải có độ dài từ 5 đến 50 kí tự!");
        }
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new IllegalArgumentException("Email không đúng định dạng!");
        }

        if (account.getFullName() == null || account.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ và tên không được để trống!");
        }
        String fullName = account.getFullName().trim();
        if (fullName.length() < 5 || fullName.length() > 50) {
            throw new IllegalArgumentException("Họ và tên phải có độ dài từ 5 đến 50 kí tự!");
        }

        if (accountRepository.isAccountExistsByUsername(username)) {
            throw new IllegalArgumentException("Username '" + username + "' đã tồn tại!");
        }
        if (accountRepository.isAccountExistsByEmail(email)) {
            throw new IllegalArgumentException("Email '" + email + "' đã tồn tại!");
        }

        return accountRepository.createAccount(account);
    }

    @Override
    public boolean deleteAccountByUsername(String username) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        if (!accountRepository.isAccountExistsByUsername(username.trim())) {
            throw new IllegalArgumentException("Không tìm thấy account với username: " + username);
        }
        return accountRepository.deleteAccountByUsername(username.trim());
    }

    @Override
    public boolean updateFullnameByUsername(String username, String newFullName) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        if (newFullName == null || newFullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ và tên mới không được để trống!");
        }
        if (!accountRepository.isAccountExistsByUsername(username.trim())) {
            throw new IllegalArgumentException("Không tìm thấy account với username: " + username);
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
        if (id <= 0 || !accountRepository.isAccountExistsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy account có ID: " + id);
        }
        return accountRepository.deleteAccountById(id);
    }

    @Override
    public boolean updateUsernameById(int id, String newUsername) throws Exception {
        if (id <= 0 || !accountRepository.isAccountExistsById(id)) {
            throw new IllegalArgumentException("Không tìm thấy account có ID: " + id);
        }
        if (newUsername == null || newUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        String trimmedUsername = newUsername.trim();
        if (trimmedUsername.length() < 5 || trimmedUsername.length() > 50) {
            throw new IllegalArgumentException("Username phải có độ dài từ 5 đến 50 kí tự!");
        }
        if (accountRepository.isAccountExistsByUsername(trimmedUsername)) {
            throw new IllegalArgumentException("Username '" + trimmedUsername + "' đã tồn tại trong DB!");
        }
        return accountRepository.updateUsernameById(id, trimmedUsername);
    }
}
