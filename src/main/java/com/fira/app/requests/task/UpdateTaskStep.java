package com.fira.app.requests.task;

import com.fira.app.enums.CollectionAction;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateTaskStep {
    CollectionAction action = CollectionAction.ADD;
    Set<CreateNewTaskStepRequest> taskStepRequests = new HashSet<>();
    Set<Long> removeStepsIds = new HashSet<>();
}
