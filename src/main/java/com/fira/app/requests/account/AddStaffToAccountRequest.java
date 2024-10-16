package com.fira.app.requests.account;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AddStaffToAccountRequest {
    Set<String> staffIds = new HashSet<>();
}
