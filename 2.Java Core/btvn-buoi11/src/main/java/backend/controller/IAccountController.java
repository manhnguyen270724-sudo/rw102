package backend.controller;

import entity.Account;
import entity.ImportResult;

import java.util.List;

public interface IAccountController {
    List<Account> getListAccounts() throws Exception;

    List<Account> getAccountsByUsername(String username) throws Exception;

    Account getAccountByUsername(String username) throws Exception;

    boolean isAccountExistsByUsername(String username) throws Exception;

    boolean isAccountExistsByEmail(String email) throws Exception;

    boolean createAccount(Account account) throws Exception;

    boolean deleteAccountByUsername(String username) throws Exception;

    boolean updateFullnameByUsername(String username, String newFullName) throws Exception;

    Account getAccountById(int id) throws Exception;

    boolean isAccountExistsById(int id) throws Exception;

    boolean deleteAccountById(int id) throws Exception;

    boolean updateUsernameById(int id, String newUsername) throws Exception;

    ImportResult importAccountsFromCSV(String filePath) throws Exception;
}
