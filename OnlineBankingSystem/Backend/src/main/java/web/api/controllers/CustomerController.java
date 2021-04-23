package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.api.models.Payee;
import web.api.models.PayeeUserRelation;
import web.api.models.User;
import web.api.services.PayeeService;
import web.api.services.PayeeUserRelationService;
import web.api.services.UserService;

import java.util.List;

@Controller
@RequestMapping("api/customer")
@CrossOrigin("http://localhost:3000/")
public class CustomerController {

    private final PayeeUserRelationService payeeUserRelationService;
    private final PayeeService payeeService;
    private final UserService userService;

    @Autowired
    public CustomerController(PayeeUserRelationService payeeUserRelationService, PayeeService payeeService, UserService userService){
        this.payeeUserRelationService = payeeUserRelationService;
        this.payeeService = payeeService;
        this.userService = userService;
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
