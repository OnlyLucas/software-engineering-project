package com.flatfusion.backend.controllers;

import com.flatfusion.backend.entities.PaymentsChangeEntity;
import com.flatfusion.backend.repositories.PaymentsChangeEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling RESTful endpoints related to payments changes.
 * Provides basic CRUD operations for payments changes.
 */
@RestController
@RequestMapping("/v1/payments-changes")
public class PaymentsChangeRESTController extends RESTController<PaymentsChangeEntity>{

    /**
     * Constructs a new PaymentsChangeRESTController with the specified repository.
     *
     * @param repository The repository for payments change entities.
     */
    @Autowired
    public PaymentsChangeRESTController(PaymentsChangeEntityRepository repository) {
        super(repository);
    }

}
