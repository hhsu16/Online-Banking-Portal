package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Prospect;
import web.api.models.enums.ProspectStatus;
import web.api.repositories.ProspectRepository;

import java.util.ArrayList;
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
        return  prospectRepository.findAllByProspectStatusEquals(ProspectStatus.PENDING_APPROVAL);
    }

    public Prospect findProspectById(Long prospectId){
        return prospectRepository.findProspectByProspectId(prospectId);
    }

    public Prospect addProspect(Prospect prospectObj)
    {
        prospectObj.setProspectStatus(ProspectStatus.PENDING_APPROVAL);
        return prospectRepository.save(prospectObj);
    }

    public Prospect updateProspectStatus(ProspectStatus prospectStatus, String email){
        Prospect getProspect = prospectRepository.findProspectByProspectStatusEqualsAndEmailId(prospectStatus, email);
        getProspect.setProspectStatus(ProspectStatus.ACTIVE);
        return prospectRepository.save(getProspect);
    }

    public boolean rejectProspect(Long prospectId){
        boolean status = false;
        try{
            Prospect prospectObj = findProspectById(prospectId);
            prospectObj.setProspectStatus(ProspectStatus.REJECT);
            prospectRepository.save(prospectObj);
            status = true;
        }
        catch (Exception e){
            status = false;
        }
        return status;
    }


}
