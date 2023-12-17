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

/**
 * REST Controller for managing {@link GroupMembershipEntity} instances, including operations related to group memberships.
 * Extends {@link RESTController} for generic CRUD operations.
 *
 * Base URL path: "/api/group-memberships"
 */
@RestController
@RequestMapping("/v1/group-memberships")
public class GroupMembershipRESTController extends RESTController<GroupMembershipEntity>{

    @Autowired
    private GroupMembershipEntityRepository repository;

    /**
     * Constructor with autowired {@link GroupMembershipEntityRepository}.
     *
     * @param repository The autowired repository for {@link GroupMembershipEntity}.
     */
    @Autowired
    public GroupMembershipRESTController(GroupMembershipEntityRepository repository) {
        super(repository);
    }

    /**
     * Delete a group membership by user ID and group ID.
     *
     * @param userId The ID of the user.
     * @param groupId The ID of the group.
     * @return {@link ResponseEntity} with HTTP status indicating success (200 OK) or not found (404 NOT FOUND).
     */
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
