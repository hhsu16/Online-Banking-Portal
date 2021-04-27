package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import web.api.models.Biller;

import java.util.List;


public interface BillerRepository extends JpaRepository<Biller, Long> {

    public Biller getBillerByBillerIdEquals(Long billerId);

}
