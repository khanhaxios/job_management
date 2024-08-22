package com.fira.app.requests.job;

import com.fira.app.enums.LabelType;
import lombok.Data;

@Data
public class CreateNewJobLabelRequest {
    private String labelName;
    private LabelType labelType;
    private String labelColor;
}
