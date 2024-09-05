package com.fira.app.requests.task;

import lombok.Data;

@Data
public class CreateTaskAttachmentRequest {
    private Long Id;
    private String path;
}
