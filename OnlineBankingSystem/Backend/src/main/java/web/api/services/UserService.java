package web.api.services;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.api.models.Prospect;
import web.api.models.enums.UserRole;
import web.api.models.User;
import web.api.models.enums.UserStatus;
import web.api.repositories.UserRepository;
import web.api.security.CustomUserDetails;

import java.util.List;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        User userObj = userRepository.findUserByEmailId(emailId)
                .orElseThrow(()->new UsernameNotFoundException("User not found with emailId : "+emailId));
        return new CustomUserDetails(userObj);
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
                UserRole.CUSTOMER,
                UserStatus.ACTIVE);
        return userRepository.save(newUser);

    }

    public List<User> getCustomers(){
        return userRepository.findUsersByRoleEquals(UserRole.CUSTOMER);
    }

    public User getUserFromUserId(Long uId){
        return userRepository.findUserByUserIdEquals(uId);
    }

    public boolean changePassword(User userObj, String password){
        boolean status = false;
        try{
            userObj.setPassword(password);
            userRepository.save(userObj);
            status = true;
        }catch (Exception ex){
            new Exception("Password is not updated", ex);
        }

        return status;
    }

    public void changeCustomerStatus(Long userId){
        User userObj = userRepository.findUserByUserIdEqualsAndRoleEquals(userId, UserRole.CUSTOMER);
        userObj.setUserStatus(UserStatus.INACTIVE);
        userRepository.save(userObj);
    }

}
