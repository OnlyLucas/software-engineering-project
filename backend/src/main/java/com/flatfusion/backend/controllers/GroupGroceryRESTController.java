package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupGroceryEntity;
import com.flatfusion.backend.repositories.GroupGroceryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/group-groceries")
public class GroupGroceryRESTController extends RESTController<GroupGroceryEntity>{
    @Autowired
    public GroupGroceryRESTController(GroupGroceryEntityRepository repository) {
        super(repository);
    }

}