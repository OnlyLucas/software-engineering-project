package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.CleaningTemplateEntity;
import com.flatfusion.backend.repositories.CleaningTemplateEntityRepository;
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
@RequestMapping("/v1/cleaning-templates")
public class CleaningTemplateRESTController extends RESTController<CleaningTemplateEntity>{
    @Autowired
    CleaningTemplateEntityRepository repository;
    @Autowired
    public CleaningTemplateRESTController(CleaningTemplateEntityRepository repository) {
        super(repository);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<CleaningTemplateEntity>> getCleaningTemplatesByGroupId(@PathVariable UUID id){
        logger.info("Get Cleaning Templates by group id:  " + id);
        Optional<List<CleaningTemplateEntity>> entities = repository.findByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

}
