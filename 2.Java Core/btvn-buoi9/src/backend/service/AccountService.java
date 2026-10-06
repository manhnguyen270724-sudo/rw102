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
        if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống!");
        }
        if (account.getFullName() == null || account.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Họ và tên không được để trống!");
        }

        if (accountRepository.isAccountExistsByUsername(account.getUsername().trim())) {
            throw new IllegalArgumentException("Username '" + account.getUsername() + "' đã tồn tại!");
        }
        if (accountRepository.isAccountExistsByEmail(account.getEmail().trim())) {
            throw new IllegalArgumentException("Email '" + account.getEmail() + "' đã tồn tại!");
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
}
