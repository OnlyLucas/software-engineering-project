package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentEntity;
import com.flatfusion.backend.repositories.PaymentEntityRepository;
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
@RequestMapping("/v1/payments")
public class PaymentRESTController extends RESTController<PaymentEntity>{
    @Autowired
    PaymentEntityRepository repository;
    @Autowired
    public PaymentRESTController(PaymentEntityRepository repository) {
        super(repository);
    }


    @GetMapping("/group/{id}")
    public ResponseEntity<List<PaymentEntity>> getPaymentsByGroupId(@PathVariable UUID id){
        logger.info("Get Payments by group id:  " + id);
        Optional<List<PaymentEntity>> entities = repository.findByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }
}
