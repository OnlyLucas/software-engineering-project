package com.example.software_engineering_project.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void makeToast(String s, Context context) {
        Toast t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }
}
