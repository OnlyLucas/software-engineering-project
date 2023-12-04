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

@RestController
@RequestMapping("/v1/group-groceries")
public class GroupGroceryRESTController extends RESTController<GroupGroceryEntity>{
    @Autowired
    GroupGroceryEntityRepository repository;

    @Autowired
    public GroupGroceryRESTController(GroupGroceryEntityRepository repository) {
        super(repository);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<GroupGroceryEntity>> getGroupGroceriesByGroupId(@PathVariable UUID id){
        logger.info("Get GroupGroceries by group id:  " + id);
        Optional<List<GroupGroceryEntity>> entities = repository.findAllByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GroupGroceryEntity> deleteGroupGrocery(@PathVariable UUID id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}