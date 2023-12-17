package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a group grocery entity with a unique identifier, group association, name, creator, creation timestamp,
 * completion status, completion timestamp, and user who completed the grocery item.
 */
@Entity
@Table(name = "group_groceries", schema = "flatfusion")
public class GroupGroceryEntity implements EntityInterface{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @ManyToOne
    @JoinColumn(
            name = "group_id",
            nullable = false
    )
    private GroupEntity group;
    @Basic
    @Column(name = "grocery_name", nullable = false, length = 255)
    private String name;
    @ManyToOne
    @JoinColumn(
            name = "created_by",
            nullable = false
    )
    private UserEntity createdByUser;

    @Basic
    @Column(name = "created_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;
    @Basic
    @Column(name = "is_completed")
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean isCompleted = false;

    @ManyToOne
    @JoinColumn(
            name = "completed_by"
    )
    private UserEntity completedByUser;

    @Basic
    @Column(name = "completed_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp completedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(UserEntity createdByUser) {
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

    public UserEntity getCompletedByUser() {
        return completedByUser;
    }

    public void setCompletedByUser(UserEntity completedByUser) {
        this.completedByUser = completedByUser;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    /**
     * Checks whether this group grocery entity is equal to another object.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupGroceryEntity that = (GroupGroceryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(name, that.name) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(createdAt, that.createdAt) && Objects.equals(isCompleted, that.isCompleted) && Objects.equals(completedByUser, that.completedByUser) && Objects.equals(completedAt, that.completedAt);
    }

    /**
     * Generates a hash code for this group grocery entity.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, createdByUser, createdAt, isCompleted, completedByUser, completedAt);
    }

    /**
     * Provides a string representation of this group grocery entity.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "GroupGroceryEntity{" +
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
