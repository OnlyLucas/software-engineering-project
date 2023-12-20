package request;

import com.example.software_engineering_project.entity.User;

/**
     * This is an Entity for creationg or changing a user.
     * Although the User entity contains a password, it is not send in http requests for security reasons.
     * Therefore, this class is used specifically when the password needs to be transmitted.
     */
public class UserWithPasswordRequest {
    User user;
    String password;

    public UserWithPasswordRequest(){
        // (used for jackson)
    }

    public UserWithPasswordRequest(User user, String password){
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

