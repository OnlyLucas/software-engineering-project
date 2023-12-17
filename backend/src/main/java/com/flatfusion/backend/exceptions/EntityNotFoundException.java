package com.flatfusion.backend.exceptions;

/**
 * A runtime exception indicating that no entity was found for the specified parameters.
 */
public class EntityNotFoundException extends RuntimeException{

    /**
     * Constructs an {@code EntityNotFoundException} with a default error message.
     */
    public EntityNotFoundException(){
        super("No entity was found for the parameters.");
    }
}
