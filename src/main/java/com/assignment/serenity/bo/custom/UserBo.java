package com.assignment.serenity.bo.custom;

import com.assignment.serenity.entity.User;
import com.assignment.serenity.util.Role;

import java.util.ArrayList;

public interface UserBo {
    boolean registerUser(String name, String userName, String email, String password, Role role);

    ArrayList<String> getAllRoll();

    User findByRoll (String selectedRoll) throws Exception;
}
