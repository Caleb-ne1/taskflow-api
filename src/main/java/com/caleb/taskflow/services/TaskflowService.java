package com.caleb.taskflow.services;

import java.util.List;

import com.caleb.taskflow.dto.ApiResponse;
import com.caleb.taskflow.exception.AccessDeniedException;
import com.caleb.taskflow.exception.ResourceNotFoundException;
import com.caleb.taskflow.model.User;
import com.caleb.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.caleb.taskflow.dto.TaskRequest;
import com.caleb.taskflow.model.Task;
import com.caleb.taskflow.repository.TaskflowRepository;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TaskflowService {
    
    private final TaskflowRepository repository;
    private final UserRepository userRepository;

    public Task createTask(Task task, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        task.setUser(user);
        return repository.save(task);
    }


    // get single task for user
    public Task getTaskForUser(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if(!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to access this task");
        }

        return  task;
    }

    // get tasks by user
    public List<Task> getTasksForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        return  repository.findByUserId(user.getId());
    }

    @Transactional
    public Task updateTaskForUser(Long id, TaskRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // check if task belongs to a user
        if (!task.getUser().getId().equals(user.getId())) {
            throw  new AccessDeniedException("You are not allowed to update this task");
        }

        // update
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.isCompleted());

        return  repository.save(task);

    }

    // delete task for user
    public ApiResponse<String> deleteTaskForUser(Long id, String email) {
        // Find the task by ID
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        // find User by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if the task belongs to the user with the given email
        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not authorized to delete this task");
        }

        // Delete the task
        repository.delete(task);

        return new ApiResponse<>(200, "Task deleted successfully");
    }
}
