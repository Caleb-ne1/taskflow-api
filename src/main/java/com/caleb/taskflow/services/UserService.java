package com.caleb.taskflow.services;


import com.caleb.taskflow.dto.ApiResponse;
import com.caleb.taskflow.dto.PasswordResetRequest;
import com.caleb.taskflow.exception.ResourceNotFoundException;
import com.caleb.taskflow.model.User;
import com.caleb.taskflow.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private  final PasswordEncoder passwordEncoder;

    // user profile
    public User getProfile(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // update password
    @Transactional
    public ApiResponse<String> updatePassword(String email, PasswordResetRequest request) {
        // Find user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Encode new password and update
        String hashPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);

        // Return response
        return new ApiResponse<>(200, "Password updated successfully");
    }

    // delete user
    @Transactional
    public  ApiResponse<String> deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);

        return  new ApiResponse<>(200, "User deleted successfully");
    }

}
