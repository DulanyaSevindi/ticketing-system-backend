package com.ticketing.controller;

import com.ticketing.dto.user.UserDTO;
import com.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ticketing.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //CREATE
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userdto){
        return ResponseEntity.ok(userService.createUser(userdto));
    }

    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, UserDTO userdto){
        return ResponseEntity.ok(userService.updateUser(id,userdto));
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
