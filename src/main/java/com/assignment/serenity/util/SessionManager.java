package com.assignment.serenity.util;

import com.assignment.serenity.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SessionManager {
    private static SessionManager instance;
    private UserDto currentUser;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void clearSession() {
        currentUser = null;
    }

    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equalsIgnoreCase(currentUser.getRole());
    }

    public boolean isReceptionist() {
        return currentUser != null && "RECEPTIONIST".equalsIgnoreCase(currentUser.getRole());
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}