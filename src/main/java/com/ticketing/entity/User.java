package com.ticketing.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.ticketing.Enums.Role;



@Entity
@Data
@Getter
@Setter
@Table(name= "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

  @Enumerated(EnumType.STRING)
    private Role role;

}
