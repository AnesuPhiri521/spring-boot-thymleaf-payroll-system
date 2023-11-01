package com.anesuphiri521.payroll.config;

import com.anesuphiri521.payroll.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

public class UserDtls implements UserDetails {

    private PasswordEncoder passwordEncoder;
    private String userName;
    private String password;
    private String firstName;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public UserDtls(User user){
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.firstName = user.getFirstName();
//        this.authorities = Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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
        return userName;
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
        return active;
    }
}
