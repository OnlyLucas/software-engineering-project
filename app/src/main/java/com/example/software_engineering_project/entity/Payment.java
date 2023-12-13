package com.example.software_engineering_project.entity;

import com.example.software_engineering_project.viewmodel.UserViewModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.OneToMany;

public class Payment {
    private UUID id;
    private Group group;
    private BigDecimal amount;
    private String currencyCode;
    private User paidByUser;
    private User createdByUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;
    private String name;

    @OneToMany(mappedBy = "payment")
    private Set<PaymentParticipation> paymentParticipations;

    public Payment() {
        // Default constructor
    }
    public Payment(BigDecimal amount, String name){
        this.id = UUID.randomUUID();
        this.group = UserViewModel.getCurrentGroup().getValue();
        this.amount = amount;
        this.currencyCode = "EUR";
        User currentUser = UserViewModel.getCurrentAppUser().getValue();
        this.paidByUser = currentUser;
        this.createdByUser = currentUser;
        long currentTimeMillis = System.currentTimeMillis();
        this.createdAt  = new Timestamp(currentTimeMillis);
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public User getPaidByUser() {
        return paidByUser;
    }

    public void setPaidByUser(User paidByUser) {
        this.paidByUser = paidByUser;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PaymentParticipation> getPaymentParticipations() {
        return paymentParticipations;
    }

    public void setPaymentParticipations(Set<PaymentParticipation> paymentParticipations) {
        this.paymentParticipations = paymentParticipations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return id.equals(payment.id) && group.equals(payment.group) && Objects.equals(amount, payment.amount) && Objects.equals(currencyCode, payment.currencyCode) && Objects.equals(paidByUser, payment.paidByUser) && createdByUser.equals(payment.createdByUser) && Objects.equals(createdAt, payment.createdAt) && Objects.equals(name, payment.name) && Objects.equals(paymentParticipations, payment.paymentParticipations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, amount, currencyCode, paidByUser, createdByUser, createdAt, name, paymentParticipations);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", group=" + group +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", paidByUser=" + paidByUser +
                ", createdByUser=" + createdByUser +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", paymentParticipations=" + paymentParticipations +
                '}';
    }
}