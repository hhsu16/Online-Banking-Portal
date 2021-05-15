package web.api.services;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.api.models.*;
import web.api.models.enums.UserRole;
import web.api.models.enums.UserStatus;
import web.api.repositories.AccountRepository;
import web.api.repositories.DeleteCustomerRepository;
import web.api.repositories.PayeeUserRelationRepository;
import web.api.repositories.UserRepository;
import web.api.security.CustomUserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final DeleteCustomerRepository deleteCustomerRepository;
    private final PayeeUserRelationService payeeUserRelationService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public UserService(DeleteCustomerRepository deleteCustomerRepository, TransactionService transactionService, UserRepository userRepository, AccountRepository accountRepository, PayeeUserRelationService payeeUserRelationService, AccountService accountService){
        this.userRepository=userRepository;
        this.accountRepository = accountRepository;
        this.payeeUserRelationService = payeeUserRelationService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.deleteCustomerRepository = deleteCustomerRepository;
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

    public User getUserByEmailId(String emailId) throws UsernameNotFoundException{
        User userObj = userRepository.findUserByEmailId(emailId)
                .orElseThrow(()->new UsernameNotFoundException("User not found with emailId "+emailId));
        return userObj;
    }

    public void deleteUser(User userObj){
        userRepository.delete(userObj);
    }

    public double deleteCustomerAccount(Long userId){
        ArrayList<Long> accountNos = new ArrayList<>();
        boolean d1, d2, d3, d4, d5;
        double remainingBalance = 0.0;
        try{
            User userObj = userRepository.findUserByUserIdEquals(userId);
            ArrayList<Account> accounts = accountRepository.findAccountsByUser_UserIdEquals(userId);
            for (Account account : accounts) {
                accountNos.add(account.getAccountNo());
                remainingBalance += account.getAccountBalance();
                account.setAccountBalance(0.0);
            }
            ArrayList<PayeeUserRelation> relations = payeeUserRelationService.listOfPayeeUserRelations(userId);
            ArrayList<Transaction> userTransactions = transactionService.listOfAllUserAccountTransactions(accountNos);
            ArrayList<RecurringPayment> payments = accountService.listOfAccountPayments(accountNos);
            ArrayList<RecurringTransfer> transfers = accountService.listOfAccountTransfers(accountNos);
            d1 = payeeUserRelationService.deleteListOfUserPayeeRelations(relations);
            d2 = accountService.deleteAccountPayments(payments);
            d3 = accountService.deleteAccountTransfers(transfers);
            d4 = transactionService.deleteListOfTransactions(userTransactions);
            d5 = accountService.deleteUserAccounts(accounts);
            if(d1 && d2 && d3 && d4 && d5){
                deleteUser(userObj);
            }
            else{
                throw new Exception("Unable to close Customer Accounts");
            }

        }catch(Exception ex){
            remainingBalance = -999;
            new Exception("Unknown error", ex);
        }
        return remainingBalance;
    }

    public boolean takeCustomerCloseRequest(Long userId){
        boolean status = false;
        try{
            DeleteCustomer dc = new DeleteCustomer(userId);
            deleteCustomerRepository.save(dc);
            status = true;
        }
        catch(Exception ex){
            status = false;
            new Exception("Error occured", ex);
        }
        return status;
    }

    public boolean checkEmailIdIfExists(String emailId){
        boolean isEmailAvailable = false;
        User userObj = userRepository.findUserByEmailIdEquals(emailId);
        if(userObj == null){
            isEmailAvailable = false;
        }
        else{
            isEmailAvailable = true;
        }
        return isEmailAvailable;
    }

}
