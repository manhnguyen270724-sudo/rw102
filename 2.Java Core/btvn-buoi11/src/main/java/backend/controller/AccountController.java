package backend.controller;

import backend.service.AccountService;
import backend.service.IAccountService;
import entity.Account;

import java.util.List;

public class AccountController implements IAccountController {

    private final IAccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
    }

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public List<Account> getListAccounts() throws Exception {
        return accountService.getListAccounts();
    }

    @Override
    public List<Account> getAccountsByUsername(String username) throws Exception {
        return accountService.getAccountsByUsername(username);
    }

    @Override
    public Account getAccountByUsername(String username) throws Exception {
        return accountService.getAccountByUsername(username);
    }

    @Override
    public boolean isAccountExistsByUsername(String username) throws Exception {
        return accountService.isAccountExistsByUsername(username);
    }

    @Override
    public boolean isAccountExistsByEmail(String email) throws Exception {
        return accountService.isAccountExistsByEmail(email);
    }

    @Override
    public boolean createAccount(Account account) throws Exception {
        return accountService.createAccount(account);
    }

    @Override
    public boolean deleteAccountByUsername(String username) throws Exception {
        return accountService.deleteAccountByUsername(username);
    }

    @Override
    public boolean updateFullnameByUsername(String username, String newFullName) throws Exception {
        return accountService.updateFullnameByUsername(username, newFullName);
    }

    @Override
    public Account getAccountById(int id) throws Exception {
        return accountService.getAccountById(id);
    }

    @Override
    public boolean isAccountExistsById(int id) throws Exception {
        return accountService.isAccountExistsById(id);
    }

    @Override
    public boolean deleteAccountById(int id) throws Exception {
        return accountService.deleteAccountById(id);
    }

    @Override
    public boolean updateUsernameById(int id, String newUsername) throws Exception {
        return accountService.updateUsernameById(id, newUsername);
    }

    @Override
    public int importAccountsFromCSV(String filePath) throws Exception {
        return accountService.importAccountsFromCSV(filePath);
    }
}
