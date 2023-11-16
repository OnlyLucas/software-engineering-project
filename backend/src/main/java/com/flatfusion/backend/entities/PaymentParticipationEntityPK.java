package com.flatfusion.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PaymentParticipationEntityPK implements Serializable {

    @Column(name = "payment_id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    String paymentId;

    @Column(name = "user_id")
    String userId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentParticipationEntityPK that = (PaymentParticipationEntityPK) o;
        return Objects.equals(paymentId, that.paymentId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, userId);
    }
}


