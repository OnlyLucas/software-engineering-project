package com.flatfusion.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/test")
public class TestRESTController {

    @GetMapping
    ResponseEntity<String> getString(){
        return new ResponseEntity<>("Hi", HttpStatus.ACCEPTED);
    }


}
