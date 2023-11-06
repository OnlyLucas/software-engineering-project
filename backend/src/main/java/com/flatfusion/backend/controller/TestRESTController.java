package com.flatfusion.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/test")
public class TestRESTController {
    @GetMapping
    ResponseEntity<String> getString(){
        return new ResponseEntity<>("Hi", HttpStatus.ACCEPTED);
    }
}
