package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserCreateEntity;
import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import com.flatfusion.backend.requests.UserWithPasswordRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    // Uses the PasswordEncoder Bean specified in the WebSecurityConfig
    @Autowired
    PasswordEncoder encoder;

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
            repository.partialUpdate(userId, updates);
            Optional<UserEntity> updatedEntityOptional = repository.findById(userId);
            UserEntity updatedEntity = updatedEntityOptional.get();
            logger.info("Update user mail of user with id " + userId + "with new mail " + updatedEntity.getEmail());
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
            logger.info("Get user by mail: " + mail);
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

    /***
     *  This method creates a UserEntity from the entity given in the https request body.
     *  The password of the user is encrypted with a @{@link PasswordEncoder} before being saved to the db.
     * @param entity {@link UserWithPasswordRequest} from the http body.
     * @return The created UserEntity as a http response.
     */

    @PostMapping("/create-with-password")
    public ResponseEntity<UserEntity> createWithPassword(@RequestBody UserWithPasswordRequest entity) {

        UserEntity newUser = entity.getUser();
        String password = entity.getPassword();

        if(newUser == null || password == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if(password.isBlank()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String email = newUser.getEmail();

        UserEntity existingUser = repository.findByEmail(email);

        if(existingUser != null){
            // No creation as user already exists for this email
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        String encryptedPassword = encoder.encode(password);
        newUser.setPassword(encryptedPassword);

        UserEntity createdEntity = repository.save(newUser);
        logger.info("User created: " + createdEntity);

        return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
    }


    /**
     *  // This default endpoint should not be used in this class and is therefore overridden.
     */
    @Override
    public ResponseEntity<UserEntity> create(UserEntity entity) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
