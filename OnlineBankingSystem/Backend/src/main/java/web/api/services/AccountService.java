package web.api.services;

import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.exceptions.InsufficientFundsException;
import web.api.models.*;
import web.api.models.enums.TransactionStatus;
import web.api.models.enums.TransactionType;
import web.api.repositories.AccountRepository;
import web.api.repositories.RecurringTransferRepository;
import web.api.repositories.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    public AccountService(RecurringTransferRepository recurringTransferRepository, AccountRepository accountRepository, PayeeService payeeService, TransactionService transactionService, TransactionRepository transactionRepository, BillerService billerService){
        this.accountRepository = accountRepository;
        this.payeeService = payeeService;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.recurringTransferRepository = recurringTransferRepository;
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

    public int transferFundsToBillers(Long accountNo, Long billerId, double transferAmount) throws InsufficientFundsException {
        int result = 0;
        Account userAccount = getAccount(accountNo);
        Biller billerObj = billerService.fetchBiller(billerId);
        Account billerAccount = getAccount(billerObj.getBillerAccount());
        if(transferAmount <= userAccount.getAccountBalance()){
            if(billerAccount!=null){
            Long transactId = transactionService.getLatestTransactionId();
            transactId++;
            String message = "Amount transfer of $"+transferAmount+" to "+billerObj.getBillerName();
            userAccount.setAccountBalance(userAccount.getAccountBalance()-transferAmount);
            accountRepository.save(userAccount);
            transactionService.addTransaction(new Transaction(transactId, new Date(), message, TransactionType.DEBIT, transferAmount, TransactionStatus.SUCCESS, userAccount));
            //if(billerAccount!=null){
                billerAccount.setAccountBalance(billerAccount.getAccountBalance()+transferAmount);
                accountRepository.save(billerAccount);
                transactionService.addTransaction(
                        new Transaction(transactId, new Date(), message, TransactionType.CREDIT, transferAmount, TransactionStatus.SUCCESS, billerAccount));
            }
            result = 1;

        }
        else{
            result = -99;
            throw new InsufficientFundsException("Insufficient Funds");
        }
        return result;
    }
}

