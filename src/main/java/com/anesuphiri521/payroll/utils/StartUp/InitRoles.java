package com.anesuphiri521.payroll.utils.StartUp;

/*
 * @created - 01/11/2023
 * @project - spring-boot-thymleaf-payroll-system
 * @author - anesuphiri - sehphirry@gmail.com
 */

import com.anesuphiri521.payroll.model.Role;
import com.anesuphiri521.payroll.repository.RoleRepository;
import com.anesuphiri521.payroll.utils.Enums.UserRoles;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
public class InitRoles implements CommandLineRunner {
    private  final RoleRepository roleRepository;
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
    }
}
