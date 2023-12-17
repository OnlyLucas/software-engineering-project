package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserCreateEntity;
import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
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

    @GetMapping("/mail/{mail}")
    public ResponseEntity<UserEntity> getUserByMail(@PathVariable("mail") String mail) {
        UserEntity user = repository.findByEmail(mail);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO Add Post Mapping
//    @Transactional
//    @PostMapping
//    public ResponseEntity<UserEntity> create(@RequestBody UserCreateEntity createUserDTO) {
//
//        // Convert the saved user entity to UserResponseDTO and return it in the response
//        UserEntity userResponseDTO = convertCreateUserDTOToEntity(createUserDTO);
//
//        // Save the new user entity to the repository
//        repository.save(userResponseDTO);
//
//        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
//    }

    // Helper methods to convert DTOs to entities and vice versa
    private UserEntity convertCreateUserDTOToEntity(UserCreateEntity createUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setEmail(createUser.getEmail());
        userEntity.setFirstName(createUser.getFirstName());
        userEntity.setLastName(createUser.getLastName());
        userEntity.setCreatedAt(createUser.getCreatedAt());

        // Set the password securely using a strong hashing algorithm
        String hashedPassword = hashPassword(createUser.getPassword());
        userEntity.setPassword(hashedPassword);

        return userEntity;
    }

    // Placeholder for password hashing logic
    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
