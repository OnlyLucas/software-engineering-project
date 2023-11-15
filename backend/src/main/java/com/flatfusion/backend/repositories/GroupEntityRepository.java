package com.flatfusion.backend.repositories;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupEntityRepository extends JpaRepository<GroupEntity, UUID> {
}
