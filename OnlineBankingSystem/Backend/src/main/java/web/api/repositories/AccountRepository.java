package web.api.repositories;

import com.sun.istack.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.api.models.Account;
import web.api.models.User;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public List<Account> findAccountsByAccountStatusEquals(boolean status);

    public List<Account> findAccountsByUser_UserIdEquals(Long userId);

    public @Nullable Account findAccountByAccountNoEquals(Long accountNo);

}