package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.PayeeUserRelation;
import web.api.models.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface PayeeUserRelationRepository extends JpaRepository<PayeeUserRelation, Long> {

    List<PayeeUserRelation> findPayeeUserRelationsByUserEquals(User user);

    ArrayList<PayeeUserRelation> findPayeeUserRelationsByUser_UserIdEquals(Long userId);
}
