package com.bancabc.payroll.repository;

import com.bancabc.payroll.model.Role;
import com.bancabc.payroll.utils.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoles userRole);
}
