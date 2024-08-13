package com.fira.app.services.role;

import com.fira.app.requests.role.AddPermToRoleRequest;
import com.fira.app.requests.role.CreateRoleRequest;
import com.fira.app.requests.role.RemovePermFromRoleRequest;
import com.fira.app.requests.role.UpdateRoleRequest;
import com.fira.app.services.base.ICrudService;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface RoleService extends ICrudService<CreateRoleRequest, UpdateRoleRequest, Long> {
    ResponseEntity<?> givePermToRole(Long roleId, AddPermToRoleRequest request);

    ResponseEntity<?> removePermFromRole(Long roleId, RemovePermFromRoleRequest request);
}
