package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupGroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupGroceryEntityRepository extends JpaRepository<GroupGroceryEntity, UUID> {
    Optional<List<GroupGroceryEntity>> findAllByGroupIdOrderByCreatedAtDesc(UUID uuid);
}