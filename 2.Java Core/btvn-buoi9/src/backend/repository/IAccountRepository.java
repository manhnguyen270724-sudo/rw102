package backend.repository;

import entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface IAccountRepository {
    List<Account> getListAccounts() throws SQLException;

    List<Account> getAccountsByUsername(String username) throws SQLException;

    Account getAccountByUsername(String username) throws SQLException;

    boolean isAccountExistsByUsername(String username) throws SQLException;

    boolean isAccountExistsByEmail(String email) throws SQLException;

    boolean createAccount(Account account) throws SQLException;

    boolean deleteAccountByUsername(String username) throws SQLException;

    boolean updateFullnameByUsername(String username, String newFullName) throws SQLException;
}
