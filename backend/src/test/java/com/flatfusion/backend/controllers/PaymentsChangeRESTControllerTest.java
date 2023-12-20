package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentsChangeEntity;
import com.flatfusion.backend.repositories.PaymentsChangeEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentsChangeRESTControllerTest {

    @Mock
    private PaymentsChangeEntityRepository paymentChangeRepository;

    @InjectMocks
    private PaymentsChangeRESTController paymentChangeController;

    /*
    @Test
    public void testGetById() {
        UUID entityId = UUID.randomUUID();
        PaymentsChangeEntity paymentsChangeEntity = new PaymentsChangeEntity(); // Create a sample PaymentsChangeEntity

        when(paymentChangeRepository.findById(entityId)).thenReturn(Optional.of(paymentsChangeEntity));

        ResponseEntity<PaymentsChangeEntity> responseEntity = paymentChangeController.getById(entityId, mock(WebRequest.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(paymentsChangeEntity, responseEntity.getBody());
    }

     */

    /*
    @Test
    public void testCreate() {
        PaymentsChangeEntity paymentsChangeEntity = new PaymentsChangeEntity(); // Create a sample PaymentsChangeEntity

        when(paymentChangeRepository.save(any())).thenReturn(paymentsChangeEntity);

        ResponseEntity<PaymentsChangeEntity> responseEntity = paymentChangeController.create(paymentsChangeEntity);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(paymentsChangeEntity, responseEntity.getBody());
        verify(paymentChangeRepository, times(1)).save(any());
    }

    @Test
    public void testUpdate() {
        UUID entityId = UUID.randomUUID();
        PaymentsChangeEntity existingEntity = new PaymentsChangeEntity(); // Create an existing entity
        PaymentsChangeEntity updatedEntity = new PaymentsChangeEntity(); // Create an updated entity

        when(paymentChangeRepository.findById(entityId)).thenReturn(Optional.of(existingEntity));
        when(paymentChangeRepository.save(any())).thenReturn(updatedEntity);

        ResponseEntity<PaymentsChangeEntity> responseEntity = paymentChangeController.update(entityId, updatedEntity);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedEntity, responseEntity.getBody());
        verify(paymentChangeRepository, times(1)).save(any());
    }

     */

    /*
    @Test
    public void testDelete() {
        UUID entityId = UUID.randomUUID();
        PaymentsChangeEntity existingEntity = new PaymentsChangeEntity(); // Create an existing entity

        when(paymentChangeRepository.findById(entityId)).thenReturn(Optional.of(existingEntity));

        ResponseEntity<PaymentsChangeEntity> responseEntity = paymentChangeController.delete(entityId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(paymentChangeRepository, times(1)).deleteById(entityId);
    }

     */
}
