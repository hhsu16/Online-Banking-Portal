package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.api.models.Role;
import web.api.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByRoleEquals(Role role);

    User findUserByUserIdEquals(Long userId);

    Optional<User> findUserByEmailIdEqualsAndPasswordEquals(String emailId, String Password);

}
