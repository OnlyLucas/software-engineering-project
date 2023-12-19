package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.repositories.GroupEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;
import java.util.UUID;

/**
 * REST Controller for managing {@link GroupEntity} instances, including operations related to groups.
 * Extends {@link RESTController} for generic CRUD operations.
 *
 * Base URL path: "/v1/groups"
 */
@RestController
@RequestMapping("/v1/groups")
public class GroupRESTController extends RESTController<GroupEntity>{
    // Two repositories to be able to call repository specific methods
    @Autowired
    GroupEntityRepository groupRepository;

    /**
     * Constructor with autowired {@link GroupEntityRepository}.
     *
     * @param repository The autowired repository for {@link GroupEntity}.
     */
    @Autowired
    public GroupRESTController(GroupEntityRepository repository) {
        super(repository);
    }


    /**
     * Get a group by ID excluding sensitive information (e.g., createdByUser password).
     *
     * @param id The ID of the group to retrieve.
     * @param request The web request.
     * @return {@link ResponseEntity} with the retrieved {@link GroupEntity} and HTTP status.
     */
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GroupEntity> getById(@PathVariable UUID id, WebRequest request){

        logger.info("Get group by group id:  " + id);
        Optional<GroupEntity> entity = groupRepository.findGroupByIdExcludingCreatedByUserPassword(id);

        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }
}
