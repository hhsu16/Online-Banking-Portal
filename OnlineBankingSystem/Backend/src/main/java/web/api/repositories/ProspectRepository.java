package web.api.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.Prospect;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect,Long> {
    List<Prospect> findAllByUserStatusEquals(boolean status);

    Optional<Prospect> findProspectsByUserStatusEqualsAndEmailIdEqualsAndContactEquals(boolean status, String email, String contact);
    //    User save(User user) throws Exception;
    //    Prospect save(Prospect  prospect) throws Exception;
    //    Role getUserRole(String email, String password);
}
