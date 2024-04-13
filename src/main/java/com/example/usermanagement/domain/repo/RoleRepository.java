package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findRoleByName(String roleName);
}
