package com.assignment.serenity.dao.custom;

import com.assignment.serenity.dao.SuperDao;
import com.assignment.serenity.entity.User;
import com.assignment.serenity.exception.UserNameDuplicateException;

import java.io.IOException;

public interface UserDao extends SuperDao {
    boolean save(User user) throws UserNameDuplicateException, IOException;
    User search(String username) throws IOException;
    User authenticate(String username, String password) throws IOException;
}