package web.api.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name="Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long transactionId; //unique id for every transaction. auto incremented
    @Column(nullable = false)
    private Date transactionDate;//date of transaction.
    private String description;//optional.
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;//credit/debit
    private double transactionAmount;//>0
    @OneToOne
    private Account account;
    //@Column(nullable = false)
//    private Long fromAccountId;//user can have checking an savings account.
//    @Column(nullable = false)
//    private Long toAccountId;// this facilitates transactions between self accounts.

    public Transaction() {
    }

    public Transaction(Date transactionDate, String description, TransactionType transactionType, double transactionAmount) {
        this.transactionDate = transactionDate;
        this.description = description;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public TransactionType getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

//    public Long getFromAccountId() {
//        return fromAccountId;
//    }
//
//    public void setFromAccountId(Long fromAccountId) {
//        this.fromAccountId = fromAccountId;
//    }
//
//    public Long getToAccountId() {
//        return toAccountId;
//    }
//
//    public void setToAccountId (Long toAccountId) {
//        this.toAccountId = toAccountId;
//    }

}
