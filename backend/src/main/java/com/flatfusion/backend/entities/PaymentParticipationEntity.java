package com.flatfusion.backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "payment_participations", schema = "flatfusion")
@IdClass(PaymentParticipationEntityPK.class)
public class PaymentParticipationEntity {
    @Id
    @ManyToOne
    @JoinColumn(
            name = "payment_id"
    )
    private PaymentEntity payment;

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity paymentId) {
        this.payment = paymentId;
    }

    @Id
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private UserEntity user;


    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity userId) {
        this.user = userId;
    }

    @Basic
    @Column(name = "participation_amount", nullable = true, precision = 2)
    private BigDecimal participationAmount;

    public BigDecimal getParticipationAmount() {
        return participationAmount;
    }

    public void setParticipationAmount(BigDecimal participationAmount) {
        this.participationAmount = participationAmount;
    }

    @Basic
    @Column(name = "currency_code", nullable = true, length = 3)
    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Basic
    @Column(name = "is_paid", nullable = true)
    private Byte isPaid;

    public Byte getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Byte isPaid) {
        this.isPaid = isPaid;
    }

    @Basic
    @Column(name = "paid_at", nullable = true)
    private Timestamp paidAt;

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
