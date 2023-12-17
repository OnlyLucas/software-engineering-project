package com.flatfusion.backend.entities;

import java.util.UUID;

/**
 * An interface representing entities with a unique identifier.
 */
public interface EntityInterface {

    /**
     * Sets the unique identifier for the entity.
     *
     * @param id The unique identifier to set.
     */
    public void setId(UUID id);
}
