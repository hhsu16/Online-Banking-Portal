package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Biller;
import web.api.repositories.BillerRepository;

@Service
public class BillerService {

    private final BillerRepository billerRepository;

    @Autowired
    public BillerService(BillerRepository billerRepository){
        this.billerRepository = billerRepository;
    }

    public Biller addBiller(Biller newBiller){
        Biller p = null;
        if(newBiller!=null){
            p = billerRepository.save(newBiller);
        }
        return p;
    }

    public Biller fetchBiller(Long billerId){
        return billerRepository.getBillerByBillerIdEquals(billerId);
    }
}
