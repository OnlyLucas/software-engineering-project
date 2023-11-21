package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentParticipationEntity;
import com.flatfusion.backend.repositories.PaymentParticipationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payment-participations")
public class PaymentParticipationRESTController extends RESTController<PaymentParticipationEntity>{
    @Autowired
    public PaymentParticipationRESTController(PaymentParticipationEntityRepository repository) {
        super(repository);
    }

}
