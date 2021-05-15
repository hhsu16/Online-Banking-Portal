package web.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.api.exceptions.InsufficientFundsException;
import web.api.models.*;
import web.api.services.AccountService;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    public Account createAccount(Account newAccount){
        return accountService.addNewAccount(newAccount);
    }

    @GetMapping("/billPayment")
    public ResponseEntity<?> billPayment
            (@RequestParam("accountNo") Long accountNo,@RequestParam("billerId") Long billerId,@RequestParam("amount") double amount) throws InsufficientFundsException{
        int status = accountService.billPaymentToBillers(accountNo, billerId, amount);
        if(status == 1){
            return new ResponseEntity<>(new String("Bill Payment successful"), HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(new String("Bill Payment failed"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fundTransfer")
    public ResponseEntity<?> fundTransferToPayees
            (@RequestParam("accountNo") Long accountNo,@RequestParam("payeeId") Long payeeId,@RequestParam("amount") double amount) throws InsufficientFundsException{

        int status = accountService.transferFundsToPayees(accountNo, payeeId, amount);
        if(status == 1){
            return new ResponseEntity<>(new String("Fund transfer successful"), HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(new String("Fund transfer failed"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recurringTransfer")
    public ResponseEntity<?> setUpRecurringTransfer
            (@RequestBody AcceptRecurringTransfer recurringTransfer){
        try{
            accountService.saveRecurringTransferRequest(recurringTransfer.getAccountNo(), recurringTransfer.getPayeeId(), recurringTransfer.getTransferDate(), recurringTransfer.getTransferAmount());
            return new ResponseEntity<>(new String("Recurring transfer request accepted"), HttpStatus.ACCEPTED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new String("Unable to accept request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/recurringPayment")
    public ResponseEntity<?> setUpRecurringPayment(@RequestBody AcceptRecurringPayment recurringPayment){
        try{
            accountService.saveRecurringPaymentRequest(recurringPayment.getAccountNo(), recurringPayment.getBillerId(), recurringPayment.getPaymentDate(), recurringPayment.getPaymentAmount());
            return new ResponseEntity<>(new String("Recurring payment request accepted"), HttpStatus.ACCEPTED);
        }
        catch(Exception ex){
            return new ResponseEntity<>(new String("Unable to accept request"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/addFunds")
    public ResponseEntity<?> depositFunds(@RequestParam("accountNo") Long accountNo, @RequestParam("amount") double depositAmount){
        ResponseEntity<?> response = null;
        try{
            int status = accountService.depositFundsIntoAccount(accountNo, depositAmount);
            if(status == 1){
                response = new ResponseEntity<>(new String("Funds deposited successfully"), HttpStatus.ACCEPTED);
            }
        }catch(Exception ex){
            response = new ResponseEntity<>(new String("Unable to deposit funds"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PutMapping("/withdrawFunds")
    public ResponseEntity<?> withdrawFunds(@RequestParam("accountNo") Long accountNo, @RequestParam("amount") double withdrawAmount){
        ResponseEntity<?> response = null;
        try{
            int status = accountService.withdrawFundsFromAccount(accountNo, withdrawAmount);
            if(status == 1){
                response = new ResponseEntity<>(new String("Funds Withdrawal successful"), HttpStatus.ACCEPTED);
            }
        }catch(Exception ex){
            response = new ResponseEntity<>(new String("Funds Withdrawal failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PutMapping("/addRefund")
    public ResponseEntity<?> addManualRefundFee(@RequestParam("accountNo") Long accountNo, @RequestParam("amount") double refundFee){
        ResponseEntity<?> response = null;
        try{
            int status = accountService.depositRefundFees(accountNo, refundFee);
            if(status == 1){
                response = new ResponseEntity<>(new String("Refund of $"+refundFee+" processed to Account: "+accountNo),HttpStatus.ACCEPTED);
            }
        }catch(Exception ex){
            response = new ResponseEntity<>(new String("Refund processing failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}