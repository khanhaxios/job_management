package com.fira.app.requests.task;

import com.fira.app.enums.CollectionAction;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateTaskCommentRequest {

    private CollectionAction action = CollectionAction.ADD;
    private Set<CreateTaskCommentRequest> commentRequests = new HashSet<>();
    private Set<Long> removeIds = new HashSet<>();

}
