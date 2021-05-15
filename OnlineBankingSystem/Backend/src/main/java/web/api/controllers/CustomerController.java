package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.models.*;
import web.api.services.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
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
        if(userAccounts != null){
            return new ResponseEntity<>(userAccounts, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updateUserPassword(@RequestParam("userId") Long userId, @RequestBody User u){
        ResponseEntity<?> responseEntity=null;
        try{
            User userObj = userService.getUserFromUserId(userId);
            boolean updated = userService.changePassword(userObj, u.getPassword());
            if(updated){
                responseEntity = new ResponseEntity<>(new String("Password Updated"), HttpStatus.OK);
            }
            else{
                responseEntity = new ResponseEntity<>(new String("Unable to update password"), HttpStatus.BAD_REQUEST);
            }
        }catch(Exception ex){
            new Exception("Password is not updated", ex);
        }
        return responseEntity;
    }

    @GetMapping("/viewPayees")
    public ResponseEntity<List<Payee>> viewUserPayees(@RequestParam("userId") Long userId){
        User requestedUser = userService.getUserFromUserId(userId);
        List<Payee> userPayees =  payeeUserRelationService.getUserPayees(requestedUser);
        return new ResponseEntity<>(userPayees, HttpStatus.OK);
    }


    @GetMapping("/viewBillers")
    public ResponseEntity<List<Biller>> viewUserBillers(){
        List<Biller> userBillers = billerService.getAllBillers();
        return new ResponseEntity<>(userBillers, HttpStatus.OK);
    }

    @PostMapping("/registerPayee")
    public ResponseEntity<?> registerPayee(@RequestBody Payee requestedPayee, @RequestParam("userId") Long userId){
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

    @PostMapping("/deleteAccounts")
    public ResponseEntity<?> requestToCloseAccounts(@RequestParam("userId") Long userId){
        boolean status = userService.takeCustomerCloseRequest(userId);
        if(status){
            return new ResponseEntity<>(new String("Accounts Closure Request accepted"), HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(new String("Unable to process request"), HttpStatus.BAD_REQUEST);
        }
    }
}
