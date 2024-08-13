package com.fira.app.seeders;

import com.fira.app.enums.Permission;
import com.fira.app.repository.PermissionRepository;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner, Ordered {
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        for (Permission value : Permission.values()) {
            com.fira.app.entities.Permission permission = permissionRepository.findByPermission(value).orElse(new com.fira.app.entities.Permission());
            permission.setPermission(value);
            permissionRepository.save(permission);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
