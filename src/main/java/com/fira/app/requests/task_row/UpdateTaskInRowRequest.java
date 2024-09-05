package com.fira.app.requests.task_row;

import com.fira.app.enums.CollectionAction;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateTaskInRowRequest {
    private Set<Long> taskIds = new HashSet<>();
    private CollectionAction action = CollectionAction.ADD;
}
