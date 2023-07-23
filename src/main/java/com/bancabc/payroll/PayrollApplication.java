package com.bancabc.payroll;

import com.bancabc.payroll.model.Role;
import com.bancabc.payroll.model.User;
import com.bancabc.payroll.repository.RoleRepository;
import com.bancabc.payroll.repository.UserRepository;
import com.bancabc.payroll.utils.Constants;
import com.bancabc.payroll.utils.UserRoles;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class PayrollApplication implements CommandLineRunner {

	public static void main(String[] args) {
		new File(Constants.uploadDir).mkdir();
		SpringApplication.run(PayrollApplication.class, args);
	}

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {


		Optional<Role> userRoles = roleRepository.findByName(UserRoles.APPROVER);
		if(userRoles.isEmpty()){
			Role role1 = new Role(UserRoles.APPROVER);
			roleRepository.save(role1);
		}

		Optional<Role> userRoles2 = roleRepository.findByName(UserRoles.INITIATOR);
		if(userRoles2.isEmpty()){
			Role role2 = new Role(UserRoles.INITIATOR);
			roleRepository.save(role2);
		}

		Optional<Role> userRoles3 = roleRepository.findByName(UserRoles.ADMIN);
		if(userRoles3.isEmpty()){
			Role role3 = new Role(UserRoles.ADMIN);
			roleRepository.save(role3);
		}

		Optional<User> adminUser = userRepository.findByUserName("admin");
		if(adminUser.isEmpty()){
			User user = new User("Test", "User", "admin", true, roleRepository.findByName(UserRoles.ADMIN).get());
			user.setPassword(passwordEncoder.encode("admin"));
			userRepository.save(user);
		}
	}
}
