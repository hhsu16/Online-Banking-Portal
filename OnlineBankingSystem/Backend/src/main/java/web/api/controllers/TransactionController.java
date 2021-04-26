package web.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.models.Account;
import web.api.models.Transaction;
import web.api.models.User;
import web.api.services.AccountService;
import web.api.services.TransactionService;

import java.util.List;

@Controller
@RequestMapping("/api/account")

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


    @GetMapping("/showTransactions")
    public ResponseEntity<?> getTransactions(@RequestParam("accountNo") Long accountNo)
    {

        Account account = accountService.getAccount(accountNo);

        List<Transaction> transactions= transactionService.getTransaction(account);
        return ResponseEntity.ok().body(transactions);
    }



}






