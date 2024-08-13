package com.fira.app.requests.account;

import com.fira.app.enums.Role;
import lombok.Data;

@Data
public class CreateAccountRequest {

    private String username;
    private String email;
    private String password;
    private Role role;
}
