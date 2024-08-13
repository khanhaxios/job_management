package com.fira.app.requests.auth;

import lombok.Data;

@Data
public class LoginByCard {
    private String cardId;
    private String pin;
    private String username;
}
