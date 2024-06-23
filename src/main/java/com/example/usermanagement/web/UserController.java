package com.example.usermanagement.web;

import com.example.usermanagement.domain.service.UserService;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.user.input.UserInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        log.info("Starting get all users");
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchByName(@RequestParam String keyword) {
        List<UserDTO> users = userService.searchByName(keyword);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{userID}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable String userID){
        log.info("Starting to find by user's id");
        Optional<UserDTO> user = userService.getUserByID(userID);
        return new ResponseEntity<>(user.orElse(null), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserInput user){
        log.info("Requesting to create new user");
        UserDTO newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserInput admin){
        log.info("Requesting to create new admin");
        UserDTO newAdmin = userService.createAdmin(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.OK);
    }

    @PutMapping("/{userID}/update")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userID, @RequestBody UserInput user){
        log.info("Requesting to update a user");
        UserDTO updateUser = userService.updateUser(userID, user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userID}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable String userID){
        log.info("Requesting to delete a user");
        userService.deleteUser(userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
