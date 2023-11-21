package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserRESTController extends RESTController<UserEntity>{
    @Autowired
    public UserRESTController(UserEntityRepository repository) {
        super(repository);
    }
}
