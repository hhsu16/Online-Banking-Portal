package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.DeleteCustomer;

@Repository
public interface DeleteCustomerRepository extends JpaRepository<DeleteCustomer, Long> {

}
