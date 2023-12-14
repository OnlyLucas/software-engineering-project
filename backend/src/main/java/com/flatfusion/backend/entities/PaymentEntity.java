package com.flatfusion.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "payments", schema = "flatfusion")
public class PaymentEntity implements EntityInterface{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "VARCHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;
    @ManyToOne
    @JoinColumn(
            name = "group_id",
            nullable = false
    )
    private GroupEntity group;
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
            name = "created_by",
            nullable = false
    )
    private UserEntity createdByUser;

    // PaymentParticipations are removed automatically with the removal of the Payment
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentParticipationEntity> paymentParticipations;

    @Basic
    @Column(name = "created_at", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @Basic
    @Column(name = "payment_name", nullable = false, length = 255)
    private String name;

    public PaymentEntity() {
        //default constructor
    }

    public PaymentEntity(GroupEntity group, BigDecimal amount, UserEntity paidByUser, UserEntity createdByUser, String name) {
        this.id = UUID.randomUUID();
        this.group = group;
        this.amount = amount;
        this.paidByUser = paidByUser;
        this.createdByUser = createdByUser;
        this.createdAt = Timestamp.from(Instant.now());
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
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

    public UserEntity getPaidByUser() {
        return paidByUser;
    }

    public void setPaidByUser(UserEntity paidByUser) {
        this.paidByUser = paidByUser;
    }

    public UserEntity getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(UserEntity createdByUser) {
        this.createdByUser = createdByUser;
    }

    public Set<PaymentParticipationEntity> getPaymentParticipations() {
        return paymentParticipations;
    }

    public void setPaymentParticipations(Set<PaymentParticipationEntity> paymentParticipations) {
        this.paymentParticipations = paymentParticipations;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity that = (PaymentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(group, that.group) && Objects.equals(amount, that.amount) && Objects.equals(currencyCode, that.currencyCode) && Objects.equals(paidByUser, that.paidByUser) && Objects.equals(createdByUser, that.createdByUser) && Objects.equals(paymentParticipations, that.paymentParticipations) && Objects.equals(createdAt, that.createdAt) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, amount, currencyCode, paidByUser, createdByUser, paymentParticipations, createdAt, name);
    }

    @Override
    public String
    toString() {
        return "PaymentEntity{" +
                "id=" + id +
                ", group=" + group +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", paidByUser=" + paidByUser +
                ", createdByUser=" + createdByUser +
                ", paymentParticipations=" + paymentParticipations +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                '}';
    }
}
