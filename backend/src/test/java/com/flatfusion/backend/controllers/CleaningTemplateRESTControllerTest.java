package com.flatfusion.backend.controllers;

import com.flatfusion.backend.controllers.CleaningTemplateRESTController;
import com.flatfusion.backend.entities.CleaningTemplateEntity;
import com.flatfusion.backend.repositories.CleaningTemplateEntityRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CleaningTemplateRESTControllerTest {

    @Mock
    private CleaningTemplateEntityRepository cleaningTemplateRepository;

    @InjectMocks
    private CleaningTemplateRESTController cleaningTemplateController;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        cleaningTemplateController = new CleaningTemplateRESTController(cleaningTemplateRepository);

    }

    @Test
    void create_ValidEntity_ReturnsCreatedEntity() {

        // Given
        CleaningTemplateEntity mockEntity = new CleaningTemplateEntity(); // Create a mock entity
        when(cleaningTemplateRepository.save(any())).thenReturn(mockEntity);

        // When
        ResponseEntity<CleaningTemplateEntity> response = cleaningTemplateController.create(mockEntity);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockEntity, response.getBody());

    }

    // Add tests for other methods like getById, update, and delete

    @Test
    void getById_ExistingId_ReturnsEntity() {

        // Given
        UUID entityId = UUID.randomUUID();
        CleaningTemplateEntity mockEntity = new CleaningTemplateEntity(); // Create a mock entity
        when(cleaningTemplateRepository.findById(entityId)).thenReturn(Optional.of(mockEntity));

        // When
        ResponseEntity<CleaningTemplateEntity> response = cleaningTemplateController.getById(entityId, null);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEntity, response.getBody());

    }

}