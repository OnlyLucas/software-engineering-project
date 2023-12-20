package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.entities.GroupMembershipEntity;
import com.flatfusion.backend.repositories.GroupMembershipEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GroupMembershipRESTControllerTest {

    @Mock
    private GroupMembershipEntityRepository groupMembershipRepository;

    @InjectMocks
    private GroupMembershipRESTController groupMembershipController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deleteGroupMembership_ExistingMembership_ReturnsOk() {

        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        GroupMembershipEntity groupMembershipEntity = new GroupMembershipEntity(); // Create a GroupMembershipEntity instance

        // Mock the behavior of the repository method
        when(groupMembershipRepository.findByGroupIdAndUserId(groupId, userId)).thenReturn(groupMembershipEntity);

        // Call the method to be tested
        ResponseEntity<Void> responseEntity = groupMembershipController.deleteGroupMembership(userId, groupId);

        // Assert the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void deleteGroupMembership_NonExistingMembership_ReturnsNotFound() {

        UUID userId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();

        // Mock the behavior of the repository method returning null for non-existing membership
        when(groupMembershipRepository.findByGroupIdAndUserId(groupId, userId)).thenReturn(null);

        // Call the method to be tested
        ResponseEntity<Void> responseEntity = groupMembershipController.deleteGroupMembership(userId, groupId);

        // Assert the response
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    void getGroupByUserId_ExistingGroup_ReturnsOk() {

        UUID userId = UUID.randomUUID();
        GroupEntity groupEntity = new GroupEntity(); // Create a GroupEntity instance

        // Mock the behavior of the repository method
        when(groupMembershipRepository.findGroupByUserId(userId)).thenReturn(Optional.of(groupEntity));

        // Call the method to be tested
        ResponseEntity<GroupEntity> responseEntity = groupMembershipController.getGroupByUserId(userId);

        // Assert the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    void getGroupByUserId_NonExistingGroup_ReturnsNotFound() {

        UUID userId = UUID.randomUUID();

        // Mock the behavior of the repository method returning empty optional for non-existing group
        when(groupMembershipRepository.findGroupByUserId(userId)).thenReturn(Optional.empty());

        // Call the method to be tested
        ResponseEntity<GroupEntity> responseEntity = groupMembershipController.getGroupByUserId(userId);

        // Assert the response
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

}