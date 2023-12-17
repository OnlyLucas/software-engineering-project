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

/**
 * REST controller for managing user-related operations.
 */
@RestController
@RequestMapping("/v1/users")
public class UserRESTController extends RESTController<UserEntity>{
    @Autowired
    private UserEntityRepository repository;

    /**
     * Constructor for the UserRESTController class.
     *
     * @param repository The repository for handling user entities.
     */
    @Autowired
    public UserRESTController(UserEntityRepository repository) {
        super(repository);
    }

    /**
     * Handles partial updates for a user entity.
     *
     * @param userId  The unique identifier of the user entity to be updated.
     * @param updates A map containing the fields to be updated along with their new values.
     * @return HTTP.OK and the updated user entity if the update is successful.
     *         HTTP.NOT_FOUND if the user entity with the given ID is not found.
     */
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

    /**
     * Retrieves a list of users belonging to a specific group.
     *
     * @param groupId The unique identifier of the group for which users are to be retrieved.
     * @return A list of user entities belonging to the specified group.
     */
    @GetMapping("/group/{groupId}")
    public List<UserEntity> getUsersByGroupId(@PathVariable("groupId") UUID groupId) {
        return repository.findByGroupId(groupId);
    }

    /**
     * Retrieves a user entity by email.
     *
     * @param mail The email address of the user.
     * @return HTTP.OK and the user entity if found.
     *         HTTP.NOT_FOUND if no user entity is found with the specified email address.
     */
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
