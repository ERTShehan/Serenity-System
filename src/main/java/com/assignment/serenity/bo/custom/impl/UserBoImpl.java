package com.assignment.serenity.bo.custom.impl;

import com.assignment.serenity.bo.custom.UserBo;
import com.assignment.serenity.dao.DaoFactory;
import com.assignment.serenity.dao.custom.UserDao;
import com.assignment.serenity.dto.UserDto;
import com.assignment.serenity.entity.User;
import com.assignment.serenity.exception.*;
import com.assignment.serenity.util.PasswordEncryptor;

import java.io.IOException;

public class UserBoImpl implements UserBo {
    private final UserDao userDao;
    private final PasswordEncryptor passwordEncryptor;

    public UserBoImpl() {
        this.userDao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.USER);
        this.passwordEncryptor = new PasswordEncryptor();
    }

    @Override
    public boolean saveUser(UserDto dto) throws UserNameDuplicateException, MissingFieldException, IOException {
        validateUserDto(dto);

        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncryptor.encrypt(dto.getPassword()));
        user.setRole(dto.getRole());

        return userDao.save(user);
    }

    @Override
    public UserDto authenticate(String username, String password) throws InvalidCredentialException, MissingFieldException, IOException {
        if (username == null || username.trim().isEmpty()) {
            throw new MissingFieldException("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new MissingFieldException("Password is required");
        }

        User user = userDao.authenticate(username, password);
        if (user == null || !passwordEncryptor.checkPassword(password, user.getPassword())) {
            throw new InvalidCredentialException("Invalid username or password");
        }

        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                null,
                user.getRole()
        );
    }

    private void validateUserDto(UserDto dto) throws MissingFieldException {
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            throw new MissingFieldException("Username is required");
        }
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new MissingFieldException("Password is required");
        }
        if (dto.getRole() == null || dto.getRole().trim().isEmpty()) {
            throw new MissingFieldException("Role is required");
        }
    }
}