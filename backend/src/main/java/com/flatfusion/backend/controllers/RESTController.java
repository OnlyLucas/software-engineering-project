package com.flatfusion.backend.controllers;
import com.flatfusion.backend.entities.EntityInterface;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;
import java.util.UUID;

/**
 * Generic REST controller providing basic CRUD operations for entities.
 *
 * @param <T> The type of entity managed by this controller.
 */
public class RESTController<T extends EntityInterface> {

    /**
     * The repository used for database operations on entities.
     */
    protected final JpaRepository<T, UUID> repository;

    /**
     * The logger instance for logging messages.
     */
    protected final Logger logger = LoggerFactory.getLogger(RESTController.class);

    /**
     * Constructs a new RESTController with the specified repository.
     *
     * @param repository The repository for entities of type T.
     */
    public RESTController(JpaRepository<T, UUID> repository) {
        this.repository = repository;
    }

    /**
     * Creates a new entity using the provided JSON representation.
     *
     * @param entity The JSON representation of the entity.
     * @return HTTP.CREATED and the created entity if successful.
     */
    @Transactional
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {

        T createdEntity = repository.save(entity);
        logger.info("Entity created : " + createdEntity);
        return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
    }

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id      The unique identifier of the entity.
     * @param request The incoming web request.
     * @return HTTP.OK and the retrieved entity if found, HTTP.NOT_FOUND otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable UUID id, WebRequest request){

        logger.info("This is the request id for the controller: " + id);
        Optional<T> entity = repository.findById(id);

        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }

    /**
     * Updates an existing entity with the provided JSON representation.
     *
     * @param id            The unique identifier of the entity to update.
     * @param updatedEntity The JSON representation of the updated entity.
     * @return HTTP.OK and the updated entity if successful, HTTP.NOT_FOUND if the entity does not exist.
     */
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable UUID id, @RequestBody T updatedEntity) {

        Optional<T> existingEntity = repository.findById(id);

        if (existingEntity.isPresent()) {
            try {
                repository.save(updatedEntity);
            } catch (PropertyValueException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity to delete.
     * @return HTTP.NO_CONTENT if the deletion was successful, HTTP.NOT_FOUND if the entity does not exist.
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable UUID id) {
        Optional<T> existingEntity = repository.findById(id);
        if (existingEntity.isPresent()) {
            repository.deleteById(id);
            logger.info("Entity deleted: " + existingEntity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
