package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserRESTController extends RESTController<UserEntity>{
    @Autowired
    private UserEntityRepository repository;

    @Autowired
    public UserRESTController(UserEntityRepository repository) {
        super(repository);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserEntity> partialUpdateUser(@PathVariable UUID userId, @RequestBody Map<String, ?> updates) {
        Optional<UserEntity> oldEntity = repository.findById(userId);


        if (oldEntity.isPresent()) {
            UserEntity updatedEntity = oldEntity.get();
            System.out.println("Old user mail: " + updatedEntity.getEmail());

            repository.partialUpdate(userId, updates);
            Optional<UserEntity> updatedEntityOptional = repository.findById(userId);
            updatedEntity = updatedEntityOptional.get();
            System.out.println("New user mail: " + updatedEntity.getEmail());

            return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/group/{groupId}")
    public List<UserEntity> getUsersByGroupId(@PathVariable("groupId") UUID groupId) {
        return repository.findByGroupId(groupId);
    }
}
