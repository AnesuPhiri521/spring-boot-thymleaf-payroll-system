package com.anesuphiri521.payroll.utils.StartUp;

import com.anesuphiri521.payroll.model.User;
import com.anesuphiri521.payroll.repository.RoleRepository;
import com.anesuphiri521.payroll.repository.UserRepository;
import com.anesuphiri521.payroll.utils.Enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/*
 * @created - 01/11/2023
 * @project - spring-boot-thymleaf-payroll-system
 * @author - anesuphiri - sehphirry@gmail.com
 */
@RequiredArgsConstructor
public class InitUsers implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        Optional<User> adminUser = userRepository.findByUserName("admin");
        if(adminUser.isEmpty()){
            User user = new User("Test", "User", "admin", true, roleRepository.findByName(UserRoles.ADMIN).get());
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
        }
    }
}
