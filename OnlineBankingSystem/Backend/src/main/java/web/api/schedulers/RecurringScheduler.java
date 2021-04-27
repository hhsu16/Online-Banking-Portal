package web.api.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import web.api.exceptions.InsufficientFundsException;
import web.api.models.RecurringPayment;
import web.api.models.RecurringTransfer;
import web.api.repositories.RecurringPaymentRepository;
import web.api.repositories.RecurringTransferRepository;
import web.api.services.AccountService;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class RecurringScheduler {

    private final RecurringTransferRepository recurringTransferRepository;
    private final RecurringPaymentRepository recurringPaymentRepository;
    private final AccountService accountService;

    @Autowired
    public RecurringScheduler(AccountService accountService,RecurringPaymentRepository recurringPaymentRepository, RecurringTransferRepository recurringTransferRepository){
        this.recurringTransferRepository = recurringTransferRepository;
        this.recurringPaymentRepository = recurringPaymentRepository;
        this.accountService = accountService;
    }

    @Scheduled(cron = "0 10 0 * * *")
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

    @Scheduled(cron = "0 50 0 * * *")
    public void setUpRecurringBillPayment(){
        List<RecurringPayment> lstPayments = recurringPaymentRepository.findByPaymentDate(LocalDate.now());
        lstPayments.stream().forEach(
                f->{
                    Long userAccountNo = f.getAccount().getAccountNo();
                    Long billerId = f.getBiller().getBillerId();
                    double transferAmount = f.getPaymentAmount();
                    try{
                        accountService.billPaymentToBillers(userAccountNo, billerId, transferAmount);
                    }
                    catch(InsufficientFundsException ex){

                    }
                }
        );
    }
}
