package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Repository interface for accessing and managing {@link UserEntity} entities in the database.
 * Extends the Spring Data {@link JpaRepository} interface for basic CRUD operations.
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Partially updates a {@link UserEntity} by applying the specified updates.
     *
     * @param userId   The ID of the user to be updated.
     * @param updates  A {@link Map} containing the fields to be updated and their new values.
     */
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET " +
            "u.email = CASE WHEN :#{#updates['email']} IS NOT NULL THEN :#{#updates['email']} ELSE u.email END, " +
            "u.firstName = CASE WHEN :#{#updates['firstName']} IS NOT NULL THEN :#{#updates['firstName']} ELSE u.firstName END, " +
            "u.lastName = CASE WHEN :#{#updates['lastName']} IS NOT NULL THEN :#{#updates['lastName']} ELSE u.lastName END " +
            "WHERE u.id = :userId")
    void partialUpdate(@Param("userId") UUID userId, @Param("updates") Map<String, ?> updates);

    /**
     * Retrieves a list of {@link UserEntity} instances associated with the given group ID.
     *
     * @param groupId The ID of the group.
     * @return A list of {@link UserEntity} instances associated with the group.
     */
    @Query("SELECT u FROM UserEntity u JOIN GroupMembershipEntity gm ON u.id = gm.user.id WHERE gm.group.id = :groupId")
    List<UserEntity> findByGroupId(@Param("groupId") UUID groupId);

    /**
     * Retrieves a {@link UserEntity} by the given email.
     *
     * @param email The email of the user.
     * @return A {@link UserEntity} instance if found, {@code null} otherwise.
     */
    UserEntity findByEmail(String email);
}