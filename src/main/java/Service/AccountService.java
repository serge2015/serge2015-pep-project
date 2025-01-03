package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }
    
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        accountDAO.addAccount(account);
        return account;
    }

    public Account checkAccount(Account account){
        accountDAO.checkAccount(account);
        return account;
    }

    public Account getAccountById(int id){
        Account account = accountDAO.getAccountByIdAccount(id);
        return account;
    }
}

