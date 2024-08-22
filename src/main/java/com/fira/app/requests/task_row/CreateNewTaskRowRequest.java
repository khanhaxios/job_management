package com.fira.app.requests.task_row;

import lombok.Data;

@Data
public class CreateNewTaskRowRequest {
    private String rowName;
    private String jobId;
    private int order;
}
