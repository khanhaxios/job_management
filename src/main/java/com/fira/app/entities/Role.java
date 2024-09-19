package com.fira.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Role extends TimeStamps {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions = new HashSet<>();

    public void addPerm(Permission permission) {
        if (!this.permissions.contains(permission)) {
            this.permissions.add(permission);
        }
    }

    public void removePerm(Permission permission) {
        if (this.permissions.contains(permission)) {
            this.permissions.remove(permission);
        }
    }
}
