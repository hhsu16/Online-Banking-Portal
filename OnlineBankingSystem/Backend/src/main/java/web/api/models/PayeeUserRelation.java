package web.api.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "PayeeUserRelations")
public class PayeeUserRelation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long relationId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Payee payee;


    public PayeeUserRelation(){

    }

    public PayeeUserRelation(User user, Payee payee){
        this.user = user;
        this.payee = payee;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }
}
