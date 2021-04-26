package web.api.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import web.api.exceptions.InsufficientFundsException;
import web.api.models.RecurringTransfer;
import web.api.repositories.RecurringTransferRepository;
import web.api.services.AccountService;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class RecurringScheduler {

    private final RecurringTransferRepository recurringTransferRepository;
    private final AccountService accountService;

    @Autowired
    public RecurringScheduler(AccountService accountService, RecurringTransferRepository recurringTransferRepository){
        this.recurringTransferRepository = recurringTransferRepository;
        this.accountService = accountService;
    }

    @Scheduled(cron = "0 51 0 * * *")
    public void setUpRecurringTransfer(){
        List<RecurringTransfer> lstTransfers = recurringTransferRepository.findByTransferDate(LocalDate.now());

        lstTransfers.stream().forEach(f->{
            Long userAccountNo = f.getAccount().getAccountNo();
            Long payeeId = f.getPayee().getPayeeId();
            double transferAmount = f.getTransferAmount();
            try{
                accountService.transferFundsToPayees(userAccountNo, payeeId, transferAmount);
            }
            catch (InsufficientFundsException ex){

            }

        });
    }
}
