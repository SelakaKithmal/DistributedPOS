package com.distributedpos.app.helpers;


import android.text.TextUtils;

public class ValidationHelper {

    public static boolean isValidEmail(String email) {
        boolean result = false;

        if (!TextUtils.isEmpty(email)) {
            result = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }

        return result;
    }
}
