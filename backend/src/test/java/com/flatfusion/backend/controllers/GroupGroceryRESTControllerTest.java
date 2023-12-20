package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupGroceryEntity;
import com.flatfusion.backend.repositories.GroupGroceryEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GroupGroceryRESTControllerTest {

    @Mock
    private GroupGroceryEntityRepository groupGroceryRepository;

    @InjectMocks
    private GroupGroceryRESTController groupGroceryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getGroupGroceriesByGroupIdUncompleted_ExistingId_ReturnsListOfUncompletedGroceries() {

        UUID groupId = UUID.randomUUID();
        GroupGroceryEntity groupGroceryEntity = new GroupGroceryEntity();
        List<GroupGroceryEntity> groupGroceries = new ArrayList<>();
        groupGroceries.add(groupGroceryEntity);

        when(groupGroceryRepository.findUncompletedByGroupId(groupId)).thenReturn(Optional.of(groupGroceries));

        ResponseEntity<List<GroupGroceryEntity>> responseEntity = groupGroceryController.getGroupGroceriesByGroupIdUncompleted(groupId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(groupGroceries, responseEntity.getBody());

    }

    @Test
    void getGroupGroceriesByGroupIdCompleted_ExistingId_ReturnsListOfCompletedGroceries() {

        UUID groupId = UUID.randomUUID();
        GroupGroceryEntity groupGroceryEntity = new GroupGroceryEntity();
        List<GroupGroceryEntity> groupGroceries = new ArrayList<>();
        groupGroceries.add(groupGroceryEntity);

        when(groupGroceryRepository.findCompletedByGroupId(groupId)).thenReturn(Optional.of(groupGroceries));

        ResponseEntity<List<GroupGroceryEntity>> responseEntity = groupGroceryController.getGroupGroceriesByGroupIdCompleted(groupId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(groupGroceries, responseEntity.getBody());

    }

}