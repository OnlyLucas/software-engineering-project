package com.flatfusion.backend.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(){
        super("No entity was found for the parameters.");
    }
}
