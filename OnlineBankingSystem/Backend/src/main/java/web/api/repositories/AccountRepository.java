package web.api.repositories;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public List<Account> findAccountsByUser_UserIdEquals(Long userId);

    public List<Account> findAccountsByAccountStatusEquals(boolean status);

    public @Nullable Account findAccountByAccountNoEquals(Long accountNo);

}