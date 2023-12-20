package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.repositories.GroupEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GroupRESTControllerTest {

    @Mock
    private GroupEntityRepository groupRepository;

    @InjectMocks
    private GroupRESTController groupRESTController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetGroupById_ExistingGroup_ReturnsOK() {

        UUID groupId = UUID.randomUUID();
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(groupId);

        when(groupRepository.findGroupByIdExcludingCreatedByUserPassword(groupId)).thenReturn(Optional.of(groupEntity));

        ResponseEntity<GroupEntity> response = groupRESTController.getById(groupId, mock(WebRequest.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(groupEntity, response.getBody());

    }

    @Test
    void testGetGroupById_NonExistingGroup_ReturnsNotFound() {

        UUID groupId = UUID.randomUUID();

        when(groupRepository.findGroupByIdExcludingCreatedByUserPassword(groupId)).thenReturn(Optional.empty());

        ResponseEntity<GroupEntity> response = groupRESTController.getById(groupId, mock(WebRequest.class));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

}