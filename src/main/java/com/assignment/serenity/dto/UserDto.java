package com.assignment.serenity.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String userId;
    private String username;
    private String password;
    private String role;

    public UserDto() {}

    public UserDto(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

}