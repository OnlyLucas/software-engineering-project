package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.GroupEntity;
import com.flatfusion.backend.entities.GroupMembershipEntity;
import com.flatfusion.backend.repositories.GroupMembershipEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
        this.repository = repository;
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
            logger.info("Deleted GroupMembership by user id: " + userId + " and group id:  " + groupId);
            repository.delete(groupMembership);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get the group associated with a user by user ID.
     *
     * @param userId The ID of the user.
     * @return {@link ResponseEntity} containing the group associated with the user with HTTP status.
     * If found, returns 200 OK; if not found, returns 404 NOT FOUND.
     */
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<GroupEntity> getGroupByUserId(@PathVariable UUID userId) {
        Optional<GroupEntity> group = repository.findGroupByUserId(userId);
        logger.info("Get group by user id: " + userId );

        if (group.isPresent()) {
            return new ResponseEntity<>(group.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
