package com.example.software_engineering_project.util;

public class ApiPaths {
    public static final String USER = "users";

    public static String getUrlPathByClass(Class entityClass){
        switch (entityClass.toString()){
            case "User": return USER;
            default: return null;
        }
    }

}
