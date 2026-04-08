package com.ticketing.entity;

import com.ticketing.Enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // e.g., "ADMIN", "DEVELOPER"

    private String email;

    private String name;

    @Column(nullable = false)
    private Integer loginCount = 0;
}