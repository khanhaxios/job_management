package com.fira.app.requests.task;

import com.fira.app.enums.CollectionAction;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateTaskAssignment {
    Set<String> assginmentIds = new HashSet<>();
    CollectionAction collectionAction = CollectionAction.ADD;
}
