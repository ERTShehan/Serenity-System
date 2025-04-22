package com.assignment.serenity.dao.custom;

import com.assignment.serenity.entity.User;

import java.util.ArrayList;

public interface UserDAO extends CrudDAO <User> {
    User getUserByUsername(String userName);

    ArrayList<String> getAllRolls();

    User findByRoll(String selectedId) throws Exception;
}
