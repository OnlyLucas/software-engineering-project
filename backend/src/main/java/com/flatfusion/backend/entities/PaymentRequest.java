package com.flatfusion.backend.entities;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PaymentRequest {
    private PaymentEntity payment;
    private Map<UUID, BigDecimal> userParticipations;

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public Set<UUID> getParticipantUserIds() {
        return userParticipations.keySet();
    }
}
