package com.caleb.taskflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegistrationResponse {
    private Long id;
    private String email;
    private String message;
}
