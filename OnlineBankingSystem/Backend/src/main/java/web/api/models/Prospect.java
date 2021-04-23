package web.api.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="Prospects")
public class Prospect implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long prospectId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String emailId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Date dateOfBirth;
    @Column(nullable = false, length = 10)
    private String contact;
    private String address;
    private boolean userStatus = false;
    @Enumerated(EnumType.STRING)
    private AccountType accountTypeWanted;


    public Prospect(){

    }

    public Prospect(String firstName, String lastName, String emailId, String password, Date dateOfBirth, String contact, String address, AccountType accountTypeWanted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.contact = contact;
        this.address = address;
        this.accountTypeWanted = accountTypeWanted;
    }

    public Long getProspectId() {
        return prospectId;
    }

    public void setProspectId(Long prospectId) {
        this.prospectId = prospectId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public AccountType getAccountTypeWanted() {
        return accountTypeWanted;
    }

    public void setAccountTypeWanted(AccountType accountTypeWanted) {
        this.accountTypeWanted = accountTypeWanted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "Prospect{" +
                "prospectId=" + prospectId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", contact='" + contact + '\'' +
                '}';
    }
}
