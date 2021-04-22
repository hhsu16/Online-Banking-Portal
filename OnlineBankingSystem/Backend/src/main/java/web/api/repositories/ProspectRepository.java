package web.api.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import web.api.models.Prospect;
import web.api.models.Role;
import web.api.models.User;
import java.util.List;

public interface ProspectRepository extends JpaRepository<Prospect,Long> {
    List<Prospect> findAllByUserStatusEquals(boolean status);
//    User save(User user) throws Exception;
//    Prospect save(Prospect  prospect) throws Exception;
//    Role getUserRole(String email, String password);
}
