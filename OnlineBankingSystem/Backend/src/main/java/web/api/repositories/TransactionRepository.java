package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.api.models.Account;
import web.api.models.Transaction;

import  java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{
//    @Query("select t from Transaction t,  Account a  where " +
//            "(a.accountNo = t.fromAccountId or a.accountNo = t.toAccountId) " +
//            " order by t.transactionDate desc")
    List<Transaction> findTransactionsByAccountEquals(Account account);

}

