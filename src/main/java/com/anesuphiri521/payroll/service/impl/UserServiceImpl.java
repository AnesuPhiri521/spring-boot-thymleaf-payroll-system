package com.anesuphiri521.payroll.service.impl;

import com.anesuphiri521.payroll.dto.NewUserDto;
import com.anesuphiri521.payroll.dto.NewUserResponseDto;
import com.anesuphiri521.payroll.model.Role;
import com.anesuphiri521.payroll.model.User;
import com.anesuphiri521.payroll.service.UserService;
import com.anesuphiri521.payroll.utils.Enums.UserRoles;
import com.anesuphiri521.payroll.repository.RoleRepository;
import com.anesuphiri521.payroll.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public NewUserResponseDto saveUser(NewUserDto newUserDto) {
        User user = new User();
        user.setUserName(newUserDto.getUsername());
        user.setFirstName(newUserDto.getFname());
        user.setLastName(newUserDto.getLname());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(newUserDto.getUsername()));


        if(newUserDto.getRole().equals("APPROVER")) {
            Optional<Role> userRoles = roleRepository.findByName(UserRoles.APPROVER);
            if(userRoles.isPresent()){
                log.info("User role is " +userRoles.get().getName());
                user.setRole(userRoles.get());
            }
        } else if (newUserDto.getRole().equals("INITIATOR")) {
            Optional<Role> userRoles = roleRepository.findByName(UserRoles.INITIATOR);
            if(userRoles.isPresent()){
                log.info("User role is " +userRoles.get().getName());
                user.setRole(userRoles.get());
            }
        }

        NewUserResponseDto responseDto = new NewUserResponseDto();
        if(userRepository.findByUserName(newUserDto.getUsername()).isPresent()){
            responseDto.setStatus(false);
            responseDto.setNarration("User with username already exits");
        }else{
            responseDto.setStatus(true);
            responseDto.setNarration("User added successfully");
            userRepository.save(user);
        }
        return responseDto;
    }

    @Override
    public String getUserRole(String userName) {
        Optional<User> user = userRepository.findByUserName(userName);
        return user.get().getRole().getName().toString();
    }
}
