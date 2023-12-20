package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.CleaningEntity;
import com.flatfusion.backend.repositories.CleaningEntityRepository;
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
import static org.mockito.Mockito.*;

class CleaningRESTControllerTest {

    @Mock
    private CleaningEntityRepository repository;

    @InjectMocks
    private CleaningRESTController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById_ValidId_ReturnsEntity() {

        UUID validId = UUID.randomUUID();
        CleaningEntity entity = new CleaningEntity(); // Create a CleaningEntity object for testing

        when(repository.findById(validId)).thenReturn(Optional.of(entity));

        ResponseEntity<CleaningEntity> response = controller.getById(validId, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(entity, response.getBody());
        verify(repository, times(1)).findById(validId);

    }

    @Test
    void testGetById_InvalidId_ReturnsNotFound() {

        UUID invalidId = UUID.randomUUID();

        when(repository.findById(invalidId)).thenReturn(Optional.empty());

        ResponseEntity<CleaningEntity> response = controller.getById(invalidId, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repository, times(1)).findById(invalidId);

    }

}