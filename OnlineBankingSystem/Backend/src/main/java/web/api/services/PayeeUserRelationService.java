package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Payee;
import web.api.models.PayeeUserRelation;
import web.api.models.User;
import web.api.repositories.PayeeUserRelationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayeeUserRelationService {

    private final PayeeUserRelationRepository payeeUserRelationRepository;

    @Autowired
    public PayeeUserRelationService(PayeeUserRelationRepository payeeUserRelationRepository){
        this.payeeUserRelationRepository = payeeUserRelationRepository;
    }

    public List<Payee> getUserPayees(User userObj){
        List<PayeeUserRelation> relations =
                payeeUserRelationRepository.findPayeeUserRelationsByUserEquals(userObj);
        List<Payee> payeesList = new ArrayList<Payee>();
        if(relations.size()!=0){
            for(PayeeUserRelation relation : relations){
                payeesList.add(relation.getPayee());
            }
        }
        return payeesList;
    }

    public PayeeUserRelation addNewPayeeUserRelation(PayeeUserRelation newPayeeUserRelation){
        PayeeUserRelation relation = null;
        if(newPayeeUserRelation!=null){
            relation = payeeUserRelationRepository.save(newPayeeUserRelation);
        }
        return relation;
    }

    public ArrayList<PayeeUserRelation> listOfPayeeUserRelations(Long userId){
        ArrayList<PayeeUserRelation> userRelations = new ArrayList<>();
        try{
            userRelations = payeeUserRelationRepository
                    .findPayeeUserRelationsByUser_UserIdEquals(userId);
        }
        catch(Exception e){
            new Exception("Error during finding User Registered payees", e);
        }
        return userRelations;
    }

    public boolean deleteListOfUserPayeeRelations(ArrayList<PayeeUserRelation> relations){
        boolean status = false;
        try{
            payeeUserRelationRepository.deleteAll(relations);
            status = true;
        }
        catch(Exception e){
            new Exception("User registered payees are not deleted", e);
        }
        return status;
    }
}
