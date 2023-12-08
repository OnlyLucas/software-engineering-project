package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupMembershipEntity;
import com.flatfusion.backend.repositories.GroupMembershipEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/group-memberships")
public class GroupMembershipRESTController extends RESTController<GroupMembershipEntity>{

    @Autowired
    private GroupMembershipEntityRepository repository;
    @Autowired
    public GroupMembershipRESTController(GroupMembershipEntityRepository repository) {
        super(repository);
    }

    @DeleteMapping("/{userId}/{groupId}")
    public ResponseEntity<Void> deleteGroupMembership(
            @PathVariable("userId") UUID userId,
            @PathVariable("groupId") UUID groupId) {
        GroupMembershipEntity groupMembership = repository.findByGroupIdAndUserId(groupId, userId);
        if (groupMembership != null) {
            repository.delete(groupMembership);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}