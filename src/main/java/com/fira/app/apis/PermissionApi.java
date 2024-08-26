package com.fira.app.apis;

import com.fira.app.entities.Permission;
import com.fira.app.repository.PermissionRepository;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/permissions")
public class PermissionApi {
    private final PermissionRepository permissionRepository;

    @GetMapping
    ResponseEntity<?> getAll(Pageable pageable) {
        try {
            return ResponseHelper.success(permissionRepository.findAll(pageable));
        } catch (Exception e) {
            return ResponseHelper.success(e.getMessage());
        }
    }
}
