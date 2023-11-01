package com.anesuphiri521.payroll.repository;

import com.anesuphiri521.payroll.model.Role;
import com.anesuphiri521.payroll.utils.Enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoles userRole);
}
