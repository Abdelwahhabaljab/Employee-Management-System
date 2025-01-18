package com.example.employee_management_system.controller;

import com.example.employee_management_system.entity.LoginRequest;
import com.example.employee_management_system.entity.User;
import com.example.employee_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Rechercher l'utilisateur par son nom d'utilisateur
        User user = userRepository.findByUsername(loginRequest.getUsername());

        // Vérifier si l'utilisateur existe et si le mot de passe correspond
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Vérifier si l'utilisateur a un rôle
        if (user.getRole() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have a role");
        }

        // Récupérer le nom du rôle de l'utilisateur
        String roleName = user.getRole().getName();

        // Si le rôle est null ou n'existe pas dans la base de données, lever une exception
        if (roleName == null || roleName.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
        }

        // Retourner le rôle de l'utilisateur
        return ResponseEntity.ok(roleName);
    }


}

