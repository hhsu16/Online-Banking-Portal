package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Biller;
import web.api.models.BillerUserRelation;
import web.api.models.User;
import web.api.repositories.BillerUserRelationRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class BillerUserRelationService {

    private final BillerUserRelationRepository billerUserRelationRepository;

    @Autowired
    public BillerUserRelationService(BillerUserRelationRepository billerUserRelationRepository){
        this.billerUserRelationRepository = billerUserRelationRepository;
    }

    public List<Biller> getUserBillers(User userObj){
        List<BillerUserRelation> relations =
                billerUserRelationRepository.findBillerUserRelationsByUserEquals(userObj);
        List<Biller> billersList = new ArrayList<Biller>();
        if(relations.size()!=0){
            for(BillerUserRelation relation : relations){
                billersList.add(relation.getBiller());
            }
        }
        return billersList;
    }

    public BillerUserRelation addNewBillerUserRelation(BillerUserRelation newBillerUserRelation){
        BillerUserRelation relation = null;
        if(newBillerUserRelation!=null){
            relation = billerUserRelationRepository.save(newBillerUserRelation);
        }
        return relation;
    }
}

