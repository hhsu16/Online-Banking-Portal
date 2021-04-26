package web.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.models.Account;
import web.api.models.Transaction;
import web.api.models.User;
import web.api.services.TransactionService;

import java.util.List;

@Controller
@RequestMapping("/api/account")

public class TransactionController
{

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    /*@PostMapping("/addTransactions")
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        int addObj = transactionService.addTransaction(transaction);

        if (addObj == 1) {
            return ResponseEntity.ok().body("transaction created");
        } else {
            return ResponseEntity.badRequest().body("transaction failed");
        }
    }*/

    @GetMapping("/showTransactions")
    public ResponseEntity<?> getTransactions(@RequestBody Account account)
    {


        List<Transaction> transactions= transactionService.getTransaction(account);
        return ResponseEntity.ok().body(transactions.toString());
    }



}






