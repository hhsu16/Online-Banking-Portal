package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.exceptions.UserNotFoundException;
import web.api.models.Prospect;
import web.api.repositories.ProspectRepository;

import java.util.List;


@Service
public class ProspectService {
    private  final ProspectRepository prospectRepository;

    @Autowired
    public ProspectService(ProspectRepository prospectRepository) {
        this.prospectRepository = prospectRepository;
    }

    public List<Prospect> getProspects()
    {
        return prospectRepository.findAllByUserStatusEquals(false);
    }

    public Prospect addProspect(Prospect prospectObj)
    {
        return prospectRepository.save(prospectObj);
    }

    public void updateProspectStatus(boolean status, String emailId, String contact){
        Prospect p = prospectRepository.findProspectsByUserStatusEqualsAndEmailIdEqualsAndContactEquals(status, emailId, contact)
        .orElseThrow(()->new UserNotFoundException("Prospect not found with username : "+emailId));
        p.setUserStatus(true);
        prospectRepository.save(p);
    }
}
