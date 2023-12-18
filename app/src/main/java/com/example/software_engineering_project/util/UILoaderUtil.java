package com.example.software_engineering_project.util;

import android.content.Context;
import android.content.Intent;

import com.example.software_engineering_project.controller.ActivityLoginScreenController;

public class UILoaderUtil {

    public static void startLoginActivity(Context context){
        if (context != null) {
            Intent intent = new Intent(context, ActivityLoginScreenController.class);
            context.startActivity(intent);
        } else {
            //TODO Log
            System.out.println("Context for startLoginActivity() is null");
        }
    }
}
