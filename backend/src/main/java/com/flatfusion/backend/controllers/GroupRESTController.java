package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.repositories.GroupEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/groups")
public class GroupRESTController extends RESTController<GroupEntity>{
    @Autowired
    public GroupRESTController(GroupEntityRepository repository) {
        super(repository);
    }

}
