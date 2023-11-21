package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentsChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentsChangeEntityRepository extends JpaRepository<PaymentsChangeEntity, UUID> {
}