package com.example.software_engineering_project.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class for displaying Toast messages in Android applications.
 */
public class ToastUtil {

    /**
     * Displays a short-duration Toast message.
     *
     * @param context The application context.
     */
    public static void makeToast(String s, Context context) {
        Toast t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }
}
