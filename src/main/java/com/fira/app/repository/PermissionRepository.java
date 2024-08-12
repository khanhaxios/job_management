package com.fira.app.repository;

import com.fira.app.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermission(com.fira.app.enums.Permission permission);
}
