package web.api.services;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.exceptions.UserNotFoundException;
import web.api.models.Prospect;
import web.api.models.enums.UserRole;
import web.api.models.User;
import web.api.models.enums.UserStatus;
import web.api.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User addUser(@NotNull Prospect prospect){

        User newUser = new User(
                prospect.getFirstName(),
                prospect.getLastName(),
                prospect.getEmailId(),
                prospect.getPassword(),
                prospect.getDateOfBirth(),
                prospect.getAddress(),
                prospect.getContact(),
                UserRole.Customer,
                UserStatus.ACTIVE);
        return userRepository.save(newUser);

    }

    public List<User> getCustomers(){
        return userRepository.findUsersByRoleEquals(UserRole.Customer);
    }

    public User getUserLogin(String emailId, String userPassword){
        User user = userRepository.findUserByEmailIdEqualsAndPasswordEquals(emailId, userPassword)
                .orElseThrow(()->new UserNotFoundException("User not found with emailId : "+emailId+" and password provided"));

        return user;
    }

    public User getUserFromUserId(Long uId){
        return userRepository.findUserByUserIdEquals(uId);
    }

}
