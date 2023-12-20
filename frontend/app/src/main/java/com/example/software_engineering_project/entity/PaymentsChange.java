package com.example.software_engineering_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a change in payment details within a group.
 */
public class PaymentsChange {
    private UUID id;
    private Payment payment;
    private Group group;
    private BigDecimal amount;
    private String currencyCode;
    private User paidByUser;
    private User changedByUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp changedAt;
    private String name;

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

    public User getChangedByUser() {
        return changedByUser;
    }

    public void setChangedByUser(User changedByUser) {
        this.changedByUser = changedByUser;
    }

    public Timestamp getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Timestamp changedAt) {
        this.changedAt = changedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentsChange that = (PaymentsChange) o;
        return Objects.equals(id, that.id) && Objects.equals(payment, that.payment) && Objects.equals(group, that.group) && Objects.equals(amount, that.amount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(paidByUser, that.paidByUser) && Objects.equals(changedByUser, that.changedByUser) && Objects.equals(changedAt, that.changedAt) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment, group, amount, currencyCode, paidByUser, changedByUser, changedAt, name);
    }

    @Override
    public String toString() {
        return "PaymentsChange{" +
                "id=" + id +
                ", payment=" + payment +
                ", group=" + group +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", paidByUser=" + paidByUser +
                ", changedByUser=" + changedByUser +
                ", changedAt=" + changedAt +
                ", name='" + name + '\'' +
                '}';
    }
}