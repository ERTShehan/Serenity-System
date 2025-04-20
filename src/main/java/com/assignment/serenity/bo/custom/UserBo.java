package com.assignment.serenity.bo.custom;

import com.assignment.serenity.bo.SuperBo;
import com.assignment.serenity.dto.UserDto;
import com.assignment.serenity.exception.*;

import java.io.IOException;

public interface UserBo extends SuperBo {
    boolean saveUser(UserDto dto) throws UserNameDuplicateException, MissingFieldException, IOException;
    UserDto authenticate(String username, String password) throws InvalidCredentialException, MissingFieldException, IOException;
}