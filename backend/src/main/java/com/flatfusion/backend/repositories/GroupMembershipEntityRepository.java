package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupMembershipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupMembershipEntityRepository extends JpaRepository<GroupMembershipEntity, UUID> {
}