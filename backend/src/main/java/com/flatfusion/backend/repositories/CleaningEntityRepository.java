package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.CleaningEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CleaningEntityRepository extends JpaRepository<CleaningEntity, UUID> {
}