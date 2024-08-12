package com.fira.app.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Permission extends TimeStamps{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private com.fira.app.enums.Permission permission;
}
