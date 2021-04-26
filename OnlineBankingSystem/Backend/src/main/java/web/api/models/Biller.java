package web.api.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Biller")
public class Biller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long billerId;
    @Column(nullable = false)
    private Long billerAccount;
    @Column(nullable = false)
    private String billerName;
    @Column(nullable = false)
    private String billerEmailId;
    @Column(nullable = false)
    private String billerContactNo;

    public Biller(){

    }

    public Biller(Long billerAccount, String billerName, String billerEmailId, String billerContactNo) {
        this.billerAccount = billerAccount;
        this.billerName = billerName;
        this.billerEmailId = billerEmailId;
        this.billerContactNo = billerContactNo;
    }

    public Long getBillerId() {
        return billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public Long getBillerAccount() {
        return billerAccount;
    }

    public void setBillerAccount(Long billerAccount) {
        this.billerAccount = billerAccount;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public String getBillerEmailId() {
        return billerEmailId;
    }

    public void setBillerEmailId(String billerEmailId) {
        this.billerEmailId = billerEmailId;
    }

    public String getBillerContactNo() {
        return billerContactNo;
    }

    public void setBillerContactNo(String billerContactNo) {
        this.billerContactNo = billerContactNo;
    }
}
