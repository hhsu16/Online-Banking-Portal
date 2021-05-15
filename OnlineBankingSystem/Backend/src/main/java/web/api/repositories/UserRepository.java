package web.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.api.models.enums.UserRole;
import web.api.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByRoleEquals(UserRole role);

    User findUserByUserIdEquals(Long userId);

    Optional<User> findUserByEmailId(String emailId);

    User findUserByEmailIdEquals(String emailId);

}
