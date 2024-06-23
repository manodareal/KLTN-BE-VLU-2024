package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Role;
import com.example.usermanagement.domain.repo.RoleRepository;
import com.example.usermanagement.util.common.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        log.info("Get all roles success");
        return roleRepository.findAll();
    }

    public Role getRoleByID(String roleId) {
        return roleRepository.findById(roleId).orElseThrow(
                () -> new NullPointerException("Not found this role: " + roleId)
        );
    }

    public Role getByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new NullPointerException("Not found this role: " + roleName)
        );
    }

    public void createDefaultRole() {
        if (roleRepository.count() == 0) {
            Role roleAdmin = new Role(RoleEnum.ADMIN.getRole(), "ROLE_ADMIN", true);
            Role roleStaff = new Role(RoleEnum.STAFF.getRole(), "ROLE_STAFF", false);
            roleRepository.save(roleAdmin);
            roleRepository.save(roleStaff);
            log.info("Create default roles successfully");
        }
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole(String roleId, Role role) {
        Role existRole = getRoleByID(roleId);
        existRole.setRoleName(role.getRoleName());
        existRole.setDescription(role.getDescription());
        return roleRepository.save(existRole);
    }

    public void deleteRole(String roleId) {
        roleRepository.deleteById(roleId);
        log.info("Delete role successfully");
    }
}
