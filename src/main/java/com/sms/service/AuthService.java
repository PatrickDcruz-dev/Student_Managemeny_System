package com.sms.service;

import com.sms.dao.UserDAO;
import com.sms.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());
    private static User currentUser = null;

    private final UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User register(String username, String password, String confirmPassword) throws SQLException {

        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new IllegalArgumentException("Please confirm your password.");
        }

        String trimmedUsername = username.trim();

        if (trimmedUsername.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long.");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long.");
        }
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        if (userDAO.existsByUsername(trimmedUsername)) {
            throw new IllegalArgumentException("Username '" + trimmedUsername + "' is already taken.");
        }

        String salt = BCrypt.gensalt(12);
        String passwordHash = BCrypt.hashpw(password, salt);

        User newUser = new User(trimmedUsername, passwordHash);
        boolean created = userDAO.createUser(newUser);

        if (!created) {
            throw new SQLException("Failed to create user account.");
        }

        LOGGER.info("User registered successfully: " + trimmedUsername);
        return newUser;
    }

    public User login(String username, String password) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter your username.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter your password.");
        }

        String trimmedUsername = username.trim();
        User user = userDAO.findByUsername(trimmedUsername);

        if (user == null) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        boolean passwordMatches = false;
        try {
            passwordMatches = BCrypt.checkpw(password, user.getPasswordHash());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error checking BCrypt hash for user: " + trimmedUsername, e);
        }

        if (!passwordMatches) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        currentUser = user;
        LOGGER.info("User logged in successfully: " + trimmedUsername);
        return user;
    }

    public static void logout() {
        if (currentUser != null) {
            LOGGER.info("User logged out: " + currentUser.getUsername());
        }
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
