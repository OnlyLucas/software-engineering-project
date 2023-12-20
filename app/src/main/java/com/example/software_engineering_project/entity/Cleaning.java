package com.example.software_engineering_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a cleaning task associated with a user, group, and cleaning template.
 * <p>
 * Each cleaning task includes information such as the ID, associated group, user performing
 * the cleaning, cleaning template, date scheduled, completion status, and completion timestamp.
 */
public class Cleaning {
    private UUID id;
    private Group group;
    private User user;
    private CleaningTemplate cleaningTemplate;
    private Date date;
    private Boolean isCompleted = false;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp completedAt;

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

    public CleaningTemplate getCleaningTemplate() {
        return cleaningTemplate;
    }

    public void setCleaningTemplate(CleaningTemplate cleaningTemplate) {

        this.cleaningTemplate = cleaningTemplate;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two cleaning tasks are considered equal if they have the same ID, associated group, user,
     * cleaning template, date, completion status, and completion timestamp.
     *
     * @param o The reference object with which to compare.
     * @return {@code true} if this object is equal to the specified object;
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cleaning cleaning = (Cleaning) o;
        return Objects.equals(id, cleaning.id) && Objects.equals(group, cleaning.group) && Objects.equals(user, cleaning.user) && Objects.equals(cleaningTemplate, cleaning.cleaningTemplate) && Objects.equals(date, cleaning.date) && Objects.equals(isCompleted, cleaning.isCompleted) && Objects.equals(completedAt, cleaning.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, user, cleaningTemplate, date, isCompleted, completedAt);
    }

    @Override
    public String toString() {
        return "Cleaning{" +
                "id=" + id +
                ", group=" + group +
                ", user=" + user +
                ", cleaningTemplate=" + cleaningTemplate +
                ", date=" + date +
                ", isCompleted=" + isCompleted +
                ", completedAt=" + completedAt +
                '}';
    }

    /**
     * Marks the cleaning as completed and sets the completion timestamp.
     * <p>
     * This method updates the completion status to true and records the current
     * timestamp as the completion time.
     */
    public void setCompleted() {
        this.isCompleted = true;
        long currentTimeMillis = System.currentTimeMillis();
        this.completedAt = new Timestamp(currentTimeMillis);
    }
}