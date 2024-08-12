package com.fira.app.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "accounts")
public class Account extends TimeStamps implements UserDetails {
    public Account() {
        this.id = UUID.randomUUID().toString();
    }

    @Id
    private String id;
    private String email;

    private String password;
    private String username;
    private String phone;
    private boolean active;
    private boolean verify;
    private LocalDate verifiedAt;

    @OneToOne
    private IDCard idCard;

    @ManyToOne
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Permission> permissions = role.getPermissions();
        return permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.toString())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
