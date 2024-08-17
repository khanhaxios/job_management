package com.fira.app.entities;

import com.fira.app.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Notification {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String fromUser;
    private String toUser;
    private String content;
    private String data;
    private Date createdAt = new Date(System.currentTimeMillis());

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
