package com.example.software_engineering_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.UUID;
import java.sql.Timestamp;

public class User {
    private UUID id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    private Boolean isActive;

    public User(){
        //default constructor
    }

    public User(String firstName, String lastName, String eMail, String hashedPassword) {
        //TODO handle password
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = eMail;
        this.password = hashedPassword;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    // TODO check if we should just make Username mandatory
    /***
     * This method returns the most suited name of the user to display for the ui.
     * It checks for null values in the attributes that are suited for display.
     * If the combination is not displayable because of null values, the next not-null combination will be given out
     *
     * Order: Username --> First name + Last name --> First name
     * @return Name of the user to display in the UI
     */
    @JsonIgnore
    public String getDisplayName(){
        if (this.username != null){
            return this.username;
        } else if (this.lastName != null){
            // example: "Jane D."
            return this.firstName + " " + this.lastName.charAt(0) + ".";
        } else{
            return this.firstName;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(createdAt, user.createdAt) && Objects.equals(isActive, user.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, username, firstName, lastName, createdAt, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdAt=" + createdAt +
                ", isActive=" + isActive +
                '}';
    }
}
