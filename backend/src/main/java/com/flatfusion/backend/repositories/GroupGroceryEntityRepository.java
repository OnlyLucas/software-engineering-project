package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupGroceryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Repository interface for accessing and managing {@link GroupGroceryEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
public interface GroupGroceryEntityRepository extends JpaRepository<GroupGroceryEntity, UUID> {

    /**
     * Retrieves a list of completed group grocery entities by group ID.
     *
     * @param groupId The ID of the group.
     * @return An optional containing the list of completed group grocery entities,
     *         or an empty optional if none are found.
     */
    @Query("SELECT g FROM GroupGroceryEntity g WHERE g.group.id = :groupId" +
            " AND g.isCompleted = true ORDER BY g.completedAt DESC")
    Optional<List<GroupGroceryEntity>> findCompletedByGroupId(@Param("groupId") UUID groupId);

    /**
     * Retrieves a list of uncompleted group grocery entities by group ID.
     *
     * @param groupId The ID of the group.
     * @return An optional containing the list of uncompleted group grocery entities,
     *         or an empty optional if none are found.
     */
    @Query("SELECT g FROM GroupGroceryEntity g WHERE g.group.id = :groupId" +
            " AND g.isCompleted = false ORDER BY g.createdAt DESC")
    Optional<List<GroupGroceryEntity>> findUncompletedByGroupId(@Param("groupId") UUID groupId);
}