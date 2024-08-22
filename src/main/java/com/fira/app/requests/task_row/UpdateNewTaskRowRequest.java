package com.fira.app.requests.task_row;

import lombok.Data;

@Data
public class UpdateNewTaskRowRequest {
    private long id;
    private String rowName;
    private int order;
}
