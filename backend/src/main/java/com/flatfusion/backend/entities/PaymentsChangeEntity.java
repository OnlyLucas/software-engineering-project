package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a change in a financial payment within a group, capturing details such as the modified amount,
 * currency code, and the users involved in the change.
 */
@Entity
@Table(name = "payments_changes", schema = "flatfusion")
public class PaymentsChangeEntity implements EntityInterface{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @ManyToOne
    @JoinColumn(
            name = "payment_id",
            nullable = false
    )
    private PaymentEntity payment;
    @ManyToOne
    @JoinColumn(
            name = "group_id",
            nullable = false
    )
    private GroupEntity group;
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
            name = "changed_by",
            nullable = false
    )
    private UserEntity changedByUser;

    @Basic
    @Column(name = "changed_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp changedAt;

    @Basic
    @Column(name = "payment_name", nullable = false, length = 255)
    private String name;

    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
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

    public void setPaidByUser(UserEntity paidByUser) {
        this.paidByUser = paidByUser;
    }

    public UserEntity getChangedByUser() {
        return changedByUser;
    }

    public void setChangedByUser(UserEntity changedByUser) {
        this.changedByUser = changedByUser;
    }

    public Timestamp getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Timestamp changedAt) {
        this.changedAt = changedAt;
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
        PaymentsChangeEntity that = (PaymentsChangeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(payment, that.payment) && Objects.equals(group, that.group) && Objects.equals(amount, that.amount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(paidByUser, that.paidByUser) && Objects.equals(changedByUser, that.changedByUser) && Objects.equals(changedAt, that.changedAt) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment, group, amount, currencyCode, paidByUser, changedByUser, changedAt, name);
    }

    @Override
    public String toString() {
        return "PaymentsChangeEntity{" +
                "id=" + id +
                ", payment=" + payment +
                ", group=" + group +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", paidByUser=" + paidByUser +
                ", changedByUser=" + changedByUser +
                ", changedAt=" + changedAt +
                ", name='" + name + '\'' +
                '}';
    }
}
