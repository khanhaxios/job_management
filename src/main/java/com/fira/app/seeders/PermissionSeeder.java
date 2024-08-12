package com.fira.app.seeders;

import com.fira.app.enums.Permission;
import com.fira.app.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner {
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        for (Permission value : Permission.values()) {
            com.fira.app.entities.Permission permission = permissionRepository.findByPermission(value).orElse(new com.fira.app.entities.Permission());
            permission.setPermission(value);
            permissionRepository.save(permission);
        }
        }
}
