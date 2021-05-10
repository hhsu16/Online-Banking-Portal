package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.RecurringTransfer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface RecurringTransferRepository extends JpaRepository<RecurringTransfer, Long> {

    List<RecurringTransfer> findByTransferDate(LocalDate newDate);

    List<RecurringTransfer> findRecurringTransfersByAccount_AccountNo(Long accountNo);
}
