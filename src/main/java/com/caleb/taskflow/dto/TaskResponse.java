package com.caleb.taskflow.dto;

import com.caleb.taskflow.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class TaskResponse {
    
    private Long id;
    private String title;

    private String description;
    private boolean completed;
}
