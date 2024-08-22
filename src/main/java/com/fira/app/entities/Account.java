package com.fira.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

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
    @Column(unique = true)
    private String username;
    private String phone;
    private boolean active = true;
    private boolean verify = false;
    private LocalDate verifiedAt;

    @OneToOne
    private IDCard idCard;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Account manager;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Permission> permissions = role.getPermissions();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.toString())));
        authorities.add(new SimpleGrantedAuthority(this.role.getName().toString()));
        return authorities;
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
