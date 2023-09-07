package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDataAccessObject){
        this.accountDAO = accountDataAccessObject;
    }

    /**
     * Adds object entry to database only if it doesn't exist in there
     * @param acc: Account object
     * @return the object if successfully added, null otherwise
     */
    public Account addAccount(Account acc){
        return accountDAO.insertAccount(acc);
    }
}
