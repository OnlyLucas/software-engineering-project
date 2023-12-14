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

public class RESTController<T extends EntityInterface> {

    protected final JpaRepository<T, UUID> repository;
    protected final Logger logger = LoggerFactory.getLogger(RESTController.class);

    public RESTController(JpaRepository<T, UUID> repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {

        T createdEntity = repository.save(entity);
        logger.info("Entity created : " + createdEntity);
        return new ResponseEntity<>(createdEntity, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable UUID id, WebRequest request){

        logger.info("This is the request id for the controller: " + id);
        Optional<T> entity = repository.findById(id);

        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }

    /***
     * This http endpoint is for updating existing database entities.
     * If the given entity to update does not exist in the database, an HTTP.NotFound status will be returned.
     * @param id UUID for the Entity
     * @param updatedEntity JSON Representation of the Entity to be updated. The JSON will be mapped to the JAVA Entity.
     * @return OK and the updated entity if the operation was successful. NOT_FOUND if the given entity is not found in the database.
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
