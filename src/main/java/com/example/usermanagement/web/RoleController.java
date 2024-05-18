package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Role;
import com.example.usermanagement.domain.service.RoleService;
import com.example.usermanagement.dto.user.input.RoleInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("")
    public ResponseEntity<List<Role>> getAllRole(){
        log.info("Starting get all roles");
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRolebyID(@PathVariable String roleId){
        log.info("Starting to find by role's id");
        Role role = roleService.getRoleByID(roleId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody RoleInput role) {
        log.info("Request to create new role");
        Role newRole = roleService.createRole(role);
        return new ResponseEntity<>(newRole, HttpStatus.OK);
    }

    @PutMapping("/{roleId}/update")
    public ResponseEntity<Role> updateRole(@PathVariable String roleId, @RequestBody Role role){
        log.info("Request to update role");
        Role updateRole = roleService.updateRole(roleId, role);
        return new ResponseEntity<>(updateRole, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}/delete")
    public ResponseEntity<Role> deleteRole(@PathVariable String roleId){
        log.info("Requesting to delete a role");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}