package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupMembershipEntity;
import com.flatfusion.backend.repositories.GroupMembershipEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/group-memberships")
public class GroupMembershipRESTController extends RESTController<GroupMembershipEntity>{
    @Autowired
    public GroupMembershipRESTController(GroupMembershipEntityRepository repository) {
        super(repository);
    }

}
