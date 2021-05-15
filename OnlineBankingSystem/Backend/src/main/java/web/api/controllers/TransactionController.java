package web.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.models.Account;
import web.api.models.Transaction;
import web.api.repositories.TransactionRepository;
import web.api.services.AccountService;
import web.api.services.TransactionService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class TransactionController
{

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService, AccountService accountService)
    {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
    }


    @GetMapping("/viewTransactions")
    public ResponseEntity<?> getTransactions(@RequestParam("accountNo") Long accountNo)
    {

        Account account = accountService.getAccount(accountNo);

        List<Transaction> transactions= transactionService.getTransactions(account);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<?> getAllTransactions(){
        List<Transaction> transactionsList = transactionRepository.findAll();

        return new ResponseEntity<>(transactionsList, HttpStatus.OK);
    }

    @GetMapping("/userTransactions")
    public ResponseEntity<?> getUserTransactions(@RequestParam("userId") Long userId){
        List<Transaction> userTransactions = transactionService.getAllUserTransactions(userId);
        return new ResponseEntity<>(userTransactions, HttpStatus.OK);
    }



}






