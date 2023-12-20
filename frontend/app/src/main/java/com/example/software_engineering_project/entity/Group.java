package com.example.software_engineering_project.entity;

import com.example.software_engineering_project.repository.AppStateRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * The Group class represents a group entity with properties such as ID, name, description,
 * creator, and creation timestamp.
 *
 * Responsibilities:
 * - Model a group entity.
 * - Provide methods for accessing and modifying group properties.
 * - Implement equals, hashCode, and toString methods for object comparison and representation.
 *
 * Note: The class includes annotations for JSON formatting.
 */
public class Group {
    private UUID id;
    private String name;
    private String description;
    private User createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    /**
     * Default constructor for the Group class.
     */
    public Group() {
        // default constructor
    }

    /**
     * Parameterized constructor for creating a Group instance with specified name and description.
     *
     * @param name        The name of the group.
     * @param description The description of the group.
     */
    public Group(String name, String description) {

        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.createdBy = AppStateRepository.getCurrentAppUserLiveData().getValue();
        long currentTimeMillis = System.currentTimeMillis();
        this.createdAt = new Timestamp(currentTimeMillis);

    }

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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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
        Group that = (Group) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdAt, that.createdAt);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdBy, createdAt);
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                '}';
    }
}
