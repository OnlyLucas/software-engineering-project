package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.CleaningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CleaningEntityRepository extends JpaRepository<CleaningEntity, UUID> {
    @Query("SELECT c FROM CleaningEntity c WHERE c.cleaningTemplate.id = :templateId AND c.isCompleted = false ORDER BY c.date ASC")
    Optional<List<CleaningEntity>> getUncompletedCleaningsForTemplate(@Param("templateId") UUID templateId);
}