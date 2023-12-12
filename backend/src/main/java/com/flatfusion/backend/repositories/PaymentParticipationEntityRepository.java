package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentParticipationEntityRepository extends JpaRepository<PaymentParticipationEntity, UUID> {
    @Query("SELECT p.user, SUM(p.participationAmount) AS totalAmount FROM PaymentParticipationEntity p " +
            "WHERE p.group.id = :groupId AND p.payment.paidByUser.id = :userId GROUP BY p.user.id")
    Optional<List<Object[]>> findByGroupIdAndPaymentCreatedByUserId(UUID groupId, UUID userId);

    @Query("SELECT p.payment.paidByUser, SUM(p.participationAmount) AS totalAmount FROM PaymentParticipationEntity p " +
            "WHERE p.group.id = :groupId AND p.user.id = :userId AND p.payment.paidByUser.id != :userId GROUP BY p.payment.paidByUser")
    Optional<List<Object[]>> findByGroupIdAndParticipation(UUID groupId, UUID userId);
}