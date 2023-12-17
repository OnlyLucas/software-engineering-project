package com.example.software_engineering_project.util;

/**
 * Utility class for managing API paths.
 */
public class ApiPaths {

    /**
     * API path constant for users.
     */
    public static final String USER = "users";

    /**
     * Gets the API path based on the provided entity class.
     *
     * @param entityClass The class representing an entity.
     * @return The corresponding API path or null if not found.
     */
    public static String getUrlPathByClass(Class entityClass){
        switch (entityClass.toString()){
            case "User": return USER;
            default: return null;
        }
    }

}
