package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.api.exceptions.InsufficientFundsException;
import web.api.models.*;
import web.api.models.enums.TransactionStatus;
import web.api.models.enums.TransactionType;
import web.api.repositories.AccountRepository;
import web.api.services.AccountService;
import web.api.services.BillerService;
import web.api.services.PayeeService;
import web.api.services.TransactionService;

import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final PayeeService payeeService;
    private final BillerService billerService;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    @Autowired
    public AccountController
            (AccountService accountService, PayeeService payeeService, AccountRepository accountRepository, TransactionService transactionService, BillerService billerService){
        this.accountService = accountService;
        this.payeeService = payeeService;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.billerService = billerService;
    }

    public Account createAccount(Account newAccount){
        return accountService.addNewAccount(newAccount);
    }

    @GetMapping("/billPayment")
    public ResponseEntity billPayment
            (@RequestParam("accountNo") Long accountNo,@RequestParam("billerId") Long billerId,@RequestParam("amount") double amount) throws InsufficientFundsException{
        Account userAccount = accountService.getAccount(accountNo);
        Biller billerObj = billerService.fetchBiller(billerId);
        Account billerAccount = accountService.getAccount(billerObj.getBillerAccount());
        if(amount<=userAccount.getAccountBalance()){
            Date date = new Date();
            String message = "Amount transfer of $"+amount+" to" +billerObj.getBillerName();
            userAccount.setAccountBalance(userAccount.getAccountBalance()-amount);
            accountRepository.save(userAccount);

            
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            throw new InsufficientFundsException("Insufficient Funds");
        }
    }

    @GetMapping("/fundTransfer")
    public ResponseEntity fundTransferToPayees
            (@RequestParam("accountNo") Long accountNo,@RequestParam("payeeId") Long payeeId,@RequestParam("amount") double amount) throws InsufficientFundsException{

        int status = accountService.transferFundsToPayees(accountNo, payeeId, amount);
        if(status == 1){
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recurringTransfer")
    public ResponseEntity setUpRecurringTransfer
            (@RequestParam("accountNo") Long accountNo, @RequestParam("payeeId") Long payeeId, @RequestParam("transferDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transferDate, @RequestParam("transferAmount") double transferAmount){
        try{
            accountService.saveRecurringTransferRequest(accountNo, payeeId, transferDate, transferAmount);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        catch(Exception ex){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}