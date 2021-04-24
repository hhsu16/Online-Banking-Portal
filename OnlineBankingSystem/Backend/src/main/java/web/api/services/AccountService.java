package web.api.services;

import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Account;
import web.api.models.User;
import web.api.repositories.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account addNewAccount(Account accountObj){
        return accountRepository.save(accountObj);
    }

    public List<Account> getUserAccounts(Long userId){
        return accountRepository.findAccountsByUser_UserIdEquals(userId);
    }

    @Nullable
    public Account getAccount(Long accountNo){
        return accountRepository.findAccountByAccountNoEquals(accountNo);
    }
}
