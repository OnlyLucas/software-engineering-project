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

/**
 * Controller for handling RESTful endpoints related to payment participations.
 * Provides endpoints to retrieve payment information grouped by user, including
 * get payments, owe payments, get payment participations, and owe payment participations.
 */
@RestController
@RequestMapping("/v1/payment-participations")
public class PaymentParticipationRESTController extends RESTController<PaymentParticipationEntity>{

    @Autowired
    private PaymentParticipationEntityRepository paymentParticipationEntityRepository;

    /**
     * Constructs a new PaymentParticipationRESTController with the specified repository.
     *
     * @param repository The repository for payment participation entities.
     */
    @Autowired
    public PaymentParticipationRESTController(PaymentParticipationEntityRepository repository){
        super(repository);
    }

    /**
     * Retrieves payments where the specified user is the recipient (gets payments) in the given group.
     *
     * @param groupId The ID of the group.
     * @param userId  The ID of the user.
     * @return A list of payments grouped by user, or HttpStatus.NOT_FOUND if no data is found.
     */
    @GetMapping("/group/{groupId}/user/{userId}/get")
    public ResponseEntity<List<Object[]>> getGetPaymentsGroupedByUser(@PathVariable UUID groupId, @PathVariable UUID userId){
        logger.info("Get Payments get by group id:  " + groupId + " and user id: " + userId);
        Optional<List<Object[]>> entities = paymentParticipationEntityRepository.getGetPaymentsGroupedByUser(groupId, userId);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    /**
     * Retrieves payments where the specified user owes payments in the given group.
     *
     * @param groupId The ID of the group.
     * @param userId  The ID of the user.
     * @return A list of payments grouped by user, or HttpStatus.NOT_FOUND if no data is found.
     */
    @GetMapping("/group/{groupId}/user/{userId}/owe")
    public ResponseEntity<List<Object[]>> getOwePaymentsGroupedByUser(@PathVariable UUID groupId, @PathVariable UUID userId){
        logger.info("Get Payments owe by group id:  " + groupId + " and user id: " + userId);
        Optional<List<Object[]>> entities = paymentParticipationEntityRepository.getOwePaymentsGroupedByUser(groupId, userId);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    /**
     * Retrieves payment participations where one user owes and another user gets in the given group.
     *
     * @param groupId   The ID of the group.
     * @param userIdOwe The ID of the user who owes.
     * @param userIdGet The ID of the user who gets.
     * @return A list of payment participations, or HttpStatus.NOT_FOUND if no data is found.
     */
    @GetMapping("/group/{groupId}/userOwe/{userIdOwe}/userGet/{userIdGet}")
    public ResponseEntity<List<PaymentParticipationEntity>> getGetPaymentParticipationsByUserIds(
            @PathVariable UUID groupId,
            @PathVariable UUID userIdOwe,
            @PathVariable UUID userIdGet
    ) {
        logger.info("Get Payment Participations Get by group id:  " + groupId + " and user id owe: " + userIdOwe +
                " and user id get: " + userIdGet);

        Optional<List<PaymentParticipationEntity>> paymentParticipations =
                paymentParticipationEntityRepository.getGetPaymentParticipationsByUserIds(groupId, userIdOwe, userIdGet);

        if(paymentParticipations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(paymentParticipations.get(), HttpStatus.OK);
    }

    /**
     * Retrieves payment participations where one user gets and another user owes in the given group.
     *
     * @param groupId   The ID of the group.
     * @param userIdGet The ID of the user who gets.
     * @param userIdOwe The ID of the user who owes.
     * @return A list of payment participations, or HttpStatus.NOT_FOUND if no data is found.
     */
    @GetMapping("/group/{groupId}/userGet/{userIdGet}/userOwe/{userIdOwe}")
    public ResponseEntity<List<PaymentParticipationEntity>> getOwePaymentParticipationsByUserIds(
            @PathVariable UUID groupId,
            @PathVariable UUID userIdGet,
            @PathVariable UUID userIdOwe
    ) {
        logger.info("Get Payment Participations Owe by group id:  " + groupId + " and user id get: " + userIdGet +
                " and user id owe: " + userIdOwe);

        Optional<List<PaymentParticipationEntity>> paymentParticipations =
                paymentParticipationEntityRepository.getOwePaymentParticipationsByUserIds(groupId, userIdGet, userIdOwe);

        if(paymentParticipations.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(paymentParticipations.get(), HttpStatus.OK);
    }



}
