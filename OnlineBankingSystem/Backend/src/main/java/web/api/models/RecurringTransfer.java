package web.api.models;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import web.api.models.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity(name="RecurringTransfers")
public class RecurringTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @ManyToOne
    private Account account;
    @OneToOne
    private Payee payee;
    @Column(nullable = false, updatable = false)
    private LocalDate transferDate;
    private double transferAmount;
    @CreatedDate
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public RecurringTransfer(){

    }

    public RecurringTransfer(Account account, Payee payee, LocalDate transferDate, double transferAmount, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.account = account;
        this.payee = payee;
        this.transferDate = transferDate;
        this.transferAmount = transferAmount;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringTransfer that = (RecurringTransfer) o;
        return Double.compare(that.transferAmount, transferAmount) == 0 && Objects.equals(id, that.id) && Objects.equals(account, that.account) && Objects.equals(payee, that.payee) && Objects.equals(transferDate, that.transferDate) && Objects.equals(createdOn, that.createdOn) && Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, payee, transferDate, transferAmount, createdOn, updatedOn);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
