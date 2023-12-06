package com.example.software_engineering_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class PaymentParticipation {
    private UUID id;
    private Payment payment;
    private User user;
    private BigDecimal participationAmount;
    private String currencyCode;
    private Boolean isPaid = false;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp paidAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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
        PaymentParticipation that = (PaymentParticipation) o;
        return Objects.equals(id, that.id) && Objects.equals(payment, that.payment) && Objects.equals(user, that.user) && Objects.equals(participationAmount, that.participationAmount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(isPaid, that.isPaid) && Objects.equals(paidAt, that.paidAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment, user, participationAmount, currencyCode, isPaid, paidAt);
    }

    @Override
    public String toString() {
        return "PaymentParticipation{" +
                "id=" + id +
                ", payment=" + payment +
                ", user=" + user +
                ", participationAmount=" + participationAmount +
                ", currencyCode='" + currencyCode + '\'' +
                ", isPaid=" + isPaid +
                ", paidAt=" + paidAt +
                '}';
    }
}