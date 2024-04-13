package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Role;
import com.example.usermanagement.domain.repo.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Role> getRolebyID(String roleId){
        log.info("Get role success");
        return roleRepository.findById(roleId);
    }
    //Add
    public Role createRole(Role role){
        roleRepository.save(role);
        log.info("Create role successfully");
        return role;
    }
    //Update

    public Role updateRole(String roleId, Role role){
        Role existRole = getRolebyID(roleId).orElse(null);
        if (existRole == null) {
            log.error("Role not exist");
        } else {
            existRole.setRoleName(role.getRoleName());
            existRole.setDesc(role.getDesc());
            existRole.setAdminstrator(true);
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
