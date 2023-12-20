package com.example.software_engineering_project.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.software_engineering_project.controller.ActivityLoginScreenController;
import com.example.software_engineering_project.repository.AppStateRepository;

/**
 * Utility class for handling UI-related operations, such as starting activities.
 * This class provides methods to start the login activity and perform related actions.
 */
 public class UILoaderUtil {
    private static final String TAG = UILoaderUtil.class.getSimpleName();

    /**
     * Starts the login activity and logs out the current user.
     *
     * @param context The application context for starting the login activity.
     */
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
