package com.example.usermanagement.domain.service;

import com.example.usermanagement.config.PasswordEncrypt;
import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.domain.repo.UserRepository;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.user.input.UserInput;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.util.common.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    public List<UserDTO> searchByName(String name) {
        List<User> users = userRepository.searchByName(name);
        return users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<UserDTO> getAllUsers(){
        log.info("Get all users success");
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserByID(String id){
        log.info("Get user success");
        Optional<User> user = userRepository.findById(id);
        return user.map(userMapper::toDTO);
    }

    public Optional<UserDTO> getUserByEmail(String email){
        log.info("Get user success");
        Optional<User> user = userRepository.findUserByEmail(email);
        return user.map(userMapper::toDTO);
    }

    public UserDTO createUser(UserInput userInput){
        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setFullName(userInput.getFullName());
        user.setEmail(userInput.getEmail());
        user.setPassword(PasswordEncrypt.bcryptPassword(userInput.getPassword()));
        if (userInput.getRole().equals("STAFF")) {
            user.setRole(roleService.getByRoleName(RoleEnum.STAFF.getRole()));
        } else if (userInput.getRole().equals("ADMIN"))
        {
            user.setRole(roleService.getByRoleName(RoleEnum.ADMIN.getRole()));
        }
        userRepository.save(user);
        log.info("Create user successfully");
        return userMapper.toDTO(user);
    }

    public UserDTO createAdmin(UserInput userInput){
        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setFullName(userInput.getFullName());
        user.setEmail(userInput.getEmail());
        user.setPassword(PasswordEncrypt.bcryptPassword(userInput.getPassword()));
        user.setRole(roleService.getByRoleName(RoleEnum.ADMIN.getRole()));

        userRepository.save(user);
        log.info("Create admin successfully");
        return userMapper.toDTO(user);
    }

    public UserDTO updateUser(String id, UserInput userInput){
        User existUser = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not exist")
        );

        existUser.setUsername(userInput.getUsername());
        existUser.setEmail(userInput.getEmail());
        existUser.setFullName(userInput.getFullName());
        if (userInput.getRole().equals("STAFF")) {
            existUser.setRole(roleService.getByRoleName(RoleEnum.STAFF.getRole()));
        } else if (userInput.getRole().equals("ADMIN"))
        {
            existUser.setRole(roleService.getByRoleName(RoleEnum.ADMIN.getRole()));
        }
        // Temporary password change if needed
        // existUser.setPassword(PasswordEncrypt.bcryptPassword(userInput.getPassword()));
        userRepository.save(existUser);

        log.info("User's information updated");
        return userMapper.toDTO(existUser);
    }

    public void deleteUser(String id){
        log.info("Delete successfully");
        userRepository.deleteById(id);
    }
}
