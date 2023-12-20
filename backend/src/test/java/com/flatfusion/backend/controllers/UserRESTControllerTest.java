package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserRESTControllerTest {

    @Mock
    private UserEntityRepository userRepository;

    @InjectMocks
    private UserRESTController userController;

    @Test
    public void testGetById_UserExists_ReturnsUser() {
        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        ResponseEntity<UserEntity> responseEntity = userController.getById(userId, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userEntity, responseEntity.getBody());
    }

    @Test
    public void testGetById_UserDoesNotExist_ReturnsNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<UserEntity> responseEntity = userController.getById(userId, null);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());
    }

    // Weitere Tests für andere Methoden des UserRESTController
}
