package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.domain.service.UserService;
import com.example.usermanagement.dto.user.input.UserInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("Starting get all users");
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserbyID(@PathVariable String id){
        log.info("Starting to find by user's id");
        Optional<User> user = userService.getUserbyID(id);
        return new ResponseEntity<>(user.orElse(null),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserInput user){
        log.info("Requesting to create new user");
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PutMapping("/{userID}/update")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user){
        log.info("Requesting to update a user");
        User updateUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @DeleteMapping("/{userID}/delete")
    public ResponseEntity<User> deleteUser(@PathVariable String id){
        log.info("Requesting to delete a user");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
