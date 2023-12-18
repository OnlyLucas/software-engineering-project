package com.example.software_engineering_project.entity;

import com.example.software_engineering_project.viewmodel.AppStateRepository;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

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

    public CleaningTemplate() {
        //defaul constructor
    }

    public CleaningTemplate(String name, String description, Date startDate, Date endDate,
                            int interval) {
        this.group = AppStateRepository.getCurrentGroupLiveData().getValue();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        //TODO Change DB keyword interval
        this.interval = interval;
        this.createdByUser = AppStateRepository.getCurrentAppUserLiveData().getValue();
        this.createdAt = new Timestamp(System.currentTimeMillis());

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

    public String getDescription() {
        return description;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleaningTemplate that = (CleaningTemplate) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(interval, that.interval) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, description, startDate, endDate, interval, createdByUser, createdAt);
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
                '}';
    }
}