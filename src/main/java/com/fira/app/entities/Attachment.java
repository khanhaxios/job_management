package com.fira.app.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Attachment extends TimeStamps {
    @jakarta.persistence.Id
    private Long Id;

    private String path;
}
