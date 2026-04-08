package com.ticketing.service;

import com.ticketing.Enums.Role;
import com.ticketing.dto.user.UserDTO;
import com.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ticketing.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //create user
    public User createUser(UserDTO userdto) {
        User user = new User();
        user.setName(userdto.getName());
        user.setPassword(userdto.getPassword());
        user.setEmail(userdto.getEmail());
        user.setRole(userdto.getRole());
        user.setLoginCount(0);
        return userRepository.save(user);
    }

    //get all users
    public Page<User> getAllUsers(Pageable pageable, String search, Role role) {
        if (search != null && role != null) {
            return userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCaseAndRole(
                    search, search, search, role, pageable);
        } else if (search != null) {
            return userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    search, search, search, pageable);
        } else if (role != null) {
            return userRepository.findByRole(role, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }

    //user By Id
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    //update user
    public User updateUser(Long id, UserDTO userdto) {
        User user = getUserById(id);

        if (userdto.getName() != null) user.setName(userdto.getName());
        if (userdto.getEmail() != null) user.setEmail(userdto.getEmail());
        if (userdto.getRole() != null) user.setRole(userdto.getRole());
        if (userdto.getPassword() != null) user.setPassword(userdto.getPassword()); // Optional, only if you allow changing password

        return userRepository.save(user);
    }

    //delete
    public void deleteUser(Long id){
        User user = getUserById(id);
        userRepository.delete(user);

    }


}