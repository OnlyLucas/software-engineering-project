package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.CleaningTemplateEntity;
import com.flatfusion.backend.repositories.CleaningTemplateEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/cleaning-templates")
public class CleaningTemplateRESTController extends RESTController<CleaningTemplateEntity>{
    @Autowired
    public CleaningTemplateRESTController(CleaningTemplateEntityRepository repository) {
        super(repository);
    }

}
