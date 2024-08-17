package com.fira.app.utils;


import com.fira.app.entities.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketPushingUtils {
    private final SimpMessagingTemplate pusher;


    public void pushNotification(Notification notification) {
        pusher.convertAndSend("/notifications", notification);
    }
}
