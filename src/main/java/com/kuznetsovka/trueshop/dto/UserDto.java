package com.kuznetsovka.trueshop.dto;

import com.kuznetsovka.trueshop.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String name;
    private String password;
    private String matchingPassword;
    private String email;
    private Role role;
}
