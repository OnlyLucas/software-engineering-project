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

    /**
     * Gets the user entity.
     *
     * @return The user entity.
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * Sets the user entity.
     *
     * @return The user entity.
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * Gets the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @return The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
