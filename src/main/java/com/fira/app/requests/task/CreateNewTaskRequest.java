package com.fira.app.requests.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fira.app.entities.TaskLabel;
import com.fira.app.enums.TaskProgress;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class CreateNewTaskRequest {
    @NotBlank
    private String taskName;
    @NotBlank
    private String taskDescription;
    private TaskProgress taskProgress = TaskProgress.PENDING;
    private Set<String> assignmentsIds = new HashSet<>();
    private Set<CreateNewTaskStepRequest> steps = new HashSet<>();
    private Set<CreateTaskAttachmentRequest> attachments = new HashSet<>();
    private Long taskLabel;

    @NotNull
    private long taskRowId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @Past
    private LocalDate timeStart;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @Past
    private LocalDate timeEnd;
}
