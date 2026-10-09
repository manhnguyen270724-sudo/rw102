package backend.service;

import entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountService {
    List<Account> getListAccounts() throws SQLException;

    List<Account> getAccountsByUsername(String username) throws SQLException;

    Account getAccountByUsername(String username) throws SQLException;

    boolean isAccountExistsByUsername(String username) throws SQLException;

    boolean isAccountExistsByEmail(String email) throws SQLException;

    boolean createAccount(Account account) throws Exception;

    boolean deleteAccountByUsername(String username) throws Exception;

    boolean updateFullnameByUsername(String username, String newFullName) throws Exception;

    Account getAccountById(int id) throws SQLException;

    boolean isAccountExistsById(int id) throws SQLException;

    boolean deleteAccountById(int id) throws Exception;

    boolean updateUsernameById(int id, String newUsername) throws Exception;
}

