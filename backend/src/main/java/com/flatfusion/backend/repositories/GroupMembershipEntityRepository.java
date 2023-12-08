package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GroupMembershipEntityRepository extends JpaRepository<GroupMembershipEntity, UUID> {

    GroupMembershipEntity findByGroupIdAndUserId(UUID groupId, UUID userId);
    @Modifying
    @Query("DELETE FROM GroupMembershipEntity gm WHERE gm.user.id = :userId AND gm.group.id = :groupId")
    void deleteGroupMembership(@Param("userId") UUID userId, @Param("groupId") UUID groupId);
}