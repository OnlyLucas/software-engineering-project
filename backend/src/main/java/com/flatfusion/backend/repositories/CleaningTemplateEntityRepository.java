package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.CleaningTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CleaningTemplateEntityRepository extends JpaRepository<CleaningTemplateEntity, UUID> {
    @Query("SELECT c FROM CleaningTemplateEntity c WHERE c.group.id = :groupId ORDER BY c.createdAt DESC")
    Optional<List<CleaningTemplateEntity>> findByGroupId(@Param("groupId") UUID groupId);
}