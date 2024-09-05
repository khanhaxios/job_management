package com.fira.app.requests.task;

import com.fira.app.enums.CollectionAction;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateTaskAttachmentRequest {
    private CollectionAction action = CollectionAction.ADD;
    private Set<CreateTaskAttachmentRequest> attachmentRequests = new HashSet<>();
    private Set<Long> removeIds = new HashSet<>();
}
