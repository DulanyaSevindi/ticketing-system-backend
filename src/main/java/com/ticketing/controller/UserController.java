package com.ticketing.controller;

import com.ticketing.Enums.Role;
import com.ticketing.dto.user.UserDTO;
import com.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ticketing.service.UserService;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Role role
    ) {
        Pageable pageable = PageRequest.of(page, size,
                direction.equalsIgnoreCase("ASC") ? Sort.by(sort).ascending() : Sort.by(sort).descending());

        Page<User> users = userService.getAllUsers(pageable, search, role);

        // Map Page<User> to a stable JSON
        return ResponseEntity.ok(Map.of(
                "users", users.getContent(),
                "pagination", Map.of(
                        "currentPage", users.getNumber(),
                        "pageSize", users.getSize(),
                        "totalElements", users.getTotalElements(),
                        "totalPages", users.getTotalPages(),
                        "isFirst", users.isFirst(),
                        "isLast", users.isLast()
                )
        ));
    }
    //get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@RequestBody  UserDTO userdto){
        return ResponseEntity.ok(userService.updateUser(id,userdto));
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
