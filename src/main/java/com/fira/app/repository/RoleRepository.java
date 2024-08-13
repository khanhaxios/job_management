package com.fira.app.repository;

import com.fira.app.entities.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(com.fira.app.enums.Role name);

    List<Role> findByNameContaining(Pageable pageable, com.fira.app.enums.Role name);
}
