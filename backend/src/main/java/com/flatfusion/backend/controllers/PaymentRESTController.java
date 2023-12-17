package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.*;
import com.flatfusion.backend.repositories.GroupEntityRepository;
import com.flatfusion.backend.repositories.PaymentEntityRepository;
import com.flatfusion.backend.repositories.PaymentParticipationEntityRepository;
import com.flatfusion.backend.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Controller for handling RESTful endpoints related to payments.
 * Provides endpoints to retrieve payments by group, as well as create payments with participations.
 */
@RestController
@RequestMapping("/v1/payments")
public class PaymentRESTController extends RESTController<PaymentEntity>{
    @Autowired
    private PaymentEntityRepository paymentRepository;
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private GroupEntityRepository groupRepository;
    @Autowired
    private PaymentParticipationEntityRepository paymentParticipationRepository;

    /**
     * Constructs a new PaymentRESTController with the specified repository.
     *
     * @param paymentRepository The repository for payment entities.
     */
    @Autowired
    public PaymentRESTController(PaymentEntityRepository paymentRepository){
        super(paymentRepository);
    }

    /**
     * Retrieves payments by the ID of the group.
     *
     * @param id The ID of the group.
     * @return A list of payments, or HttpStatus.NOT_FOUND if no data is found.
     */
    @GetMapping("/group/{id}")
    public ResponseEntity<List<PaymentEntity>> getPaymentsByGroupId(@PathVariable UUID id){
        logger.info("Get Payments by group id:  " + id);
        Optional<List<PaymentEntity>> entities = paymentRepository.findByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    /**
     * Creates a new payment with participations based on the provided request.
     *
     * @param request The payment creation request containing payment details and participant user IDs.
     * @return The created payment entity, or HttpStatus.UNPROCESSABLE_ENTITY if the request is missing attributes.
     */
    // Overriding method from superclass
    @Transactional
    @PostMapping("/create-with-participations")
    public ResponseEntity<PaymentEntity> createWithParticipations(@RequestBody PaymentCreationRequest request) {
        PaymentEntity payment = request.getPayment();
        List<UserEntity> participatingUsers = new ArrayList<>();

        UUID paidByUserId;
        UUID createdByUserId;
        UUID groupId;

        try{
            // Get user and group instance from the repository
            paidByUserId = payment.getPaidByUser().getId();

            createdByUserId = payment.getCreatedByUser().getId();
            groupId = payment.getGroup().getId();
        } catch (NullPointerException e){
            logger.info("Missing attributes. Payment not saved: " + request.getPayment());
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<UserEntity> paidByUser = userRepository.findById(paidByUserId);
        Optional<UserEntity> createdByUser = userRepository.findById(createdByUserId);
        Optional<GroupEntity> group = groupRepository.findById(groupId);

        // check if elements are existing in db
        if (paidByUser.isPresent() && createdByUser.isPresent() && group.isPresent()){

            payment.setCreatedByUser(createdByUser.get());
            payment.setPaidByUser(paidByUser.get());
            payment.setGroup(group.get());

            paymentRepository.save(payment);
        } else {
            logger.info("Missing attributes. Payment not saved: " + request.getPayment());
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // query participating users
        for(UUID userId : request.getParticipantUserIds()){
            Optional<UserEntity> user = userRepository.findById(userId);

            if(user.isPresent()){
                participatingUsers.add(user.get());
            }
        }

        // create PaymentParticipationEntities
        for(UserEntity user : participatingUsers){

            BigDecimal participationAmount = getParticipationAmountForUser(payment, participatingUsers, user);
            PaymentParticipationEntity paymentParticipation = new PaymentParticipationEntity(payment, group.get(), user, participationAmount);

            String currencyCode = payment.getCurrencyCode();

            // if payment has currency code, also store it in paymentParticipation
            if (currencyCode != null || !currencyCode.isBlank()){
                paymentParticipation.setCurrencyCode(currencyCode);
            }

            // If user has made the payment, then they have automatically paid their part.
            if (user.equals(paidByUser.get())){
                paymentParticipation.setIsPaid(true);
            }

            paymentParticipationRepository.save(paymentParticipation);
            logger.info("PaymentParticipation saved: " + paymentParticipation);
        }

        logger.info("Payment created: " + payment);

        //save in repository
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    private BigDecimal getParticipationAmountForUser(PaymentEntity payment, List<UserEntity> participatingUsers, UserEntity user) {
        int participantCount = participatingUsers.size();
        BigDecimal paymentAmount = payment.getAmount();

        if(participantCount == 0) {
            return new BigDecimal(0);
        }

        // Half even round mode to minimize cumulative error
        return paymentAmount.divide(new BigDecimal(participantCount), 2, RoundingMode.HALF_EVEN);
    }
}
