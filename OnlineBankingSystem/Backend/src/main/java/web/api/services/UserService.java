package web.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.api.exceptions.UserNotFoundException;
import web.api.models.Role;
import web.api.models.User;
import web.api.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public boolean addUser(User userObj){
        boolean status;

        if(userObj!=null){
            userRepository.save(userObj);
            status = true;
        }
        else{
            status = false;
        }

        return status;
    }

    public List<User> getCustomers(){
        return userRepository.findUsersByRoleEquals(Role.CUSTOMER);
    }

    public Role getUserRole(String emailId, String userPassword){
        User user = userRepository.findUserByEmailIdEqualsAndPasswordEquals(emailId, userPassword)
                .orElseThrow(()->new UserNotFoundException("User not found with emailId : "+emailId+" and password provided"));

        return user.getRole();
    }
}
