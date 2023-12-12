package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentParticipationEntity;
import com.flatfusion.backend.repositories.PaymentParticipationEntityRepository;
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
@RequestMapping("/v1/payment-participations")
public class PaymentParticipationRESTController extends RESTController<PaymentParticipationEntity>{

    @Autowired
    private PaymentParticipationEntityRepository paymentParticipationEntityRepository;

    @Autowired
    public PaymentParticipationRESTController(PaymentParticipationEntityRepository repository){
        super(repository);
    }

    //TODO Change and add URL in frontend & Renaming methods
    @GetMapping("/group/{groupId}/user/{userId}/get")
    public ResponseEntity<List<Object[]>> findByGroupIdAndPaymentCreatedByUserId(@PathVariable UUID groupId, @PathVariable UUID userId){
        logger.info("Get Payments get by group id:  " + groupId + " and user id: " + userId);
        Optional<List<Object[]>> entities = paymentParticipationEntityRepository.findByGroupIdAndPaymentCreatedByUserId(groupId, userId);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}/user/{userId}/owe")
    public ResponseEntity<List<Object[]>> findByGroupIdAndParticipation(@PathVariable UUID groupId, @PathVariable UUID userId){
        logger.info("Get Payments owe by group id:  " + groupId + " and user id: " + userId);
        Optional<List<Object[]>> entities = paymentParticipationEntityRepository.findByGroupIdAndParticipation(groupId, userId);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

}
