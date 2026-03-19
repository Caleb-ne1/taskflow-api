package com.caleb.taskflow.dto;

import java.time.Instant;

public class ApiResponse<T> {

    private int status;
    private String message;
    private Instant timestamp;
    private T data;

    // Constructors
    public ApiResponse() {
        this.timestamp = Instant.now();
    }

    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    // Getters and setters
    public int isStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}