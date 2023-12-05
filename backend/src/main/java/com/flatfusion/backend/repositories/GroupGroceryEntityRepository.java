package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupGroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupGroceryEntityRepository extends JpaRepository<GroupGroceryEntity, UUID> {
    @Query("SELECT g FROM GroupGroceryEntity g WHERE g.groupId = :groupId" +
            " AND g.isCompleted = true ORDER BY g.timestamp DESC")
    Optional<List<GroupGroceryEntity>> findCompletedByGroupId(UUID uuid);

    @Query("SELECT g FROM GroupGroceryEntity g WHERE g.groupId = :groupId" +
            " AND g.isCompleted = false ORDER BY g.timestamp DESC")
    Optional<List<GroupGroceryEntity>> findUncompletedByGroupId(UUID uuid);
}