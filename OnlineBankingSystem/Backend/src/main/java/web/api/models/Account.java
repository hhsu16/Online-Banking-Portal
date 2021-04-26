package web.api.models;



import web.api.models.enums.AccountType;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Accounts")
public class Account implements Serializable {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNo;
    private boolean accountStatus;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private double accountBalance;

    @ManyToOne
    private User user;

    public Account(){

    }

    public Account(User user, boolean accountStatus, AccountType accountType, double accountBalance){
        this.user = user;
        this.accountStatus = accountStatus;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public boolean getAccountStatus(){
        return this.accountStatus;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

}
