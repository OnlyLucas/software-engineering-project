package com.flatfusion.backend.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "cleanings", schema = "flatfusion")
public class CleaningEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private UserEntity user;
    @Column(name = "date", nullable = true)
    private Date date;
    @Column(name = "is_completed", nullable = true)
    private Byte isCompleted;
    @Column(name = "completed_at", nullable = true)
    private Timestamp completedAt;
    @ManyToOne
    @JoinColumn(
            name = "cleaning_template"
    )
    private CleaningTemplateEntity cleaningTemplate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CleaningTemplateEntity getCleaningTemplate() {
        return cleaningTemplate;
    }

    public void setCleaningTemplate(CleaningTemplateEntity cleaningTemplate) {
        this.cleaningTemplate = cleaningTemplate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Byte getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Byte isCompleted) {
        this.isCompleted = isCompleted;
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
        CleaningEntity that = (CleaningEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(cleaningTemplate, that.cleaningTemplate) && Objects.equals(date, that.date) && Objects.equals(isCompleted, that.isCompleted) && Objects.equals(completedAt, that.completedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, cleaningTemplate, date, isCompleted, completedAt);
    }
}
