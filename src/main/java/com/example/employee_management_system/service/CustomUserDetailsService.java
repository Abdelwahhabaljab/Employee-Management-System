package com.example.employee_management_system.service;

import com.example.employee_management_system.entity.User;
import com.example.employee_management_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        // Si l'utilisateur n'est pas trouvé, lever une exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Récupérer le rôle de l'utilisateur (en supposant qu'un utilisateur n'a qu'un seul rôle)
        String role = user.getRole().getName();  // Si vous avez un seul rôle par utilisateur

        // Retourner un UserDetails avec un seul rôle
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(role)  // Ajouter directement le nom du rôle
                .build();
    }

}
