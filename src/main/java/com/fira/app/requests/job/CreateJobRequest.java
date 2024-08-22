package com.fira.app.requests.job;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class CreateJobRequest {

    @NotNull
    private String jobName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @NotNull
    private LocalDate timeStart;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate timeEnd;

    private Set<String> memberIds = new HashSet<>();
    private Set<CreateNewJobLabelRequest> labels = new HashSet<>();

}
