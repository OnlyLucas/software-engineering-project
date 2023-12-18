package com.flatfusion.backend.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flatfusion.backend.entities.PaymentEntity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a request for creating a payment with associated user participations.
 */
public class PaymentCreationRequest {
    private PaymentEntity payment;
    private Map<UUID, BigDecimal> userParticipations;



    /**
     * Constructs a new PaymentCreationRequest.
     *
     * @param payment             The payment entity to be created.
     * @param userParticipations  A map of user IDs to their participations in the payment.
     */
    @JsonCreator
    public PaymentCreationRequest(@JsonProperty("payment") PaymentEntity payment,
                                  @JsonProperty("userParticipations") Map<UUID, BigDecimal> userParticipations) {
        this.payment = payment;
        this.userParticipations = userParticipations;
    }

    /**
     * Gets the payment entity to be created.
     *
     * @return The payment entity.
     */
    public PaymentEntity getPayment() {
        return payment;
    }

    /**
     * Sets the payment entity to be created.
     *
     * @param payment The payment entity to set.
     */
    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    /**
     * Gets a set of user IDs participating in the payment.
     *
     * @return A set of user IDs.
     */
    public Set<UUID> getParticipantUserIds() {
        return userParticipations.keySet();
    }

    /**
     * Gets the map of user IDs to their participations.
     *
     * @return A map of user IDs to their participations.
     */
    public Map<UUID, BigDecimal> getUserParticipations() {
        return userParticipations;
    }

    /**
     * Sets the map of user IDs to their participations.
     *
     * @param userParticipations The map of user IDs to their participations to set.
     */
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
