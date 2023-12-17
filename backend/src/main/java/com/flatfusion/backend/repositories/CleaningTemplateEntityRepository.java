package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.CleaningTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link CleaningTemplateEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
public interface CleaningTemplateEntityRepository extends JpaRepository<CleaningTemplateEntity, UUID> {

    /**
     * Retrieves a list of cleaning template entities associated with a specified group ID.
     * The result is ordered by creation timestamp in descending order.
     *
     * @param groupId The ID of the group.
     * @return An optional containing the list of cleaning template entities, or an empty optional if none are found.
     */
    @Query("SELECT c FROM CleaningTemplateEntity c WHERE c.group.id = :groupId ORDER BY c.createdAt DESC")
    Optional<List<CleaningTemplateEntity>> findByGroupId(@Param("groupId") UUID groupId);
}