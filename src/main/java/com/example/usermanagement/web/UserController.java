package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.User;
import com.example.usermanagement.domain.service.UserService;
import com.example.usermanagement.dto.user.input.UserInput;
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
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        log.info("Starting get all users");
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/{userID}")
    public ResponseEntity<User> getUserbyID(@PathVariable String userID){
        log.info("Starting to find by user's id");
        Optional<User> user = userService.getUserbyID(userID);
        return new ResponseEntity<>(user.orElse(null),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserInput user){
        log.info("Requesting to create new user");
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<User> createAdmin(@RequestBody UserInput admin){
        log.info("Requesting to create new admin");
        User newAdmin = userService.createAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.OK);
    }

    @PutMapping("/{userID}/update")
    public ResponseEntity<User> updateUser(@PathVariable String userID, @RequestBody User user){
        log.info("Requesting to update a user");
        User updateUser = userService.updateUser(userID, user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @DeleteMapping("/{userID}/delete")
    public ResponseEntity<User> deleteUser(@PathVariable String userID){
        log.info("Requesting to delete a user");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
