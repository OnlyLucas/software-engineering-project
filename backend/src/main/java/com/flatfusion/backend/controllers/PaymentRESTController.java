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

@RestController
@RequestMapping("/v1/payments")
public class PaymentRESTController extends RESTController<PaymentEntity>{
    @Autowired
    PaymentEntityRepository paymentRepository;
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private GroupEntityRepository groupRepository;
    @Autowired
    private PaymentParticipationEntityRepository paymentParticipationRepository;

    @Autowired
    public PaymentRESTController(PaymentEntityRepository paymentRepository,
                                 PaymentParticipationEntityRepository paymentParticipationEntityRepository) {
        super(paymentRepository);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<PaymentEntity>> getPaymentsByGroupId(@PathVariable UUID id){
        logger.info("Get Payments by group id:  " + id);
        Optional<List<PaymentEntity>> entities = paymentRepository.findByGroupId(id);

        if(entities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(entities.get(), HttpStatus.OK);
    }

    // Overriding method from superclass
    @Transactional
    @PostMapping("/create-with-participations")
    public ResponseEntity<PaymentEntity> createWithParticipations(@RequestBody PaymentCreationRequest request) {
        PaymentEntity payment = request.getPayment();;
        List<UserEntity> participatingUsers = new ArrayList<>();

        UUID paidByUserId;
        UUID createdByUserId;
        UUID groupId;

        System.out.println(request.getPayment().toString());
        System.out.println(request.toString());

        try{
            // Get user and group instance from the repository
            paidByUserId = payment.getPaidByUser().getId();

            // TODO check against authentication credentials
            createdByUserId = payment.getCreatedByUser().getId();
            groupId = payment.getGroup().getId();
        } catch (NullPointerException e){
            logger.info("Missing attributes. Payment not saved: " + request.getPayment());
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<UserEntity> paidByUser = userRepository.findById(paidByUserId);
        Optional<UserEntity> createdByUser = userRepository.findById(createdByUserId);
        Optional<GroupEntity> group = groupRepository.findById(groupId);

        System.out.println(payment);

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

            // TODO improve the allocation function
            BigDecimal participationAmount = getParticipationAmountForUser(payment, participatingUsers, user);
            PaymentParticipationEntity paymentParticipation = new PaymentParticipationEntity(payment, group.get(), user, participationAmount);

            String currencyCode = payment.getCurrencyCode();

            // if payment has currency code, also store it in paymentParticipation
            if (currencyCode != null || !currencyCode.isBlank()){
                paymentParticipation.setCurrencyCode(currencyCode);
            }

            paymentParticipationRepository.save(paymentParticipation);
            logger.info("PaymentParticipation saved: " + paymentParticipation);
        }

        logger.info("Payment created: " + payment);

        //save in repository
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentEntity> delete(@PathVariable UUID id) {
        Optional<PaymentEntity> paymentOptional = repository.findById(id);
        if (paymentOptional.isPresent()) {
            PaymentEntity payment = paymentOptional.get();
            Set<PaymentParticipationEntity> paymentParticipations = payment.getPaymentParticipations();

            if(paymentParticipations != null){
                paymentParticipationRepository.deleteAll(payment.getPaymentParticipations());
                logger.info("PaymentParticipations deleted: " + paymentParticipations);
            }

            repository.deleteById(id);
            logger.info("Payment deleted: " + payment);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.info("Payment not found. Hence no deletion. ID:" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




    private BigDecimal getParticipationAmountForUser(PaymentEntity payment, List<UserEntity> participatingUsers, UserEntity user) {
        int participantCount = participatingUsers.size();
        BigDecimal paymentAmount = payment.getAmount();

        if(participantCount == 0)
            return new BigDecimal(0);

        // Half even round mode to minimize cumulative error
        return paymentAmount.divide(new BigDecimal(participantCount), RoundingMode.HALF_EVEN);
    }
}
