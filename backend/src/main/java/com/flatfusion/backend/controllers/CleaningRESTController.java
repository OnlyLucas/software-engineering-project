package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.CleaningEntity;
import com.flatfusion.backend.repositories.CleaningEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST Controller for managing {@link CleaningEntity} instances, particularly focusing on cleaning templates.
 * Extends {@link RESTController} for generic CRUD operations.
 *
 * Base URL path: "/api/cleanings"
 */
@RestController
@RequestMapping("/v1/cleanings")
public class CleaningRESTController extends RESTController<CleaningEntity>{
    @Autowired
    CleaningEntityRepository repository;

    /**
     * Constructor with autowired {@link CleaningEntityRepository}.
     *
     * @param repository The autowired repository for {@link CleaningEntity}.
     */
    @Autowired
    public CleaningRESTController(CleaningEntityRepository repository) {
        super(repository);
    }

    /**
     * Get uncompleted cleanings for a specific cleaning template.
     *
     * @param templateId The ID of the cleaning template.
     * @return {@link ResponseEntity} containing a list of uncompleted cleanings with HTTP status.
     *         If found, returns 200 OK; if not found, returns 404 NOT FOUND.
     */
    @GetMapping("/cleaning-template/{templateId}/uncompleted")
    public ResponseEntity<List<CleaningEntity>> getUncompletedCleaningsForCleaningTemplate(@PathVariable UUID templateId) {
        logger.info("Get uncompleted Cleanings by CleaningTemplate id:  " + templateId);
        Optional<List<CleaningEntity>> uncompletedCleanings = repository.getUncompletedCleaningsForTemplate(templateId);
        if(uncompletedCleanings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(uncompletedCleanings.get(), HttpStatus.OK);
    }

    @GetMapping("/cleaning-template/{templateId}/uncompletedSmallestDate")
    public ResponseEntity<CleaningEntity> getUncompletedCleaningWithSmallestDate(@PathVariable UUID templateId) {
        logger.info("Get uncompleted Cleaning with smallest date by CleaningTemplate id:  " + templateId);
        Pageable pageable = PageRequest.of(0, 1); // Limit the result to 1 item
        Page<CleaningEntity> cleaningPage = repository.getUncompletedCleaningWithSmallestDate(templateId, pageable);
        //Optional<CleaningEntity> uncompletedCleaning = repository.getUncompletedCleaningWithSmallestDate(templateId);
        if(cleaningPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cleaningPage.getContent().get(0), HttpStatus.OK);
    }

}
