package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Role;
import com.example.usermanagement.domain.repo.RoleRepository;
import com.example.usermanagement.util.common.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    //Get all roles into List
    public List<Role> getAllRoles(){
        log.info("Get all roles success");
        return roleRepository.findAll();
    }

    //Using optional to find needing one
    public Role getRoleByID(String roleId){
        Role existRole = roleRepository.findById(roleId).orElseThrow(
                () -> new NullPointerException("Not found this role:" + roleId)
        );
        log.info("Get role success");
        return existRole;
    }

    //Using optional to find needing one
    public Role getRoleByName(String roleName){
        Role existRole = roleRepository.findRoleByName(roleName).orElseThrow(
                () -> new NullPointerException("Not found this role:" + roleName)
        );
        log.info("Get role success");
        return existRole;
    }

    //Add default role system
    public void createDefaultRole() {
        List<Role> roles = new ArrayList<>();

        Role roleAdmin = new Role(RoleEnum.ADMIN.getRole(), "", true);
        Role roleUser = new Role(RoleEnum.USER.getRole(), "", false);

        roles.add(roleAdmin);
        roles.add(roleUser);

        roleRepository.saveAll(roles);
        log.info("Create list roles default success");
    }

    //Add
    public Role createRole(Role role){
        roleRepository.save(role);
        log.info("Create role successfully");
        return role;
    }

    //Update
    public Role updateRole(String roleId, Role role){
        Role existRole = getRoleByID(roleId);
        if (existRole == null) {
            log.error("Role not exist");
        } else {
            existRole.setRoleName(role.getRoleName());
            existRole.setDesc(role.getDesc());
            existRole.setAdministrator(true);
            log.info("Role's information updated");
        }
        return existRole;
    }

    //delete
    public void deleteRole(String roleId){
        log.info("Delete successfully");
        roleRepository.deleteById(roleId);
    }
}
