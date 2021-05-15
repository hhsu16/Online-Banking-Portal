package web.api.models;

import java.time.LocalDate;

public class AcceptRecurringPayment {

    private Long accountNo;
    private Long billerId;
    private LocalDate paymentDate;
    private double paymentAmount;

    public AcceptRecurringPayment() {
    }

    public AcceptRecurringPayment(Long accountNo, Long billerId, LocalDate paymentDate, double paymentAmount) {
        this.accountNo = accountNo;
        this.billerId = billerId;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public Long getBillerId() {
        return billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
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
}
