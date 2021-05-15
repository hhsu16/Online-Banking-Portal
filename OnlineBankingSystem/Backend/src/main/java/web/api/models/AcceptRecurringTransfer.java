package web.api.models;

import java.time.LocalDate;

public class AcceptRecurringTransfer {

    private Long accountNo;
    private Long payeeId;
    private LocalDate transferDate;
    private double transferAmount;

    public AcceptRecurringTransfer(Long accountNo, Long payeeId, LocalDate transferDate, double transferAmount) {
        this.accountNo = accountNo;
        this.payeeId = payeeId;
        this.transferDate = transferDate;
        this.transferAmount = transferAmount;
    }

    public AcceptRecurringTransfer() {
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
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
}
