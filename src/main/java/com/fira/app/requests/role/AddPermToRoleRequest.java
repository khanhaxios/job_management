package com.fira.app.requests.role;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AddPermToRoleRequest {
    Set<Long> ids = new HashSet<>();
}
