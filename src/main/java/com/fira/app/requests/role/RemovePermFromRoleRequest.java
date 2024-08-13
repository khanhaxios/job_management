package com.fira.app.requests.role;

import com.fira.app.enums.Permission;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RemovePermFromRoleRequest {
    Set<Long> ids = new HashSet<>();
}
