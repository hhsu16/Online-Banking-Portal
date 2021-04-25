package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.exceptions.UserNotFoundException;
import web.api.models.Prospect;
import web.api.models.enums.ProspectStatus;
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
        return prospectRepository.findAllByProspectStatusEquals(ProspectStatus.PendingApproval);
    }

    public Prospect addProspect(Prospect prospectObj)
    {
        prospectObj.setProspectStatus(ProspectStatus.PendingApproval);
        return prospectRepository.save(prospectObj);
    }

    public Prospect updateProspectStatus(ProspectStatus prospectStatus, String email){
        Prospect getProspect = prospectRepository.findProspectByProspectStatusEqualsAndEmailId(prospectStatus, email);
        getProspect.setProspectStatus(ProspectStatus.Active);
        return prospectRepository.save(getProspect);
    }

}
