package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentEntity;
import com.flatfusion.backend.repositories.PaymentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class PaymentRESTController extends RESTController<PaymentEntity>{
    @Autowired
    public PaymentRESTController(PaymentEntityRepository repository) {
        super(repository);
    }

}
