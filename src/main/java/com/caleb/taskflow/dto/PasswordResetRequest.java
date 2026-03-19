package com.caleb.taskflow.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {

    private String currentPassword;
    private String newPassword;
}
