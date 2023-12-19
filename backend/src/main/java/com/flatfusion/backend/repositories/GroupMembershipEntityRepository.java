package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.entities.GroupMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link GroupMembershipEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
public interface GroupMembershipEntityRepository extends JpaRepository<GroupMembershipEntity, UUID> {

    /**
     * Retrieves a {@link GroupMembershipEntity} by the given group and user IDs.
     *
     * @param groupId The ID of the group.
     * @param userId  The ID of the user.
     * @return A {@link GroupMembershipEntity} instance if found, {@code null} otherwise.
     */
    GroupMembershipEntity findByGroupIdAndUserId(UUID groupId, UUID userId);

    /**
     * Deletes a {@link GroupMembershipEntity} by the given group and user IDs.
     *
     * @param userId  The ID of the user.
     * @param groupId The ID of the group.
     */
    @Modifying
    @Query("DELETE FROM GroupMembershipEntity gm WHERE gm.user.id = :userId AND gm.group.id = :groupId")
    void deleteGroupMembership(@Param("userId") UUID userId, @Param("groupId") UUID groupId);

    @Query("SELECT gm.group FROM GroupMembershipEntity gm WHERE gm.user.id = :userId")
    Optional<GroupEntity> findGroupByUserId(@Param("userId") UUID userId);
}