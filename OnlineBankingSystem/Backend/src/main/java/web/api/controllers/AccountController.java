package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import web.api.models.Account;
import web.api.services.AccountService;

@Controller
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    public Account createAccount(Account newAccount){
        return accountService.addNewAccount(newAccount);
    }
}
