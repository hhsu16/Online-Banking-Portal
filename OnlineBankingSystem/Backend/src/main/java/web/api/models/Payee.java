package web.api.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Payees")
public class Payee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long payeeId;
    @Column(nullable = false)
    private Long payeeAccount;
    @Column(nullable = false)
    private String payeeName;
    @Column(nullable = false)
    private String payeeEmailId;
    @Column(nullable = false)
    private String payeeContactNo;

    public Payee(){

    }

    public Payee(Long payeeAccount, String payeeName, String payeeEmailId, String payeeContactNo) {
        this.payeeAccount = payeeAccount;
        this.payeeName = payeeName;
        this.payeeEmailId = payeeEmailId;
        this.payeeContactNo = payeeContactNo;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(Long payeeId) {
        this.payeeId = payeeId;
    }

    public Long getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(Long payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayeeEmailId() {
        return payeeEmailId;
    }

    public void setPayeeEmailId(String payeeEmailId) {
        this.payeeEmailId = payeeEmailId;
    }

    public String getPayeeContactNo() {
        return payeeContactNo;
    }

    public void setPayeeContactNo(String payeeContactNo) {
        this.payeeContactNo = payeeContactNo;
    }
}
