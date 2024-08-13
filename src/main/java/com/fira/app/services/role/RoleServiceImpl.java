package com.fira.app.services.role;

import com.fira.app.entities.Permission;
import com.fira.app.enums.Role;
import com.fira.app.expceptions.AppException;
import com.fira.app.repository.PermissionRepository;
import com.fira.app.repository.RoleRepository;
import com.fira.app.requests.role.AddPermToRoleRequest;
import com.fira.app.requests.role.CreateRoleRequest;
import com.fira.app.requests.role.RemovePermFromRoleRequest;
import com.fira.app.requests.role.UpdateRoleRequest;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public ResponseEntity<?> store(CreateRoleRequest createRoleRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(UpdateRoleRequest updateRoleRequest) {
        return null;
    }

    @Override
    public ResponseEntity<?> destroy(Long aLong) {
        return null;
    }

    @Override
    public ResponseEntity<?> destroyAll(Set<Long> longs) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDesc), fieldSort);
        Pageable pageOf = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        if (!Objects.equals(query, "")) {
            return ResponseHelper.success(roleRepository.findByNameContaining(pageOf, Role.valueOf(query)));
        }
        return ResponseHelper.success(roleRepository.findAll(pageable));
    }


    @Override
    public ResponseEntity<?> givePermToRole(Long roleId, AddPermToRoleRequest request) {
        com.fira.app.entities.Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException("Role not found"));
        for (Long id : request.getIds()) {
            Permission permission = permissionRepository.findById(id).orElse(null);
            if (permission != null) {
                role.addPerm(permission);
            }
        }
        return ResponseHelper.success(roleRepository.save(role));
    }

    @Override
    public ResponseEntity<?> removePermFromRole(Long roleId, RemovePermFromRoleRequest request) {
        com.fira.app.entities.Role role = roleRepository.findById(roleId).orElseThrow(() -> new AppException("Role not found"));
        for (Long id : request.getIds()) {
            Permission permission = permissionRepository.findById(id).orElse(null);
            if (permission != null) {
                role.removePerm(permission);
            }
        }
        return ResponseHelper.success(roleRepository.save(role));
    }
}
