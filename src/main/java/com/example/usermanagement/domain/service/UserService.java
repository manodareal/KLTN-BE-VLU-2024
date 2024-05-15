package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.domain.repo.UserRepository;
import com.example.usermanagement.dto.user.input.UserInput;
import com.example.usermanagement.util.common.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    //Get all users into List
    public List<User> getAllUsers(){
        log.info("Get all users success");
        return userRepository.findAll();
    }
    //Using optional to find needing one
    public Optional<User> getUserbyID(String id){
        log.info("Get user success");
        return userRepository.findById(id);
    }

    /**
     * Calculate point base on price (1 point = 1 USD)
     *
     * @param userInput - Input data from userInput
     * @return - point
     */
    public User createUser(UserInput userInput){
        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setFullName(userInput.getFullName());
        user.setEmail(userInput.getEmail());
        user.setPassword(userInput.getPassword());
        user.setRole(roleService.getByRoleName(RoleEnum.STAFF.getRole()));

        userRepository.save(user);
        log.info("Create user successfully");
        return user;
    }

    public User createAdmin(UserInput userInput){
        User user = new User();
        user.setUsername(userInput.getUsername());
        user.setFullName(userInput.getFullName());
        user.setEmail(userInput.getEmail());
        user.setPassword(userInput.getPassword());
        user.setRole(roleService.getByRoleName(RoleEnum.ADMIN.getRole()));

        userRepository.save(user);
        log.info("Create admin successfully");
        return user;
    }

    //Update

    public User updateUser(String id, User user){
        User existUser = getUserbyID(id).orElse(null);
        if (existUser == null) {
            log.error("User not exist");
        } else {
            existUser.setUsername(user.getUsername());
            existUser.setEmail(user.getEmail());
            existUser.setFullName(user.getFullName());
            //temporary password change
            existUser.setPassword(user.getPassword());
            log.info("User's information updated");
        }
        return existUser;
    }

    //delete
    public void deleteUser(String id){
        log.info("Delete successfully");
        userRepository.deleteById(id);
    }


}
