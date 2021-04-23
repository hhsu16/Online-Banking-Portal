package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.models.Payee;
import web.api.repositories.PayeeRepository;

@Service
public class PayeeService {

    private final PayeeRepository payeeRepository;

    @Autowired
    public PayeeService(PayeeRepository payeeRepository){
        this.payeeRepository = payeeRepository;
    }

    public Payee addPayee(Payee newPayee){
        Payee p = null;
        if(newPayee!=null){
            p = payeeRepository.save(newPayee);
        }
        return p;
    }

    public Payee fetchPayee(Long payeeId){
        return payeeRepository.getPayeeByPayeeIdEquals(payeeId);
    }
}
