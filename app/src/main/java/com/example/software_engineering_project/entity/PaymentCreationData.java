package com.example.software_engineering_project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PaymentCreationData {
    private Payment payment;
    private Map<UUID, BigDecimal> userParticipations;


    public PaymentCreationData(Payment payment){
        this.payment = payment;
        userParticipations = new HashMap();
    }

    public void PaymentCreationData(){}

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @JsonIgnore
    public Set<UUID> getParticipantUserIds() {
        return userParticipations.keySet();
    }

    public Map<UUID, BigDecimal> getUserParticipations() { return userParticipations; }


    public void setUserParticipations(Map<UUID, BigDecimal> userParticipations) { this.userParticipations = userParticipations; }
}

