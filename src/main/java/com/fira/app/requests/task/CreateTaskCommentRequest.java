package com.fira.app.requests.task;

import com.fira.app.entities.Attachment;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateTaskCommentRequest {
    private Long Id;
    private String content;
    private String attachmentRequests;
    private String authorId;
}
