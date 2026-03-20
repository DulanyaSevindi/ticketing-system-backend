package com.ticketing.dto;

import com.ticketing.Enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    public String name;
    private String email;
    private Role role;
}
