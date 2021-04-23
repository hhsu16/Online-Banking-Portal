package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.Payee;

import java.util.List;

@Repository
public interface PayeeRepository extends JpaRepository<Payee, Long> {

    Payee getPayeeByPayeeIdEquals(Long payeeId);

}
