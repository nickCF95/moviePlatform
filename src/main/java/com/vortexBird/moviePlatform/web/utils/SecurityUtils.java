package com.vortexBird.moviePlatform.web.utils;

import com.vortexBird.moviePlatform.domain.MetodoPagoUsuario;
import com.vortexBird.moviePlatform.domain.customExceptions.ExpiredCardException;
import com.vortexBird.moviePlatform.domain.customExceptions.InsufficientFundsException;
import com.vortexBird.moviePlatform.domain.customExceptions.RejectedPaymentException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class SecurityUtils {
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals(role) || authority.getAuthority().equals("ROLE_" + role)) {
                return true;
            }
        }
        return false;
    }

    public static void validateUser(String username){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !(username.equalsIgnoreCase(currentUsername))) {
            throw new IllegalStateException("The referenced user does not match the logged in user ");
        }
    }
}
