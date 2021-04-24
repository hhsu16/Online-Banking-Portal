package web.api.controllers;

import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.api.exceptions.InsufficientFundsException;
import web.api.models.*;
import web.api.repositories.AccountRepository;
import web.api.services.AccountService;
import web.api.services.PayeeService;
import web.api.services.TransactionService;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final PayeeService payeeService;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;

    @Autowired
    public AccountController
            (AccountService accountService, PayeeService payeeService, AccountRepository accountRepository, TransactionService transactionService){
        this.accountService = accountService;
        this.payeeService = payeeService;
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    public Account createAccount(Account newAccount){
        return accountService.addNewAccount(newAccount);
    }

    @GetMapping("/fundTransfer")
    public ResponseEntity fundTransferToPayees
            (@RequestParam("accountNo") Long accountNo,@RequestParam("payeeId") Long payeeId,@RequestParam("amount") double amount) throws InsufficientFundsException{
        Account userAccount = accountService.getAccount(accountNo);
        Payee payeeObj = payeeService.fetchPayee(payeeId);
        Account payeeAccount = accountService.getAccount(payeeObj.getPayeeAccount());
        if(amount<=userAccount.getAccountBalance()){
            Date date = new Date();
            String message = "Amount transfer of $"+amount+" from "+userAccount.getUser().getFirstName()+" to "+payeeObj.getPayeeName();
            userAccount.setAccountBalance(userAccount.getAccountBalance()-amount);
            accountRepository.save(userAccount);
            if(payeeAccount!=null){
                payeeAccount.setAccountBalance(payeeAccount.getAccountBalance()+amount);
                accountRepository.save(payeeAccount);
                transactionService.addTransaction(
                        new Transaction(date, message, TransactionType.CREDIT, amount, TransactionStatus.SUCCESS, payeeAccount));
            }
            transactionService.addTransaction(
                    new Transaction(date, message, TransactionType.DEBIT, amount, TransactionStatus.SUCCESS, userAccount));

            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            throw new InsufficientFundsException("Insufficient Funds");
        }
    }
}