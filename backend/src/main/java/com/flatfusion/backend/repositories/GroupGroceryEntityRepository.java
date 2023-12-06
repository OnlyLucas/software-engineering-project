package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupGroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupGroceryEntityRepository extends JpaRepository<GroupGroceryEntity, UUID> {
    @Query("SELECT g FROM GroupGroceryEntity g WHERE g.group.id = :groupId" +
            " AND g.isCompleted = true ORDER BY g.createdAt DESC")
    Optional<List<GroupGroceryEntity>> findCompletedByGroupId(@Param("groupId") UUID groupId);

    @Query("SELECT g FROM GroupGroceryEntity g WHERE g.group.id = :groupId" +
            " AND g.isCompleted = false ORDER BY g.createdAt DESC")
    Optional<List<GroupGroceryEntity>> findUncompletedByGroupId(@Param("groupId") UUID groupId);
}