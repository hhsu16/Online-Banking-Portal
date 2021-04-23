package web.api.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Account;
import web.api.models.Transaction;
import web.api.models.User;
import web.api.repositories.AccountRepository;
import web.api.repositories.TransactionRepository;

import java.util.List;

@Service
public class TransactionService
{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository)
    {
        this.transactionRepository=transactionRepository;
        this.accountRepository = accountRepository;
    }


    public int addTransaction(Transaction transaction)
    {

        try {

            //only debit
//            Account fromAccount = accountRepository.findAccount(transaction.getFromAccountId());
//            Account toAccount = accountRepository.findAccount(transaction.getToAccountId());
//            if(fromAccount.getAccountBalance() - transaction.getTransactionAmount() < 0)  {
//                return -1;
//            }
//            fromAccount.setAccountBalance(fromAccount.getAccountBalance() - transaction.getTransactionAmount());
//            toAccount.setAccountBalance(toAccount.getAccountBalance() + transaction.getTransactionAmount());
//            accountRepository.save(fromAccount);
//            accountRepository.save(toAccount);
            transactionRepository.save(transaction);
            System.out.println("Transaction succeeded");
            return 1;
        }  catch(Exception e) {
            System.out.println(e);
        }
        return 0;
    }


    public List<Transaction> getTransaction(Account account)
     {
        List<Transaction> allTransactions = transactionRepository.findTransactionsByAccountEquals(account);
        return allTransactions;
    }

}
