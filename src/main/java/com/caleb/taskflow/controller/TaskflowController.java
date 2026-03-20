package com.caleb.taskflow.controller;

import java.util.List;

import com.caleb.taskflow.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.caleb.taskflow.dto.TaskRequest;
import com.caleb.taskflow.dto.TaskResponse;
import com.caleb.taskflow.model.Task;
import com.caleb.taskflow.services.TaskflowService;

import jakarta.validation.Valid;


@Controller
public class TaskflowController {
    
    private final TaskflowService service;

    public TaskflowController(TaskflowService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<String> hello() {
        return ResponseEntity.status(HttpStatus.OK).body("{ message: TaskFlow api running }");
    }


    // create task for user
    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = Task.builder().build();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.isCompleted());

        Task newTask = service.createTask(task, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TaskResponse(
            newTask.getId(),
            newTask.getTitle(),
                newTask.getDescription(),
            newTask.isCompleted()
        ));
    }


    // get all tasks for a user
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTaskForUser(@AuthenticationPrincipal UserDetails userDetails) {
        List<TaskResponse> tasks = service.getTasksForUser(userDetails.getUsername()).stream().map(task -> new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted()
        )).toList();

        return  ResponseEntity.status(HttpStatus.OK).body(tasks);

    }

    // get single task for a user
    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getTaskForUser(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = service.getTaskForUser(id, userDetails.getUsername());

        return  ResponseEntity.status(HttpStatus.OK).body(new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted()));
    }


    // update task for user
    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Task task = service.updateTaskForUser(id, request, userDetails.getUsername());

        return  ResponseEntity.status(HttpStatus.OK).body(new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted()));
    }

    // delete task for user
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<ApiResponse<String>>  deleteTaskForUser(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<String> response = service.deleteTaskForUser(id, userDetails.getUsername());

        return  ResponseEntity.ok(response);
    }
    
}
