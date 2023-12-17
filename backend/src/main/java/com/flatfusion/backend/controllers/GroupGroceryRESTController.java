package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupGroceryEntity;
import com.flatfusion.backend.repositories.GroupGroceryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST Controller for managing {@link GroupGroceryEntity} instances, including operations related to group groceries.
 * Extends {@link RESTController} for generic CRUD operations.
 *
 * Base URL path: "/api/group-groceries"
 */
@RestController
@RequestMapping("/v1/group-groceries")
public class GroupGroceryRESTController extends RESTController<GroupGroceryEntity>{
    @Autowired
    GroupGroceryEntityRepository repository;

    /**
     * Constructor with autowired {@link GroupGroceryEntityRepository}.
     *
     * @param repository The autowired repository for {@link GroupGroceryEntity}.
     */
    @Autowired
    public GroupGroceryRESTController(GroupGroceryEntityRepository repository) {
        super(repository);
    }

    /**
     * Get uncompleted group groceries by group ID.
     *
     * @param id The ID of the group.
     * @return {@link ResponseEntity} containing a list of uncompleted group groceries with HTTP status.
     *         If found, returns 200 OK; if not found, returns 404 NOT FOUND.
     */
    @GetMapping("/group/{id}/uncompleted")
    public ResponseEntity<List<GroupGroceryEntity>> getGroupGroceriesByGroupIdUncompleted(@PathVariable UUID id){
        logger.info("Get uncompleted GroupGroceries by group id:  " + id);
        Optional<List<GroupGroceryEntity>> entities = repository.findUncompletedByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    /**
     * Get completed group groceries by group ID.
     *
     * @param id The ID of the group.
     * @return {@link ResponseEntity} containing a list of completed group groceries with HTTP status.
     *         If found, returns 200 OK; if not found, returns 404 NOT FOUND.
     */
    @GetMapping("/group/{id}/completed")
    public ResponseEntity<List<GroupGroceryEntity>> getGroupGroceriesByGroupIdCompleted(@PathVariable UUID id){
        logger.info("Get completed GroupGroceries by group id:  " + id);
        Optional<List<GroupGroceryEntity>> entities = repository.findCompletedByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }
}