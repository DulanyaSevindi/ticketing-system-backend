package com.ticketing.dto.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String message;
    private String token;
    private String role;
    private String email;
    private Long id;
}
