package web.api.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.Prospect;
import web.api.models.enums.ProspectStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect,Long> {

    List<Prospect> findAllByProspectStatusEquals(ProspectStatus status);

    Prospect findProspectByProspectStatusEqualsAndEmailId(ProspectStatus status, String email);

    Prospect findProspectByProspectId(Long prospectId);

}
