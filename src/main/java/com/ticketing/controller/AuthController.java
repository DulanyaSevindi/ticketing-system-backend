package com.ticketing.controller;

import com.ticketing.dto.auth.AuthRequest;
import com.ticketing.dto.auth.AuthResponse;
import com.ticketing.dto.user.UserDTO;
import com.ticketing.entity.User;
import com.ticketing.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        User createdUser = authService.register(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            String token = authService.login(authRequest.getEmail(), authRequest.getPassword());

            User user = authService.getUserByEmail(authRequest.getEmail());

            return ResponseEntity.ok(
                    new AuthResponse(
                            "Login Successful",
                            token,
                            user.getRole().name(), // ✅ send role
                            user.getEmail(),
                            user.getId()
                    )
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid email or password", null, null, null, null));
        }
    }
}