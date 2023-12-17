package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link GroupEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
public interface GroupEntityRepository extends JpaRepository<GroupEntity, UUID> {

    /**
     * Retrieves a group entity by its ID, excluding the password attribute of the createdByUser.
     * Uses a custom query with a fetch join to load the createdByUser without its password.
     *
     * @param groupId The ID of the group.
     * @return An optional containing the group entity, or an empty optional if none is found.
     */
    // Custom query with fetch join to exclude password attribute of createdByUser
    @Query("SELECT g FROM GroupEntity g JOIN FETCH g.createdByUser u WHERE g.id = :groupId")
    Optional<GroupEntity> findGroupByIdExcludingCreatedByUserPassword(@Param("groupId") UUID groupId);
}
