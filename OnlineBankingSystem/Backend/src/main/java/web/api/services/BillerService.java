package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;
import web.api.models.Biller;
import web.api.repositories.BillerRepository;

import java.util.List;

@Service
public class BillerService {

    private final BillerRepository billerRepository;

    @Autowired
    public BillerService(BillerRepository billerRepository){
        this.billerRepository = billerRepository;
    }

    public List<Biller> getAllBillers(){
        return billerRepository.findAll();
    }

    public Biller fetchBiller(Long billerId){
        return billerRepository.getBillerByBillerIdEquals(billerId);
    }
}
