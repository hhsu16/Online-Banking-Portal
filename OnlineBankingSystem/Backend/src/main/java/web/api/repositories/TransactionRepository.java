package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.Account;
import web.api.models.Transaction;

import  java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{

    List<Transaction> findTransactionsByAccountEquals(Account account);

    List<Transaction> findTransactionsByAccount_AccountNo(Long accountNo);

    Transaction findTopByOrderByTransactionId();

}

