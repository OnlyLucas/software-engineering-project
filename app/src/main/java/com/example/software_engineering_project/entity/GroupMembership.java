package com.example.software_engineering_project.entity;

import com.example.software_engineering_project.repository.AppStateRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a membership in a group, indicating the association between a user and a group,
 * along with the timestamp of when the membership was created.
 */
public class GroupMembership {
    private UUID id;
    private Group group;
    private User user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    public GroupMembership() {
        // default constructor
    }

    public GroupMembership(User user) {

        this.id = UUID.randomUUID();
        this.user = user;
        this.group = AppStateRepository.getCurrentGroupLiveData().getValue();
        long currentTimeMillis = System.currentTimeMillis();
        this.createdAt = new Timestamp(currentTimeMillis);

    }

    public GroupMembership(User user, Group group) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.group = group;
        long currentTimeMillis = System.currentTimeMillis();
        this.createdAt = new Timestamp(currentTimeMillis);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMembership that = (GroupMembership) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(user, that.user) && Objects.equals(createdAt, that.createdAt);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, group, user, createdAt);
    }

    @Override
    public String toString() {
        return "GroupMembership{" +
                "id=" + id +
                ", group=" + group +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}