package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, UUID> {
    @Query("SELECT p FROM PaymentEntity p WHERE p.group.id = :groupId ORDER BY p.createdAt DESC")
    Optional<List<PaymentEntity>> findByGroupId(@Param("groupId") UUID groupId);
}