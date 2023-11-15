package com.flatfusion.backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;


//TODO fix hashCode() and equals() methods
@Entity
@Table(name = "living_groups", schema = "flatfusion")
public class GroupEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    // Name escaping for mysql database
    @Basic
    @Column(name = "group_name", length = 255)
    private String name;
    @Basic
    @Column(name = "group_description", nullable = true, length = 255)
    private String description;
//    @ManyToOne
//    @JoinColumn(
//            name = "created_by",
//            nullable = false
//    )
//    private UserEntity createdByUser;
    @Basic
    @Column(name = "created_at", nullable = true)
    private Timestamp createdAt;

//    @ManyToMany
//    @JoinTable(
//            name = "group_memberships",
//            joinColumns = {@JoinColumn(name = "group_id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id")}
//    )
//    private Set<UserEntity> groupMembers;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//
//    public UserEntity getCreatedBy() {
//        return createdByUser;
//    }
//
//    public void setCreatedBy(UserEntity createdByUser) {
//        this.createdByUser = createdByUser;
//    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

//    public Set<UserEntity> getGroupMembers() {
//        return groupMembers;
//    }
//
//    public void setGroupMembers(Set<UserEntity> groupMembers) {
//        this.groupMembers = groupMembers;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity that = (GroupEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description)  && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdAt);
    }
}
