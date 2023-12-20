package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentParticipationEntity;
import com.flatfusion.backend.repositories.PaymentParticipationEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentParticipationRESTController.class)
public class PaymentParticipationRESTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentParticipationEntityRepository repository;

    @InjectMocks
    private PaymentParticipationRESTController controller;

    @Test
    public void testGetById_ReturnsEntityIfExists() throws Exception {
        // Mock data
        UUID entityId = UUID.randomUUID();
        PaymentParticipationEntity entity = new PaymentParticipationEntity();
        entity.setId(entityId);

        // Mock repository response
        when(repository.findById(entityId)).thenReturn(Optional.of(entity));

        // Perform GET request
        mockMvc.perform(get("/v1/payment-participations/{id}", entityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(entityId.toString()));
    }

    @Test
    public void testGetById_ReturnsNotFoundForNonExistingEntity() throws Exception {
        // Mock data
        UUID nonExistingId = UUID.randomUUID();

        // Mock repository response
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Perform GET request
        mockMvc.perform(get("/v1/payment-participations/{id}", nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /*
    @Test
    public void testCreate_ReturnsCreatedEntity() throws Exception {
        // Mock data
        PaymentParticipationEntity entity = new PaymentParticipationEntity();

        // Mock repository response
        when(repository.save(any(PaymentParticipationEntity.class))).thenReturn(entity);

        // Perform POST request
        mockMvc.perform(post("/v1/payment-participations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(/* JSON representation of the entity/!  ))
            .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }
    */

}
