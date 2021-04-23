package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.api.models.Account;
import web.api.models.Payee;
import web.api.services.AccountService;
import web.api.services.PayeeService;

@Controller
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final PayeeService payeeService;

    @Autowired
    public AccountController(AccountService accountService, PayeeService payeeService){
        this.accountService = accountService;
        this.payeeService = payeeService;
    }

    public Account createAccount(Account newAccount){
        return accountService.addNewAccount(newAccount);
    }

    /*@GetMapping("/fundTransfer")
    public ResponseEntity fundTransferToPayees
            (@RequestParam("accountNo") Long accountNo,@RequestParam("payeeId") Long payeeId,@RequestParam("amount") double amount){
        Account userAccount = accountService.getAccount(accountNo);
        Payee payeeObj = payeeService.fetchPayee(payeeId);
        Account payeeAccount = accountService.getAccount(payeeObj.getPayeeAccount());
        if(amount<=userAccount.getAccountBalance()){

        }
    }*/
}
