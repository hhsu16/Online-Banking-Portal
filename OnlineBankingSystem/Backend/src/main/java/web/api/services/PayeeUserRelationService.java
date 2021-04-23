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
}
