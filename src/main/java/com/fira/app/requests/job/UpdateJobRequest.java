package com.fira.app.requests.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fira.app.enums.CollectionAction;
import com.fira.app.enums.MemberAction;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UpdateJobRequest {
    private String jobId;
    private String jobName;

    private List<String> listMemberIds = new ArrayList<>();
    private MemberAction memberAction = MemberAction.ADD;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate timeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate timeEnd;
    private Set<CreateNewJobLabelRequest> labels = new HashSet<>();
    private Set<Long> removeLabelIds = new HashSet<>();
    private CollectionAction labelAction = CollectionAction.ADD;
}
