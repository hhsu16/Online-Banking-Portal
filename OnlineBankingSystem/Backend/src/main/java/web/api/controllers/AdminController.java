package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.models.Account;
import web.api.models.Prospect;
import web.api.models.User;
import web.api.services.AccountService;
import web.api.services.ProspectService;
import web.api.services.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/api/admin")
public class AdminController {

    private final ProspectService prospectService;
    private final UserService userService;
    private final AccountService accountService;
    private final AccountController accountController;

    @Autowired
    public AdminController(ProspectService prospectService, UserService userService, AccountService accountService, AccountController accountController)
    {
        this.prospectService = prospectService;
        this.userService = userService;
        this.accountService = accountService;
        this.accountController = accountController;
    }

    @GetMapping("/newUsers")
    public ResponseEntity<List<Prospect>> getUsers() {
        System.out.println("In getMapping of Users");
        List<Prospect> prospects = prospectService.getProspects();
        return ResponseEntity.ok().body(prospects);
    }

    @PostMapping("/addNewCustomer")
    public ResponseEntity<Account> openOnlineBankingAccount(@RequestBody Prospect prospect){
        Account newAccount = null;

        User isUserCreated = userService.addUser(prospect);
        if(isUserCreated != null){
            prospectService.updateProspectStatus(false, isUserCreated.getEmailId(), isUserCreated.getContact());
            newAccount = accountController.createAccount(new Account(isUserCreated, true, prospect.getAccountTypeWanted(), 0.0));
        }

        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);

    }
}
