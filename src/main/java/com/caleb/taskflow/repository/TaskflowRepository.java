package com.caleb.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caleb.taskflow.model.Task;

import java.util.List;


public interface TaskflowRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}
