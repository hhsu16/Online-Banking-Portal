package web.api.models;

import web.api.models.enums.UserRole;
import web.api.models.enums.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long userId;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String emailId;
    private String password;
    private Date dateOfBirth;
    private String address;
    @Column(nullable = false, unique = true, length = 10)
    private String contact;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;


    public User() {
    }

    public User(String firstName, String lastName, String emailId, String password, Date dateOfBirth, String address, String contact, UserRole role, UserStatus userStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.contact = contact;
        this.role = role;
        this.userStatus = userStatus;
    }

    public Long getUserId() {
        return userId;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContact() {
        return contact;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailId='" + emailId + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", contact='" + contact + '\'' +
                ", role=" + role +
                '}';
    }
}
