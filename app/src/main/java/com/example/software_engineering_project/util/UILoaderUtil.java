package com.example.software_engineering_project.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.software_engineering_project.controller.ActivityLoginScreenController;

public class UILoaderUtil {
    private static final String TAG = UILoaderUtil.class.getSimpleName();

    public static void startLoginActivity(Context context){
        if (context != null) {
            Intent intent = new Intent(context, ActivityLoginScreenController.class);
            context.startActivity(intent);
        } else {
            Log.e(TAG, "Context for startLoginActivity() is null");
        }
    }
}
