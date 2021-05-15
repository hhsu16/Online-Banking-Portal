package web.api.services;

import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.exceptions.InsufficientFundsException;
import web.api.models.*;
import web.api.models.enums.TransactionStatus;
import web.api.models.enums.TransactionType;
import web.api.repositories.AccountRepository;
import web.api.repositories.RecurringPaymentRepository;
import web.api.repositories.RecurringTransferRepository;
import web.api.repositories.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final PayeeService payeeService;
    private final BillerService billerService;
    private final TransactionService transactionService;
    private final RecurringTransferRepository recurringTransferRepository;
    private final RecurringPaymentRepository recurringPaymentRepository;

    @Autowired
    public AccountService(RecurringPaymentRepository recurringPaymentRepository, RecurringTransferRepository recurringTransferRepository, AccountRepository accountRepository, PayeeService payeeService, TransactionService transactionService, TransactionRepository transactionRepository, BillerService billerService){
        this.accountRepository = accountRepository;
        this.payeeService = payeeService;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.recurringTransferRepository = recurringTransferRepository;
        this.recurringPaymentRepository = recurringPaymentRepository;
        this.billerService = billerService;
    }

    public Account addNewAccount(Account accountObj){
        return accountRepository.save(accountObj);
    }

    public List<Account> getUserAccounts(Long userId){
        return accountRepository.findAccountsByUser_UserIdEquals(userId);
    }

    public void saveRecurringTransferRequest(Long accountNo, Long payeeId, LocalDate transferDate, double transferAmount){
        Account account = accountRepository.findAccountByAccountNoEquals(accountNo);
        Payee payee = payeeService.fetchPayee(payeeId);
        recurringTransferRepository.save(new RecurringTransfer(account, payee, transferDate, transferAmount,LocalDateTime.now(), LocalDateTime.now()));
    }

    public void saveRecurringPaymentRequest(Long accountNo, Long billerId, LocalDate paymentDate, double paymentAmount){
        Account account = accountRepository.findAccountByAccountNoEquals(accountNo);
        Biller biller = billerService.fetchBiller(billerId);
        recurringPaymentRepository.save(new RecurringPayment(account, biller, paymentDate, paymentAmount, LocalDateTime.now(), LocalDateTime.now()));

    }

    public int depositFundsIntoAccount(Long accountNo, double depositAmount){
        int status = 0;
        Long transactId = transactionService.getLatestTransactionId();
        String message = "Amount of $"+depositAmount+" deposited";
        Account userAccount = null;
        try{
            userAccount = accountRepository.findAccountByAccountNoEquals(accountNo);
            userAccount.setAccountBalance(userAccount.getAccountBalance()+depositAmount);
            accountRepository.save(userAccount);
            transactionService.addTransaction(new Transaction(++transactId, new Date(), message, TransactionType.CREDIT, depositAmount, TransactionStatus.SUCCESS, userAccount));
            status = 1;
        }
        catch(Exception e){
            transactionService.addTransaction(new Transaction(++transactId, new Date(), message, TransactionType.CREDIT, depositAmount, TransactionStatus.FAILED, userAccount));
            status = -99;
        }
        return status;
    }

    public int depositRefundFees(Long accountNo, double depositAmount){
        int status = 0;
        Long transactId = transactionService.getLatestTransactionId();
        transactId++;
        String message = "Refund of $"+depositAmount+" deposited";
        Account userAccount = null;
        try{
            userAccount = accountRepository.findAccountByAccountNoEquals(accountNo);
            userAccount.setAccountBalance(userAccount.getAccountBalance()+depositAmount);
            accountRepository.save(userAccount);
            transactionService.addTransaction(new Transaction(transactId, new Date(), message, TransactionType.CREDIT, depositAmount, TransactionStatus.SUCCESS, userAccount));
            status = 1;
        }
        catch(Exception e){
            transactionService.addTransaction(new Transaction(transactId, new Date(), message, TransactionType.CREDIT, depositAmount, TransactionStatus.FAILED, userAccount));
            status = -99;
        }
        return status;
    }

    public int withdrawFundsFromAccount(Long accountNo, double withdrawAmount){
        int status = 0;
        Long transactId = transactionService.getLatestTransactionId();
        transactId++;
        String message = "Withdrawn an amount of $"+withdrawAmount;
        Account userAccount = null;
        try{
            userAccount = accountRepository.findAccountByAccountNoEquals(accountNo);
            userAccount.setAccountBalance(userAccount.getAccountBalance()-withdrawAmount);
            accountRepository.save(userAccount);
            transactId = transactionService.getLatestTransactionId();
            transactId++;
            transactionService.addTransaction(new Transaction(transactId, new Date(), message, TransactionType.DEBIT, withdrawAmount, TransactionStatus.SUCCESS, userAccount));
            status = 1;
        }catch(Exception e){
            transactionService.addTransaction(new Transaction(transactId, new Date(), message, TransactionType.DEBIT, withdrawAmount, TransactionStatus.FAILED, userAccount));
            status = -99;
        }
        return status;
    }

    @Nullable
    public Account getAccount(Long accountNo){
        return accountRepository.findAccountByAccountNoEquals(accountNo);
    }

    public int transferFundsToPayees(Long accountNo, Long payeeId, double transferAmount) throws InsufficientFundsException {
        int result = 0;
        Account userAccount = getAccount(accountNo);
        Payee payeeObj = payeeService.fetchPayee(payeeId);
        Account payeeAccount = getAccount(payeeObj.getPayeeAccount());
        if(transferAmount <= userAccount.getAccountBalance()){
            Long transactId = transactionService.getLatestTransactionId();
            transactId++;
            String message = "Amount transfer of $"+transferAmount+" from "+userAccount.getUser().getFirstName()+" to "+payeeObj.getPayeeName();
            userAccount.setAccountBalance(userAccount.getAccountBalance()-transferAmount);
            accountRepository.save(userAccount);
            transactionService.addTransaction(new Transaction(transactId, new Date(), message, TransactionType.DEBIT, transferAmount, TransactionStatus.SUCCESS, userAccount));
            if(payeeAccount!=null){
                payeeAccount.setAccountBalance(payeeAccount.getAccountBalance()+transferAmount);
                accountRepository.save(payeeAccount);
                transactionService.addTransaction(
                        new Transaction(transactId, new Date(), message, TransactionType.CREDIT, transferAmount, TransactionStatus.SUCCESS, payeeAccount));
            }
            result = 1;

        }
        else{
            result = -99;
            throw new InsufficientFundsException("Insufficient Funds");
        }
        return result;
    }

    public int billPaymentToBillers(Long accountNo, Long billerId, double paymentAmount) throws InsufficientFundsException {
        int result = 0;
        Account userAccount = getAccount(accountNo);
        Biller biller = billerService.fetchBiller(billerId);
        if(paymentAmount <= userAccount.getAccountBalance()){
            Long transactId = transactionService.getLatestTransactionId();
            transactId++;
            String message = "Bill payment of $"+paymentAmount+" from"+userAccount.getUser().getFirstName()+" to "+biller.getBillerName();
            userAccount.setAccountBalance(userAccount.getAccountBalance()-paymentAmount);
            accountRepository.save(userAccount);
            transactionService.addTransaction(new Transaction(transactId, new Date(), message, TransactionType.DEBIT, paymentAmount, TransactionStatus.SUCCESS, userAccount));
            result = 1;
        }
        else{
            result = -99;
            throw new InsufficientFundsException("Insufficient Funds");
        }
        return result;
    }

    public List<Account> getCustomerAccounts(){
        return accountRepository.findAccountsByAccountStatusEquals(true);
    }

    public ArrayList<RecurringPayment> listOfAccountPayments(ArrayList<Long> accountNos){
        ArrayList<RecurringPayment> payments = new ArrayList<>();
        for (Long account:accountNos) {
            payments.addAll(recurringPaymentRepository.findRecurringPaymentsByAccount_AccountNo(account));
        }
        return payments;
    }

    public ArrayList<RecurringTransfer> listOfAccountTransfers(List<Long> accountNos){
        ArrayList<RecurringTransfer> transfers = new ArrayList<>();
        for(Long account : accountNos){
            transfers.addAll(recurringTransferRepository.findRecurringTransfersByAccount_AccountNo(account));
        }
        return transfers;
    }

    public boolean deleteAccountTransfers(ArrayList<RecurringTransfer> transfers){
        boolean status = false;
        try{
            recurringTransferRepository.deleteAll(transfers);
            status = true;
        }
        catch(Exception ex) {
            new Exception("Transfers deletion failed", ex);
        }
        return status;
    }

    public boolean deleteAccountPayments(ArrayList<RecurringPayment> payments){
        boolean status = false;
        try{
            recurringPaymentRepository.deleteAll(payments);
            status = true;
        }
        catch(Exception ex) {
            new Exception("Payments deletion failed", ex);
        }
        return status;
    }

    public boolean deleteUserAccounts(ArrayList<Account> accounts){
        boolean status = false;
        try{
            accountRepository.deleteAll(accounts);
            status = true;
        }
        catch(Exception ex) {
            new Exception("User accounts deletion failed", ex);
        }
        return status;

    }
}

