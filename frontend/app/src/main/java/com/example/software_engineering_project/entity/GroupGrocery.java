package com.example.software_engineering_project.entity;

import com.example.software_engineering_project.repository.AppStateRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a grocery item within a group with information such as name, creator, creation timestamp,
 * completion status, and completion details.
 */
public class GroupGrocery {
    private UUID id;
    private Group group;
    private String name;
    private User createdByUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;
    private Boolean isCompleted = false;
    private User completedByUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp completedAt;

    /**
     * Constructs a new GroupGrocery with the given name. The group, creator, and creation timestamp
     * are automatically set based on the current user and group.
     *
     * @param name The name of the grocery item.
     */
    public GroupGrocery(String name) {

        this.id = UUID.randomUUID();
        this.group = AppStateRepository.getCurrentGroupLiveData().getValue();
        this.createdByUser = AppStateRepository.getCurrentAppUserLiveData().getValue();
        this.name = name;
        long currentTimeMillis = System.currentTimeMillis();
        this.createdAt = new Timestamp(currentTimeMillis);

    }

    /**
     * Default constructor for GroupGrocery.
     */
    public GroupGrocery() {
        // Default constructor
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public User getCompletedByUser() {
        return completedByUser;
    }

    public void setCompletedByUser(User completedByUser) {
        this.completedByUser = completedByUser;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    /**
     * Mark the grocery item as completed. Updates completion status, user, and completion timestamp.
     */
    public void setCompleted() {
        this.isCompleted = true;
        this.completedByUser = AppStateRepository.getCurrentAppUserLiveData().getValue();
        long currentTimeMillis = System.currentTimeMillis();
        this.completedAt = new Timestamp(currentTimeMillis);
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
        GroupGrocery that = (GroupGrocery) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(name, that.name) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(createdAt, that.createdAt) && Objects.equals(isCompleted, that.isCompleted) && Objects.equals(completedByUser, that.completedByUser) && Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, createdByUser, createdAt, isCompleted, completedByUser, completedAt);
    }

    @Override
    public String toString() {
        return "GroupGrocery{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", createdByUser=" + createdByUser +
                ", createdAt=" + createdAt +
                ", isCompleted=" + isCompleted +
                ", completedByUser=" + completedByUser +
                ", completedAt=" + completedAt +
                '}';
    }
}