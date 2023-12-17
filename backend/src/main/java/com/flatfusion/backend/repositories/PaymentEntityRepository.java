package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link PaymentEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
public interface PaymentEntityRepository extends JpaRepository<PaymentEntity, UUID> {

    /**
     * Retrieves a list of payment entities by group ID.
     *
     * @param groupId The ID of the group.
     * @return An optional containing the list of payment entities, or an empty optional if none are found.
     */
    @Query("SELECT p FROM PaymentEntity p WHERE p.group.id = :groupId ORDER BY p.createdAt DESC")
    Optional<List<PaymentEntity>> findByGroupId(@Param("groupId") UUID groupId);
}