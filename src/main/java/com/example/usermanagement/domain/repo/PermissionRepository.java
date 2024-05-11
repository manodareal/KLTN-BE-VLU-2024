package com.example.usermanagement.domain.repo;

import com.example.usermanagement.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
