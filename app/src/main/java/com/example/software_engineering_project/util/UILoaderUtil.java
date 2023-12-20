package com.example.software_engineering_project.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.repository.AppStateRepository;

public class UILoaderUtil {
    private static final String TAG = UILoaderUtil.class.getSimpleName();

    public static void startLoginActivity(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, ActivityLoginScreenController.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);

            // logout user
            AppStateRepository.setCurrentUser(null);
            AppStateRepository.setCurrentGroup(null);
        } else {
            Log.e(TAG, "Context for startLoginActivity() is null");
        }
    }
}
