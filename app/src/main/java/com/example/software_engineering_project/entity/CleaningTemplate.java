package com.example.software_engineering_project.entity;

import com.example.software_engineering_project.viewmodel.AppStateRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a template for cleaning tasks within a group.
 */
public class CleaningTemplate {
    private UUID id;
    private Group group;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer interval;
    private User createdByUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    private Set<Cleaning> cleanings;

    public CleaningTemplate() {
        //default constructor
    }

    /**
     * Parameterized constructor for creating a cleaning template with specific attributes.
     *
     * @param name        The name of the cleaning template.
     * @param description A description of the cleaning template.
     * @param startDate   The start date for the cleaning template.
     * @param endDate     The end date for the cleaning template.
     * @param interval    The interval between cleaning tasks in days.
     */
    public CleaningTemplate(String name, String description, Date startDate, Date endDate,
                            int interval) {
        this.group = AppStateRepository.getCurrentGroupLiveData().getValue();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interval = interval;
        this.createdByUser = AppStateRepository.getCurrentAppUserLiveData().getValue();
        this.createdAt = new Timestamp(System.currentTimeMillis());

    }

    /**
     * Get the unique identifier for the cleaning template.
     *
     * @return The unique identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set the unique identifier for the cleaning template.
     *
     * @param id The unique identifier to set.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Get the group to which the cleaning template belongs.
     *
     * @return The group.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Set the group to which the cleaning template belongs.
     *
     * @param group The group to set.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Get the name of the cleaning template.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the cleaning template.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the cleaning template.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the cleaning template.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
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

    public Set<Cleaning> getCleanings() {
        return cleanings;
    }

    public void setCleanings(Set<Cleaning> cleanings) {
        this.cleanings = cleanings;
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
        CleaningTemplate that = (CleaningTemplate) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(interval, that.interval) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(createdAt, that.createdAt) && Objects.equals(cleanings, that.cleanings);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, description, startDate, endDate, interval, createdByUser, createdAt, cleanings);
    }

    @Override
    public String toString() {
        return "CleaningTemplate{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", interval=" + interval +
                ", createdByUser=" + createdByUser +
                ", createdAt=" + createdAt +
                ", cleanings=" + cleanings +
                '}';
    }
}