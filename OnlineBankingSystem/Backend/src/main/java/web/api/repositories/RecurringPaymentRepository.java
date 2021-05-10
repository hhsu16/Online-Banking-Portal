package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.RecurringPayment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecurringPaymentRepository extends JpaRepository<RecurringPayment, Long> {

    List<RecurringPayment> findByPaymentDate(LocalDate newDate);

    List<RecurringPayment> findRecurringPaymentsByAccount_AccountNo(Long accountNo);

}
