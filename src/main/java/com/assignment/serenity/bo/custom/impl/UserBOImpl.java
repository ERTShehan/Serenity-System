package com.assignment.serenity.bo.custom.impl;

import com.assignment.serenity.bo.custom.UserBo;
import com.assignment.serenity.dao.custom.UserDAO;
import com.assignment.serenity.dao.custom.impl.UserDAOImpl;
import com.assignment.serenity.entity.User;
import com.assignment.serenity.util.PasswordEncryptionUtil;
import com.assignment.serenity.util.Role;

import java.util.ArrayList;

public class UserBOImpl implements UserBo{
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public boolean registerUser(String name, String userName, String email, String password, Role role) {
        if (userDAO.getUserByUsername(userName) != null) {
            return false;
        }

        String hashedPassword = PasswordEncryptionUtil.hashPassword(password);
        String newUserId = userDAO.getNextId(); // new id akak genarate kirima
        User user = new User(newUserId, name, userName, email, hashedPassword, role);
        userDAO.save(user);
        return true;
    }

    @Override
    public ArrayList<String> getAllRoll() {
        return userDAO.getAllRolls();
    }

    @Override
    public User findByRoll(String selectedRoll) throws Exception {
        return userDAO.findByRoll(selectedRoll);
    }
}
