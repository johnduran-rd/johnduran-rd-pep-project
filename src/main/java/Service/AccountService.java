package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account){
        if (accountIsValid(account)) {
            return accountDAO.insertAccount(account);
        }
        return null;
    }

    public Account loginAccount(Account account){
        return accountDAO.login(account);
    }

    private boolean accountIsValid(Account account){
        if (account.getPassword().length()<4 || account.getUsername().isBlank()) {
            return false;
        }
        return true;
    }
    
}
