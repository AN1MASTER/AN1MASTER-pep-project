package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * @param account object representing new account
     * @return newly registered account after validation
     */
    public Account registerAccount(Account account) {
        if (!account.getUsername().isBlank() && account.getPassword().length() >= 4 && !checkIfExists(account)) {
            System.out.println("username:" + account.getUsername());
            return this.accountDAO.insertAccount(account);
        }
        System.out.println("User not Created!");
        return null;
    }

    /**
     * Check if account already exists to validate user registration
     * @param account object representing potentially new user
     * @return boolean determining existence of account username
     */
    private boolean checkIfExists(Account account) {
        List<Account> accounts = this.accountDAO.getAllAccounts();
        if (accounts.contains(account)) {
            return true;
        }
        return false;
    }

    public Account login(Account account) {
        return this.accountDAO.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
