package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.Account;
import web.api.models.Transaction;

import java.util.ArrayList;
import java.util.Date;
import  java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>
{

    List<Transaction> findTransactionsByAccountEquals(Account account);

    ArrayList<Transaction> findTransactionsByAccount_AccountNo(Long accountNo);

    ArrayList<Transaction> findTransactionsByAccount_User_UserId(Long userId);

    ArrayList<Transaction> findTransactionsByAccount_AccountNoAndTransactionDateAfterAndTransactionDateBefore(Long accountNo, Date fromDate, Date endDate);

    Transaction findTopByOrderByTransactionIdDesc();

}

