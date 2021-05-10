package web.api.models;

import javax.persistence.*;

@Entity(name = "DeleteCustomers")
public class DeleteCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long deleteUserId;

    public DeleteCustomer(){

    }

    public DeleteCustomer(Long deleteUserId) {
        this.deleteUserId = deleteUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeleteUserId() {
        return deleteUserId;
    }

    public void setDeleteUserId(Long deleteUserId) {
        this.deleteUserId = deleteUserId;
    }
}
