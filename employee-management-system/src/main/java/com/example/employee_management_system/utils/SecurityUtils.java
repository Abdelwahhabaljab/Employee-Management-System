package com.example.employee_management_system.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    /**
     * Récupère le nom de l'utilisateur actuellement authentifié.
     *
     * @return Le nom de l'utilisateur, ou "Unknown" si aucune authentication n'est trouvée.
     */
    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : "Unknown";
    }
}
