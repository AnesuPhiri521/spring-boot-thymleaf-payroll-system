package com.bancabc.payroll.config;

import com.bancabc.payroll.model.Role;
import com.bancabc.payroll.model.User;
import com.bancabc.payroll.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    UserRepository userRepository;


    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found" + userName));
        return user.map(UserDtls::new).get();
//        return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPassword(), null);
    }

//    private Collection<? extends GrantedAuthority> mapRoles(Collection<Role> roles){
//        roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//        return null;
//    }
}