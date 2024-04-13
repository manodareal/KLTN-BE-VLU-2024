package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.domain.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
    //Add
    public User createUser(User user){
        userRepository.save(user);
        log.info("Create user successfully");
        return user;
    }
    //Update

    public User updateUser(String id, User user){
        User existUser = getUserbyID(id).orElse(null);
        if (existUser == null) {
            log.error("User not exist");
        } else {
            existUser.setName(user.getName());
            existUser.setEmail(user.getEmail());
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
