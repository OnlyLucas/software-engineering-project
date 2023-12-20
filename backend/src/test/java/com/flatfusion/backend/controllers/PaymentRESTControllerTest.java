package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.entities.PaymentEntity;
import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.GroupEntityRepository;
import com.flatfusion.backend.repositories.PaymentEntityRepository;
import com.flatfusion.backend.repositories.UserEntityRepository;
import com.flatfusion.backend.requests.PaymentCreationRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentRESTControllerTest {

    @Mock
    private PaymentEntityRepository paymentRepository;

    @Mock
    private UserEntityRepository userRepository;

    @Mock
    private GroupEntityRepository groupRepository;

    @Mock
    private RESTController<PaymentEntity> restController;

    @InjectMocks
    private PaymentRESTController paymentController;

    @Test
    public void testGetPaymentsByGroupId() {
        UUID groupId = UUID.randomUUID();
        PaymentEntity paymentEntity = new PaymentEntity(); // Create a sample PaymentEntity

        when(paymentRepository.findByGroupId(groupId)).thenReturn(Optional.of(Collections.singletonList(paymentEntity)));

        ResponseEntity<List<PaymentEntity>> responseEntity = paymentController.getPaymentsByGroupId(groupId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList(paymentEntity), responseEntity.getBody());
    }

    @Test
    public void testCreateWithParticipations() {
        PaymentCreationRequest paymentCreationRequest = createSamplePaymentCreationRequest();
        PaymentEntity paymentEntity = paymentCreationRequest.getPayment();

        // Mock data retrieval from repositories
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity())); // Mock UserRepository response
        when(groupRepository.findById(any())).thenReturn(Optional.of(new GroupEntity())); // Mock GroupRepository response
        when(paymentRepository.save(any())).thenReturn(paymentEntity);

        ResponseEntity<PaymentEntity> responseEntity = paymentController.createWithParticipations(paymentCreationRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(paymentEntity, responseEntity.getBody());
        verify(paymentRepository, times(1)).save(any());
    }

    private PaymentCreationRequest createSamplePaymentCreationRequest() {
        PaymentEntity paymentEntity = new PaymentEntity();
        // Set necessary attributes for the payment entity

        List<UUID> participantUserIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());

        return new PaymentCreationRequest(paymentEntity, (Map<UUID, BigDecimal>) participantUserIds);
    }
}

