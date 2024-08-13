package com.fira.app.responses.auth;

import com.fira.app.entities.Account;
import lombok.Data;

@Data
public class AccountResponse extends Account {
    String token;
}
