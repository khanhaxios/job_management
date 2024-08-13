package com.fira.app.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class IDCard {
    @Id
    private String ID;

    private String name;
    private LocalDate issueDate;
    private LocalDate expiredDate;
    private LocalDate birthday;
    private String avatar;
    private String address;
    private String pin;
}
