package com.fira.app.seeders;

import com.fira.app.enums.Permission;
import com.fira.app.enums.Role;
import com.fira.app.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner, Ordered {

    private final RoleRepository repository;

    @Override
    public void run(String... args) throws Exception {
        for (Role value : Role.values()) {
            com.fira.app.entities.Role role = repository.findByName(value).orElse(new com.fira.app.entities.Role());
            role.setName(value);
            repository.save(role);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
