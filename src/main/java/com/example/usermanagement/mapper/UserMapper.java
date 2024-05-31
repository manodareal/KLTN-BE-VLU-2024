package com.example.usermanagement.mapper;

import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.domain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private RoleService roleService;

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getCreateAt(),
                user.getUpdateAt(),
                user.getRole().getRoleName(),
                user.getRole().getDescription()
        );
    }

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setCreateAt(userDTO.getCreateAt());
        user.setUpdateAt(userDTO.getUpdateAt());
        user.setRole(roleService.getByRoleName(userDTO.getRoleName()));
        return user;
    }
}
