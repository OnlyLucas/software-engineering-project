package com.flatfusion.backend.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flatfusion.backend.entities.PaymentEntity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class PaymentCreationRequest {
    private PaymentEntity payment;
    private Map<UUID, BigDecimal> userParticipations;




    @JsonCreator
    public PaymentCreationRequest(@JsonProperty("payment") PaymentEntity payment,
                                  @JsonProperty("userParticipations") Map<UUID, BigDecimal> userParticipations) {
        this.payment = payment;
        this.userParticipations = userParticipations;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public Set<UUID> getParticipantUserIds() {
        return userParticipations.keySet();
    }

    public Map<UUID, BigDecimal> getUserParticipations() {
        return userParticipations;
    }

    public void setUserParticipations(Map<UUID, BigDecimal> userParticipations) {
        this.userParticipations = userParticipations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentCreationRequest that = (PaymentCreationRequest) o;
        return Objects.equals(payment, that.payment) && Objects.equals(userParticipations, that.userParticipations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payment, userParticipations);
    }

    @Override
    public String toString() {
        return "PaymentCreationRequest{" +
                "payment=" + payment.toString() +
                ", userParticipations=" + userParticipations.entrySet().toString() +
                '}';
    }
}
