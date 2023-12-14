package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cleanings", schema = "flatfusion")
public class CleaningEntity implements EntityInterface{
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
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private UserEntity user;
    @Column(name = "date", nullable = false)
    private Date date;
    @Column(name = "is_completed", nullable = true)
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean isCompleted = false;

    @Column(name = "completed_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp completedAt;
    @ManyToOne
    @JoinColumn(
            name = "cleaning_template"
    )
    private CleaningTemplateEntity cleaningTemplate;

    public CleaningEntity(){
        //default constructor
    }

    public CleaningEntity(GroupEntity group, UserEntity user, Date date, CleaningTemplateEntity cleaningTemplate){
        this.id = UUID.randomUUID();
        this.group = group;
        this.user = user;
        this.date = date;
        this.isCompleted = false;
        this.cleaningTemplate = cleaningTemplate;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public void setIsCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }

    public CleaningTemplateEntity getCleaningTemplate() {
        return cleaningTemplate;
    }

    public void setCleaningTemplate(CleaningTemplateEntity cleaningTemplate) {
        this.cleaningTemplate = cleaningTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CleaningEntity that = (CleaningEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(user, that.user) && Objects.equals(date, that.date) && Objects.equals(isCompleted, that.isCompleted) && Objects.equals(completedAt, that.completedAt) && Objects.equals(cleaningTemplate, that.cleaningTemplate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, user, date, isCompleted, completedAt, cleaningTemplate);
    }

    @Override
    public String toString() {
        return "CleaningEntity{" +
                "id=" + id +
                ", group=" + group +
                ", user=" + user +
                ", date=" + date +
                ", isCompleted=" + isCompleted +
                ", completedAt=" + completedAt +
                ", cleaningTemplate=" + cleaningTemplate +
                '}';
    }
}
