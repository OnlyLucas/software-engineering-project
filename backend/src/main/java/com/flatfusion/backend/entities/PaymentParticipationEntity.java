package com.flatfusion.backend.entities;

import jakarta.persistence.*;
import org.hibernate.type.NumericBooleanConverter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "payment_participations", schema = "flatfusion")
@IdClass(PaymentParticipationEntityPK.class)
public class PaymentParticipationEntity {
    @EmbeddedId
    PaymentParticipationEntityPK id;
    @ManyToOne
    @MapsId("paymentId")
    @JoinColumn(
            name = "payment_id",
            nullable = false
    )
    private PaymentEntity payment;

    @ManyToOne
    @MapsId("userId")
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
    private Timestamp paidAt;

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity paymentId) {
        this.payment = paymentId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userId) {
        this.user = userId;
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

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
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
        return Objects.equals(payment, that.payment) && Objects.equals(user, that.user) && Objects.equals(participationAmount, that.participationAmount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(isPaid, that.isPaid) && Objects.equals(paidAt, that.paidAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payment, user, participationAmount, currencyCode, isPaid, paidAt);
    }
}
