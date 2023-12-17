package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a group membership entity with a unique identifier, associated group, associated user, and creation timestamp.
 */
@Entity
@jakarta.persistence.Table(name = "group_memberships", schema = "flatfusion")
public class GroupMembershipEntity implements EntityInterface {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @ManyToOne
    @JoinColumn(
            name = "group_id"
    )
    private GroupEntity group;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private UserEntity user;

    @Basic
    @Column(name = "created_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity groupId) {
        this.group = groupId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userId) {
        this.user = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Checks whether this group membership entity is equal to another object.
     *
     * @param o The object to compare with.
     * @return {@code true} if the objects are equal; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMembershipEntity that = (GroupMembershipEntity) o;
        return Objects.equals(group, that.group) && Objects.equals(user, that.user) && Objects.equals(createdAt, that.createdAt);
    }

    /**
     * Generates a hash code for this group membership entity.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(group, user, createdAt);
    }

    /**
     * Provides a string representation of this group membership entity.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "GroupMembershipEntity{" +
                "id=" + id +
                ", group=" + group +
                ", user=" + user +
                ", createdAt=" + createdAt +
                '}';
    }
}
