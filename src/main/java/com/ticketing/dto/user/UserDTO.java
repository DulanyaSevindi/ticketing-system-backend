package com.ticketing.dto.user;

import com.ticketing.Enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    public String name;
    private String email;
    private Role role;
    private String password;
}
