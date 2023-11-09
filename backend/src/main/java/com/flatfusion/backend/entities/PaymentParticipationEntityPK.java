package com.flatfusion.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PaymentParticipationEntityPK implements Serializable {
    @Column(name = "payment_id")
    String groupId;

    @Column(name = "user_id")
    String userId;
}
