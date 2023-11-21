package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface GroupEntityRepository extends JpaRepository<GroupEntity, UUID> {
    // Custom query with fetch join to exclude password attribute of createdByUser
    @Query("SELECT g FROM GroupEntity g JOIN FETCH g.createdByUser u WHERE g.id = :groupId")
    Optional<GroupEntity> findGroupByIdExcludingCreatedByUserPassword(@Param("groupId") UUID groupId);
}
