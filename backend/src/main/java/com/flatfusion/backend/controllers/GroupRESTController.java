package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.GroupEntityRepository;
import com.flatfusion.backend.repositories.UserEntityRepository;
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
public class GroupRESTController //extends RESTController<GroupEntityRepository>
{
    @Autowired
    private GroupEntityRepository groupRepository;

    @GetMapping("/{id}")
    public ResponseEntity<GroupEntity> getUserById(@PathVariable String id, WebRequest request){
        System.out.println("This is the request id: " + id);
        Optional<GroupEntity> group = groupRepository.findById(id);

        if(group.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(group.get(), HttpStatus.OK);
    }
}
