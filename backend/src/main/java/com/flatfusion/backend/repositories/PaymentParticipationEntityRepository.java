package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentParticipationEntityRepository extends JpaRepository<PaymentParticipationEntity, UUID> {
}