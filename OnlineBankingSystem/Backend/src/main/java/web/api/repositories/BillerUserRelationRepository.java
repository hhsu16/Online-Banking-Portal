package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.BillerUserRelation;
import web.api.models.User;
import java.util.List;


@Repository
public interface BillerUserRelationRepository extends JpaRepository<BillerUserRelation, Long> {

    List<BillerUserRelation> findBillerUserRelationsByUserEquals(User user);
}
