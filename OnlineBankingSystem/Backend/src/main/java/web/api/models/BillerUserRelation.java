package web.api.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "BillerUserRelations")
public class BillerUserRelation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long relationId;
    @OneToOne
    private User user;
    @OneToOne
    private Biller biller;


    public BillerUserRelation(){

    }

    public BillerUserRelation(User user, Biller biller){
        this.user = user;
        this.biller = biller;
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

    public Biller getBiller() {
        return biller;
    }

    public void setBiller(Biller biller) {
        this.biller = biller;
    }
}
