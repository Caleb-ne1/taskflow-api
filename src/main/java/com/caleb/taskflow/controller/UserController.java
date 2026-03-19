package com.caleb.taskflow.controller;


import com.caleb.taskflow.dto.ApiResponse;
import com.caleb.taskflow.dto.PasswordResetRequest;
import com.caleb.taskflow.dto.UserResponse;
import com.caleb.taskflow.model.User;
import com.caleb.taskflow.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User response = userService.getProfile(userDetails.getUsername());

        return  ResponseEntity.status(HttpStatus.OK).body(new UserResponse(response.getId(), response.getEmail(), response.getPassword()));

    }

    @PutMapping("/update-password")
    public ResponseEntity<ApiResponse<String>> updateUserPassword(@RequestBody PasswordResetRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        ApiResponse<String> response = userService.updatePassword(userDetails.getUsername(), request);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        ApiResponse<String> response = userService.deleteUser(userDetails.getUsername());

        return ResponseEntity.ok(response);
    }
}
