package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.models.Account;
import web.api.models.Prospect;
import web.api.models.Transaction;
import web.api.models.User;
import web.api.services.AccountService;
import web.api.services.ProspectService;
import web.api.services.TransactionService;
import web.api.services.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class AdminController {

    private final ProspectService prospectService;
    private final UserService userService;
    private final AccountController accountController;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public AdminController(TransactionService transactionService, AccountService accountService, ProspectService prospectService, UserService userService, AccountController accountController)
    {
        this.prospectService = prospectService;
        this.userService = userService;
        this.accountController = accountController;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/prospects")
    public ResponseEntity<List<Prospect>> getUsers() {
        List<Prospect> prospects = prospectService.getProspects();
        return ResponseEntity.ok().body(prospects);
    }

    @PostMapping("/addNewCustomer")
    public ResponseEntity<Account> openOnlineBankingAccount(@RequestParam("prospectId") Long prospectId){
        Account newAccount = null;
        Prospect prospect = prospectService.findProspectById(prospectId);
        if(prospect!=null){
            User isUserCreated = userService.addUser(prospect);
            if(isUserCreated != null){
                prospectService.updateProspectStatus(prospect.getProspectStatus(), prospect.getEmailId());
                newAccount = accountController.createAccount(new Account(isUserCreated, true, prospect.getAccountTypeWanted(), 0.0));
            }
        }

        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);

    }

    @GetMapping("/viewCustomerAccounts")
    public ResponseEntity<List<Account>> viewCustomerAccounts(){
        List<Account> customerAccounts = accountService.getCustomerAccounts();
        return new ResponseEntity<>(customerAccounts, HttpStatus.OK);
    }

    @GetMapping("/viewCustomers")
    public ResponseEntity<List<User>> viewCustomers(){
        List<User> customersList = userService.getCustomers();
        return new ResponseEntity<>(customersList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<?> closeCustomerAccount(@RequestParam("userId") Long userId){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
