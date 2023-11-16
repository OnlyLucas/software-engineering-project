package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "payments", schema = "flatfusion")
public class PaymentEntity implements EntityInterface{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;
    @Basic
    @Column(name = "currency_code", nullable = true, length = 3)
    private String currencyCode;
    @ManyToOne
    @JoinColumn(
            name = "paid_by",
            nullable = false
    )
    private UserEntity paidByUser;

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

    @Basic
    @Column(name = "payment_name", nullable = false, length = 255)
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public UserEntity getPaidByUser() {
        return paidByUser;
    }

    public void setPaidByUser(UserEntity paidBy) {
        this.paidByUser = paidBy;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity that = (PaymentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(paidByUser, that.paidByUser) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currencyCode, paidByUser, createdByUser, createdAt, name);
    }

}
