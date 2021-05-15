package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.models.*;
import web.api.repositories.DeleteCustomerRepository;
import web.api.services.AccountService;
import web.api.services.ProspectService;
import web.api.services.UserService;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class AdminController {

    private final ProspectService prospectService;
    private final UserService userService;
    private final AccountController accountController;
    private final AccountService accountService;
    private final DeleteCustomerRepository deleteCustomerRepository;

    @Autowired
    public AdminController(DeleteCustomerRepository deleteCustomerRepository, AccountService accountService, ProspectService prospectService, UserService userService, AccountController accountController)
    {
        this.prospectService = prospectService;
        this.userService = userService;
        this.accountController = accountController;
        this.accountService = accountService;
        this.deleteCustomerRepository = deleteCustomerRepository;
    }

    @GetMapping("/prospects")
    public ResponseEntity<List<Prospect>> getUsers() {
        List<Prospect> prospects = prospectService.getProspects();
        return new ResponseEntity<>(prospects, HttpStatus.OK);
    }

    @PostMapping("/addNewCustomer")
    public ResponseEntity<Account> openOnlineBankingAccount(@RequestParam("prospectId") Long prospectId){
        Account newAccount = null;
        Prospect prospect = prospectService.findProspectById(prospectId);
        if(prospect!=null){
            boolean isUserExists = userService.checkEmailIdIfExists(prospect.getEmailId());
            if(!isUserExists){
                User isUserCreated = userService.addUser(prospect);
                if(isUserCreated != null) {
                    prospectService.updateProspectStatus(prospect.getProspectStatus(), prospect.getEmailId());
                    newAccount = accountController.createAccount(new Account(isUserCreated, true, prospect.getAccountTypeWanted(), 0.0));
                }
            }
            else{
                User userObj = userService.getUserByEmailId(prospect.getEmailId());
                prospectService.updateProspectStatus(prospect.getProspectStatus(), prospect.getEmailId());
                newAccount = accountController.createAccount(new Account(userObj,true, prospect.getAccountTypeWanted(), 0.0));
            }

        }

        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @GetMapping("/viewCustomerAccounts")
    public ResponseEntity<List<Account>> viewCustomerAccounts(){
        List<Account> customerAccounts = accountService.getCustomerAccounts();
        return new ResponseEntity<>(customerAccounts, HttpStatus.OK);
    }

    @PutMapping("/rejectProspect")
    public ResponseEntity<?> rejectProspectCustomer(@RequestParam("prospectId") Long prospectId){
        ResponseEntity<?> responseEntity;
        boolean status = prospectService.rejectProspect(prospectId);
        if(status){
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping("/viewCustomers")
    public ResponseEntity<List<User>> viewCustomers(){
        List<User> customersList = userService.getCustomers();
        return new ResponseEntity<>(customersList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<?> closeCustomerAccount(@RequestParam("userId") Long userId){
        double balance = userService.deleteCustomerAccount(userId);
        if(balance>=0){
            return new ResponseEntity<>(new String("Account Closed\nCheque of $"+balance+" issued for User: "+userId), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/closeCustomers")
    public ResponseEntity<List<User>> getAccountClosingRequests(){
        List<DeleteCustomer> dcs = deleteCustomerRepository.findAll();
        ArrayList<User> users = new ArrayList<>();
        for (DeleteCustomer dc :dcs) {
            users.add(userService.getUserFromUserId(dc.getDeleteUserId()));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
