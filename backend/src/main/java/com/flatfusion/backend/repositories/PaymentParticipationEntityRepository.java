package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentParticipationEntityRepository extends JpaRepository<PaymentParticipationEntity, UUID> {

    // get a table with
    // 1) User in first column (who owes something to the current user)
    // 2) Accumulated amount this user owes the current user in the second column
    // parameters are the current groupId and the userId of the current user
    @Query("SELECT p.user, SUM(p.participationAmount) AS totalAmount FROM PaymentParticipationEntity p " +
            "WHERE p.group.id = :groupId AND p.isPaid = false AND p.payment.paidByUser.id = :userId AND p.user.id != :userId GROUP BY p.user.id")
    Optional<List<Object[]>> getGetPaymentsGroupedByUser(UUID groupId, UUID userId);

    // get a table with
    // 1) User in first column (who the current user owes something to)
    // 2) Accumulated amount the current user owes this user in the second column
    // parameters are the current groupId and the userId of the current user
    @Query("SELECT p.payment.paidByUser, SUM(p.participationAmount) AS totalAmount FROM PaymentParticipationEntity p " +
            "WHERE p.group.id = :groupId AND p.isPaid = false AND p.user.id = :userId AND p.payment.paidByUser.id != :userId GROUP BY p.payment.paidByUser")
    Optional<List<Object[]>> getOwePaymentsGroupedByUser(UUID groupId, UUID userId);

    // get all Get PaymentParticipations that are involved between the user who gets money and who owes money
    @Query("SELECT p FROM PaymentParticipationEntity p " +
            "WHERE p.group.id = :groupId " +
            "AND p.isPaid = false " +
            "AND p.user.id = :userIdOwe " +
            "AND p.payment.paidByUser.id = :userIdGet")
    Optional<List<PaymentParticipationEntity>> getGetPaymentParticipationsByUserIds(UUID groupId, UUID userIdOwe, UUID userIdGet);

    // get all Owe PaymentParticipations that are involved between the user who gets money and who owes money
    @Query("SELECT p FROM PaymentParticipationEntity p " +
            "WHERE p.group.id = :groupId " +
            "AND p.isPaid = false " +
            "AND p.user.id = :userIdGet " +
            "AND p.payment.paidByUser.id = :userIdOwe")
    Optional<List<PaymentParticipationEntity>> getOwePaymentParticipationsByUserIds(UUID groupId, UUID userIdGet, UUID userIdOwe);

}