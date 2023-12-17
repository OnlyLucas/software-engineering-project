package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a user's participation in a financial payment within a group, capturing details such as the
 * participation amount, currency, and payment status.
 */
@Entity
@jakarta.persistence.Table(name = "payment_participations", schema = "flatfusion")
public class PaymentParticipationEntity implements EntityInterface {
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

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private UserEntity user;

    @Basic
    @Column(name = "participation_amount", nullable = false, precision = 2)
    private BigDecimal participationAmount;

    @Basic
    @Column(name = "currency_code", nullable = true, length = 3)
    private String currencyCode;

    @Basic
    @Column(name = "is_paid", nullable = true)
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean isPaid = false;

    @Basic
    @Column(name = "paid_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp paidAt;

    /**
     * Default constructor for the PaymentParticipationEntity.
     */
    public PaymentParticipationEntity() {
    }

    /**
     * Parameterized constructor for creating a PaymentParticipationEntity.
     *
     * @param payment             The payment to which the participation belongs.
     * @param group               The group associated with the payment participation.
     * @param user                The user participating in the payment.
     * @param participationAmount The monetary amount of the user's participation.
     */
    public PaymentParticipationEntity(PaymentEntity payment, GroupEntity group, UserEntity user, BigDecimal participationAmount) {
        this.id = UUID.randomUUID();
        this.payment = payment;
        this.group = group;
        this.user = user;
        this.participationAmount = participationAmount;
        this.currencyCode = currencyCode;
        this.isPaid = false;
    }

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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public BigDecimal getParticipationAmount() {
        return participationAmount;
    }

    public void setParticipationAmount(BigDecimal participationAmount) {
        this.participationAmount = participationAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean paid) {
        isPaid = paid;
    }

    public Timestamp getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Timestamp paidAt) {
        this.paidAt = paidAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentParticipationEntity that = (PaymentParticipationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(payment, that.payment) && Objects.equals(group, that.group) && Objects.equals(user, that.user) && Objects.equals(participationAmount, that.participationAmount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(isPaid, that.isPaid) && Objects.equals(paidAt, that.paidAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment, group, user, participationAmount, currencyCode, isPaid, paidAt);
    }

    @Override
    public String toString() {
        return "PaymentParticipationEntity{" +
                "id=" + id +
                ", payment=" + payment +
                ", group=" + group +
                ", user=" + user +
                ", participationAmount=" + participationAmount +
                ", currencyCode='" + currencyCode + '\'' +
                ", isPaid=" + isPaid +
                ", paidAt=" + paidAt +
                '}';
    }
}
