package com.flatfusion.backend.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "group_memberships", schema = "flatfusion")
@IdClass(GroupMembershipEntityPK.class)
public class GroupMembershipEntity {
    @Id
    @ManyToOne
    @JoinColumn(
            name = "group_id"
    )
    private GroupEntity group;

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity groupId) {
        this.group = groupId;
    }

    @Id
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userId) {
        this.user = userId;
    }

    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupMembershipEntity that = (GroupMembershipEntity) o;
        return Objects.equals(group, that.group) && Objects.equals(user, that.user) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, user, createdAt);
    }
}
