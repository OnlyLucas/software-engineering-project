package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.CleaningEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link CleaningEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
public interface CleaningEntityRepository extends JpaRepository<CleaningEntity, UUID> {

    /**
     * Retrieves a list of uncompleted cleaning entities associated with a specified cleaning template ID.
     * The result is ordered by date in ascending order.
     *
     * @param templateId The ID of the cleaning template.
     * @return An optional containing the list of uncompleted cleaning entities, or an empty optional if none are found.
     */
    @Query("SELECT c FROM CleaningEntity c WHERE c.cleaningTemplate.id = :templateId AND c.isCompleted = false ORDER BY c.date ASC")
    Optional<List<CleaningEntity>> getUncompletedCleaningsForTemplate(@Param("templateId") UUID templateId);

    @Query("SELECT c FROM CleaningEntity c WHERE c.cleaningTemplate.id = :templateId AND c.isCompleted = false ORDER BY c.date ASC")
    Page<CleaningEntity> getUncompletedCleaningWithSmallestDate(@Param("templateId") UUID templateId, Pageable pageable);
}