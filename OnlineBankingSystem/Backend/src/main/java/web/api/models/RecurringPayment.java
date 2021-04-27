package web.api.models;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "RecurringPayments")
public class RecurringPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @ManyToOne
    private Account account;
    @OneToOne
    private Biller biller;
    @Column(nullable = false, updatable = false)
    private LocalDate paymentDate;
    private double paymentAmount;
    @CreatedDate
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public RecurringPayment(){

    }

    public RecurringPayment(Account account, Biller biller, LocalDate paymentDate, double paymentAmount, LocalDateTime createdOn, LocalDateTime updatedOn) {
        this.account = account;
        this.biller = biller;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
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

    public Biller getBiller() {
        return biller;
    }

    public void setBiller(Biller biller) {
        this.biller = biller;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecurringPayment that = (RecurringPayment) o;
        return Double.compare(that.paymentAmount, paymentAmount) == 0 && Objects.equals(id, that.id) && Objects.equals(account, that.account) && Objects.equals(biller, that.biller) && Objects.equals(paymentDate, that.paymentDate) && Objects.equals(createdOn, that.createdOn) && Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, biller, paymentDate, paymentAmount, createdOn, updatedOn);
    }
}
