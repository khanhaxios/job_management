package com.fira.app.requests.job;

import com.fira.app.enums.JobEvaluate;
import lombok.Data;

@Data
public class EvoluteJobRequest {
    private JobEvaluate jobEvaluate;
    private String userId;

}
