package com.fira.app.requests.job;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateJobRequest {
    private String jobId;
    private String title;

    private String description;
    private String note;
    private String target;

    private Date timeStart;
    private Date timeEnd;
    private String additionInfo;
}
