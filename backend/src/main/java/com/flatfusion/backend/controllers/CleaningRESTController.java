package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.CleaningEntity;
import com.flatfusion.backend.repositories.CleaningEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/cleanings")
public class CleaningRESTController extends RESTController<CleaningEntity>{
    @Autowired
    CleaningEntityRepository repository;

    @Autowired
    public CleaningRESTController(CleaningEntityRepository repository) {
        super(repository);
    }

    @GetMapping("/cleaning-template/{templateId}/uncompleted")
    public ResponseEntity<List<CleaningEntity>> getUncompletedCleaningsForCleaningTemplate(@PathVariable UUID templateId) {
        logger.info("Get uncompleted Cleanings by CleaningTemplate id:  " + templateId);
        Optional<List<CleaningEntity>> uncompletedCleanings = repository.getUncompletedCleaningsForTemplate(templateId);
        if(uncompletedCleanings.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(uncompletedCleanings.get(), HttpStatus.OK);
    }

}
