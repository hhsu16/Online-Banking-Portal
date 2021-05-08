package web.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.models.Account;
import web.api.models.Transaction;
import web.api.services.AccountService;
import web.api.services.TransactionService;

import java.util.List;

@RestController
public class TransactionController
{

    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService)
    {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }


    @GetMapping("/viewTransactions")
    public ResponseEntity<?> getTransactions(@RequestParam("accountNo") Long accountNo)
    {

        Account account = accountService.getAccount(accountNo);

        List<Transaction> transactions= transactionService.getTransactions(account);
        return ResponseEntity.ok().body(transactions);
    }



}






