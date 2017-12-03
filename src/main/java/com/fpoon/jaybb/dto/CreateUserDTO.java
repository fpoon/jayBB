package com.fpoon.jaybb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserDTO {
    private String username;
    private String password;
    private String repeatPassword;
    private String email;

    public CreateUserDTO(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
