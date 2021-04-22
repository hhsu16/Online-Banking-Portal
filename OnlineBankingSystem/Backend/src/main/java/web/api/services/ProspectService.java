package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Prospect;
import web.api.models.User;
import web.api.repositories.ProspectRepository;
import web.api.repositories.UserRepository;

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
}
