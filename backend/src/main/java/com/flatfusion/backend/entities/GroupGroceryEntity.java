package com.flatfusion.backend.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "group_groceries", schema = "flatfusion")
public class GroupGroceryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @ManyToOne
    @JoinColumn(
            name = "group_id"
    )
    private GroupEntity group;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @ManyToOne
    @JoinColumn(
            name = "created_by"
    )
    private UserEntity createdByUser;

    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;
    @Basic
    @Column(name = "is_completed", nullable = true)
    private Byte isCompleted;

    @ManyToOne
    @JoinColumn(
            name = "completed_by"
    )
    private UserEntity completedByUser;

    @Basic
    @Column(name = "completed_at", nullable = true)
    private Timestamp completedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Byte getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Byte isCompleted) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupGroceryEntity that = (GroupGroceryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(name, that.name) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(createdAt, that.createdAt) && Objects.equals(isCompleted, that.isCompleted) && Objects.equals(completedByUser, that.completedByUser) && Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, createdByUser, createdAt, isCompleted, completedByUser, completedAt);
    }
}
