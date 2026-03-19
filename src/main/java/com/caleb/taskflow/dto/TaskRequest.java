package com.caleb.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskRequest {

    @NotBlank(message = "Title required")
    @Size(min = 3, max = 100, message = "Title must not be a least 3 characters and must not be at most 100 words")
    private String title;

    private String description;

    @NotNull(message = "Completed status required")
    private boolean completed;

}