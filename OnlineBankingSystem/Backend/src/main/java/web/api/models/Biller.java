package web.api.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Billers")
public class Biller implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long billerId;
    @Column(nullable = false)
    private String billerName;

    public Biller(){

    }

    public Biller(String billerName) {
        this.billerName = billerName;
    }

    public Long getBillerId() {
        return billerId;
    }

    public void setBillerId(Long billerId) {
        this.billerId = billerId;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }
}
