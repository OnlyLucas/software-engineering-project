package com.flatfusion.backend.requests;

import com.flatfusion.backend.entities.UserEntity;

/**
 * This is an Entity for creationg or changing a user.
 * Although the User entity contains a password, it is not send in http requests for security reasons.
 * Therefore, this class is used specifically when the password needs to be transmitted.
 */
public class UserWithPasswordRequest {
    UserEntity user;
    String password;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
