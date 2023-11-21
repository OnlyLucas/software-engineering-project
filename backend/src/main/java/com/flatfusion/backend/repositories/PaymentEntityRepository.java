package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, UUID> {
}