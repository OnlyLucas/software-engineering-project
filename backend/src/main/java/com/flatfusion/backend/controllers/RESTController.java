package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.UserEntity;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;
import java.util.UUID;

public class RESTController<T extends JpaRepository> {
    @Autowired
    private T repository;

    @GetMapping("/{id}")
    public ResponseEntity<T> getUserById(@PathVariable String id, WebRequest request){
        Optional<T> entity = repository.findById(id);

        if(entity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entity.get(), HttpStatus.OK);
    }
}
