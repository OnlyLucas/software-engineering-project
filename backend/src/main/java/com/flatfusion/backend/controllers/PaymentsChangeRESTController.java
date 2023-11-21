package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentsChangeEntity;
import com.flatfusion.backend.repositories.PaymentsChangeEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments-changes")
public class PaymentsChangeRESTController extends RESTController<PaymentsChangeEntity>{
    @Autowired
    public PaymentsChangeRESTController(PaymentsChangeEntityRepository repository) {
        super(repository);
    }

}
