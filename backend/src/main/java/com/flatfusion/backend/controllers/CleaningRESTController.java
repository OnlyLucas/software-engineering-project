package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.CleaningEntity;
import com.flatfusion.backend.repositories.CleaningEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cleanings")
public class CleaningRESTController extends RESTController<CleaningEntity>{
    @Autowired
    public CleaningRESTController(CleaningEntityRepository repository) {
        super(repository);
    }

}
