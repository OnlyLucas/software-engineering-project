package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET " +
            "u.email = CASE WHEN :#{#updates['email']} IS NOT NULL THEN :#{#updates['email']} ELSE u.email END, " +
            "u.firstName = CASE WHEN :#{#updates['firstName']} IS NOT NULL THEN :#{#updates['firstName']} ELSE u.firstName END, " +
            "u.lastName = CASE WHEN :#{#updates['lastName']} IS NOT NULL THEN :#{#updates['lastName']} ELSE u.lastName END " +
            "WHERE u.id = :userId")
    void partialUpdate(@Param("userId") UUID userId, @Param("updates") Map<String, ?> updates);

    @Query("SELECT u FROM UserEntity u JOIN GroupMembershipEntity gm ON u.id = gm.user.id WHERE gm.group.id = :groupId")
    List<UserEntity> findByGroupId(@Param("groupId") UUID groupId);
}