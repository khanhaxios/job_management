package com.fira.app.apis;

import com.fira.app.requests.role.AddPermToRoleRequest;
import com.fira.app.requests.role.RemovePermFromRoleRequest;
import com.fira.app.services.role.RoleService;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/roles")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin
public class RoleApi {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllRole(@RequestParam(name = "query", required = false, defaultValue = "") String query, @RequestParam(name = "sortBy", defaultValue = "name") String sortBy, @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDirection, Pageable pageable) {
        try {
            return roleService.getAll(pageable, sortBy, sortDirection, query);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping("/add-perm/{id}")
    public ResponseEntity<?> addPermToRole(@PathVariable(name = "id") Long id, @RequestBody AddPermToRoleRequest request) {
        try {
            return roleService.givePermToRole(id, request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PutMapping("/remove-perm/{id}")
    public ResponseEntity<?> removePermFromRole(@PathVariable(name = "id") Long id, @RequestBody RemovePermFromRoleRequest request) {
        try {
            return roleService.removePermFromRole(id, request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
