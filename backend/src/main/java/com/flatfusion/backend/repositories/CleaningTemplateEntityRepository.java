package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.CleaningTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CleaningTemplateEntityRepository extends JpaRepository<CleaningTemplateEntity, UUID> {
}