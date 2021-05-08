package web.api.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.api.models.User;
import web.api.models.enums.UserStatus;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String password;
    private UserStatus userStatus;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.username = user.getEmailId();
        this.password = user.getPassword();
        this.userStatus = user.getUserStatus();
        this.authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        boolean status = true;
        if(userStatus.equals(UserStatus.ACTIVE)){
            status = true;
        }
        else if(userStatus.equals(UserStatus.INACTIVE)){
            status = false;
        }
        return status;
    }
}
