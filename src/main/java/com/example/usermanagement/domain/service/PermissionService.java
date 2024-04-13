package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Permission;
import com.example.usermanagement.domain.repo.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        log.info("Get all permissions success");
        return  permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionbyId(Integer perId){
        log.info("Get permissionId success");
        return permissionRepository.findById(perId);
    }

    public void createPermission(Permission permission){
        permissionRepository.save(permission);
        log.info("Create permission successfully");
    }

    public void updatePermission(Integer perId, Permission permission){
        Permission existPermission = getPermissionbyId(perId).orElse(null);
        if(existPermission == null){
            log.error("Permission not exist");
        }else{
            existPermission.setPermissionName(permission.getPermissionName());
            existPermission.setDescription(permission.getDescription());
            existPermission.setPageKey(permission.getPageKey());
            existPermission.setIsActive(permission.getIsActive());
            log.info("Permission's info updated");
        }
    }

    public void deletePermission(Integer perId){
        permissionRepository.deleteById(perId);
        log.info("Deleted permission " + perId);
    }
}
