package com.assignment.serenity.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptor {
    private static final int WORKLOAD = 12;

    public String encrypt(String plainTextPassword) {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}