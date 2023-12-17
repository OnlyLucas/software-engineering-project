package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentsChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link PaymentsChangeEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
public interface PaymentsChangeEntityRepository extends JpaRepository<PaymentsChangeEntity, UUID> {
}