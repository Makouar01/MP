package com.example.controller;

import com.example.dto.SigninRequest;
import com.example.dto.SignupRequest;
import com.example.dto.AuthResponse;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès");
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody SigninRequest request) {
        return ResponseEntity.ok(authService.signin(request));
    }
}
