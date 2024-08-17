package com.fira.app.requests.job;

import com.fira.app.enums.JobStatus;
import lombok.Data;

@Data
public class UpdateUserJobDetailRequest {
    private String userId;
    private int progress;
    private String verifyLink;
    private String instructionLink;
    private String denyReason;
    private JobStatus status;

}
