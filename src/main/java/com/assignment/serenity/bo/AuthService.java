package com.assignment.serenity.bo;

import com.assignment.serenity.dao.custom.UserDAO;
import com.assignment.serenity.dao.custom.impl.UserDAOImpl;
import com.assignment.serenity.entity.User;
import com.assignment.serenity.util.PasswordEncryptionUtil;
import com.assignment.serenity.util.Role;

public class AuthService {
    private final UserDAO userDAO = new UserDAOImpl();

    public Role authenticateUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && PasswordEncryptionUtil.checkPassword(password, user.getPassword())) {
            return user.getRole();
        }
        return null;
    }
}
