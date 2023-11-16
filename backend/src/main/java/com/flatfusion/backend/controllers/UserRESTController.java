package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.exceptions.EntityNotFoundException;
import com.flatfusion.backend.repositories.GroupEntityRepository;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserRESTController extends RESTController<UserEntity>{
    @Autowired
    public UserRESTController(UserEntityRepository userRepository) {
        super(userRepository);
    }
}
