package web.api.services;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.exceptions.UserNotFoundException;
import web.api.models.Prospect;
import web.api.models.Role;
import web.api.models.User;
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
                Role.CUSTOMER);
        return userRepository.save(newUser);

    }

    public List<User> getCustomers(){
        return userRepository.findUsersByRoleEquals(Role.CUSTOMER);
    }

    public User getUserLogin(String emailId, String userPassword){
        User user = userRepository.findUserByEmailIdEqualsAndPasswordEquals(emailId, userPassword)
                .orElseThrow(()->new UserNotFoundException("User not found with emailId : "+emailId+" and password provided"));

        return user;
    }


}
