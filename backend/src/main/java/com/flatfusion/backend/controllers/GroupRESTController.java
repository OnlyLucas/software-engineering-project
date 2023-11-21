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

@RestController
@RequestMapping("/v1/groups")
public class GroupRESTController extends RESTController<GroupEntity>{
    // Two repositories to be able to call repository specific methods
    @Autowired
    GroupEntityRepository groupRepository;

    @Autowired
    public GroupRESTController(GroupEntityRepository repository) {
        super(repository);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GroupEntity> getById(@PathVariable UUID id, WebRequest request){

        System.out.println("This is the request id for the generic controller: " + id);
        logger.info("This is the request id for the controller: " + id);
        Optional<GroupEntity> entity = groupRepository.findGroupByIdExcludingCreatedByUserPassword(id);

        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }
}
