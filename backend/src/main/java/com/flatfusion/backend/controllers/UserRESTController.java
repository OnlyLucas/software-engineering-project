package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    PasswordEncoder encoder;

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

    /***
     *  This method creates a UserEntity from the entity given in the https request body.
     *  The password of the user is encrypted with a @{@link PasswordEncoder} before being saved to the db.
     * @param entity {@link UserEntity} from the http body.
     * @return The created UserEntity as a http response..
     */
    @Override
    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody UserEntity entity) {

        String password = entity.getPassword();
        String encryptedPassword = encoder.encode(password);
        entity.setPassword(encryptedPassword);

        UserEntity createdEntity = repository.save(entity);
        logger.info("User created: " + createdEntity.getEmail());
        return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
    }
}
