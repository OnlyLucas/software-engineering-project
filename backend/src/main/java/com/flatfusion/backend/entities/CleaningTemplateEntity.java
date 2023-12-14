package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cleaning_template", schema = "flatfusion")
public class CleaningTemplateEntity implements EntityInterface{
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
    @Column(name = "task_name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "template_description", nullable = true, length = 255)
    private String description;
    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    //TODO change name to something else than interval (key word)
    @Basic
    @Column(name = "cleaning_interval", nullable = false)
    private Integer interval;
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

    // Cleanings are removed automatically with the removal of the CleaningTemplate
    @OneToMany(mappedBy = "cleaningTemplate", orphanRemoval = true)
    private Set<CleaningEntity> cleanings;

    public CleaningTemplateEntity(){
        // default constructor for Spring
    }

    public CleaningTemplateEntity(GroupEntity group, String name, String description, Date startDate,
                                  Date endDate, Integer interval, UserEntity createdByUser){
        this.id = UUID.randomUUID();
        this.group = group;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interval = interval;
        this.createdByUser = createdByUser;
        this.createdAt = Timestamp.from(Instant.now());
        this.cleanings = null;
    }

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

    public void setGroup(GroupEntity group) {
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

    public Set<CleaningEntity> getCleanings() {
        return cleanings;
    }

    public void setCleanings(Set<CleaningEntity> cleanings) {
        this.cleanings = cleanings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleaningTemplateEntity that = (CleaningTemplateEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(interval, that.interval) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(createdAt, that.createdAt) && Objects.equals(cleanings, that.cleanings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, name, description, startDate, endDate, interval, createdByUser, createdAt, cleanings);
    }

    @Override
    public String toString() {
        return "CleaningTemplateEntity{" +
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
