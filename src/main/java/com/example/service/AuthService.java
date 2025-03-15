package com.example.service;

import com.example.dto.SigninRequest;
import com.example.dto.SignupRequest;
import com.example.dto.AuthResponse;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.exception.IncorrectPasswordException;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserRepository;
import com.example.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public void signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.OBSERVATEUR);

        userRepository.save(user);
    }

    public AuthResponse signin(SigninRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé !"));

        if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            throw new IncorrectPasswordException("Mot de passe incorrect !");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
