package com.flatfusion.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test REST controller providing a simple endpoint to return a string.
 */
@RestController
@RequestMapping("/api/test")
public class TestRESTController {

    /**
     * Handles HTTP GET requests to retrieve a string response.
     *
     * @return HTTP.ACCEPTED and the string "Hi" if the request is successful.
     */
    @GetMapping
    ResponseEntity<String> getString(){
        return new ResponseEntity<>("Hi", HttpStatus.ACCEPTED);
    }
}
