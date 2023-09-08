package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDataAccessObject){
        this.accountDAO = accountDataAccessObject;
    }

    /**
     * Adds object entry to database 
     * Preconditions: Account must not already exist, username must not be blank, 
     * password must be at least 4 characters long
     * @param acc: Account object
     * @return the object if successfully added, null otherwise
     */
    public Account addAccount(Account acc){
        if(acc.getUsername().length() <= 0 
            || acc.getPassword().length() <= 3 
            || accountDAO.getAccountByUsername(acc.getUsername()) != null){
                return null;
        }
        return accountDAO.insertAccount(acc);
    }
}
