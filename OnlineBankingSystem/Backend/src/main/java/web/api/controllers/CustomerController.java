package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.models.*;
import web.api.services.*;

import java.util.List;

@Controller
@RequestMapping("api/customer")
@CrossOrigin("http://localhost:3000/")
public class CustomerController {

    private final PayeeUserRelationService payeeUserRelationService;
    private final PayeeService payeeService;
    private final BillerService billerService;
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public CustomerController(AccountService accountService, PayeeUserRelationService payeeUserRelationService, PayeeService payeeService, UserService userService, BillerService billerService){
        this.payeeUserRelationService = payeeUserRelationService;
        this.payeeService = payeeService;
        this.userService = userService;
        this.billerService = billerService;
        this.accountService = accountService;
    }

    @GetMapping("/viewAccounts")
    public ResponseEntity<List<Account>> viewUserAccounts(@RequestParam("userId") Long userId){
        List<Account> userAccounts = accountService.getUserAccounts(userId);
        if(userAccounts.size()>0){
            return new ResponseEntity<>(userAccounts, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/viewPayees")
    public ResponseEntity<List<Payee>> viewUserPayees(@RequestParam("userId") Long userId){
        User requestedUser = userService.getUserFromUserId(userId);
        List<Payee> userPayees =  payeeUserRelationService.getUserPayees(requestedUser);

        if(userPayees.size()>0){
            return new ResponseEntity<>(userPayees, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/viewBillers")
    public ResponseEntity<List<Biller>> viewUserBillers(@RequestParam("userId") Long userId){
        User requestedUser = userService.getUserFromUserId(userId);
        List<Biller> userBillers = billerService.getAllBillers();

        if(userBillers.size()>0){
            return new ResponseEntity<>(userBillers, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registerPayee")
    public ResponseEntity<PayeeUserRelation> registerPayee(@RequestBody Payee requestedPayee, @RequestParam("userId") Long userId){
        Payee addedPayee = null;
        PayeeUserRelation addedRelation = null;
        User requestedUser = userService.getUserFromUserId(userId);
        if(requestedPayee!=null){
            addedPayee = payeeService.addPayee(requestedPayee);
        }
        if(requestedUser!=null && addedPayee!=null){
            addedRelation = payeeUserRelationService.addNewPayeeUserRelation(new PayeeUserRelation(requestedUser, addedPayee));
        }

        if(addedRelation!=null){
            return new ResponseEntity<>(addedRelation, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
