package com.ticketing.service;

import com.ticketing.dto.UserDTO;
import com.ticketing.entity.User;
import lombok.RequiredArgsConstructor;
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
        user.setEmail(userdto.getEmail());
        user.setRole(userdto.getRole());
        return userRepository.save(user);
    }

    //get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
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
        if (userdto.getEmail() != null) user.setEmail(user.getEmail());
        if (userdto.getRole() != null) user.setRole(user.getRole());

        return userRepository.save(user);

    }

    //delete
    public void deleteUser(Long id){
        User user = getUserById(id);
        userRepository.delete(user);

    }


}