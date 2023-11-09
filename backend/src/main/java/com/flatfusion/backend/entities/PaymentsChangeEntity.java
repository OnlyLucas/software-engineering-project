package com.flatfusion.backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "payments_changes", schema = "flatfusion")
public class PaymentsChangeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @ManyToOne
    @JoinColumn(
            name = "payment_id",
            nullable = false
    )
    private PaymentEntity payment;
    @Basic
    @Column(name = "amount", nullable = false, precision = 2)
    private BigDecimal amount;
    @Basic
    @Column(name = "currency_code", nullable = true, length = 3)
    private String currencyCode;

    @ManyToOne
    @JoinColumn(
            name = "paid_by",
            nullable = false
    )
    private UserEntity paidByUser;

    @ManyToOne
    @JoinColumn(
            name = "changed_by",
            nullable = false
    )
    private UserEntity changedByUser;

    @Basic
    @Column(name = "changed_at", nullable = true)
    private Timestamp changedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPaymentId(PaymentEntity payment) {
        this.payment = payment;
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

    public UserEntity getPaidBy() {
        return paidByUser;
    }

    public void setPaidBy(UserEntity paidByUser) {
        this.paidByUser = paidByUser;
    }

    public UserEntity getChangedBy() {
        return changedByUser;
    }

    public void setChangedBy(UserEntity changedByUser) {
        this.changedByUser = changedByUser;
    }

    public Timestamp getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Timestamp changedAt) {
        this.changedAt = changedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentsChangeEntity that = (PaymentsChangeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(payment, that.payment) && Objects.equals(amount, that.amount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(paidByUser, that.paidByUser) && Objects.equals(changedByUser, that.changedByUser) && Objects.equals(changedAt, that.changedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, payment, amount, currencyCode, paidByUser, changedByUser, changedAt);
    }
}
