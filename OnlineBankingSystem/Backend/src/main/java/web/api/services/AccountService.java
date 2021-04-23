package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Account;
import web.api.models.User;
import web.api.repositories.AccountRepository;

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

}
