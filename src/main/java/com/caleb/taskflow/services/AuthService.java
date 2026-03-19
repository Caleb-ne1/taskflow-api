package com.caleb.taskflow.services;

import com.caleb.taskflow.dto.AuthRequest;
import com.caleb.taskflow.dto.UserRegistrationRequest;
import com.caleb.taskflow.dto.UserRegistrationResponse;
import com.caleb.taskflow.exception.AlreadyExistsException;
import com.caleb.taskflow.model.User;
import com.caleb.taskflow.repository.UserRepository;
import com.caleb.taskflow.utill.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    //registration
    @Transactional
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        // check if email already exist;
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email already exists");
        }

        // hash password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder().email(request.getEmail()).password(hashedPassword).build();

        userRepository.save(user);

        return new UserRegistrationResponse(user.getId(), user.getEmail(), "User registered successfully");
    }

    public Map<String, String> login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return Map.of("token", token);
    }
}
