package com.example.aostia.otask;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Aostia on 8/14/2015.
 */
public class CustomToast {
    public static void showToast(Context context, String message, String duration) {
        int length;

        if (duration == "short")
            length = Toast.LENGTH_SHORT;
        else
            length = Toast.LENGTH_LONG;


        Toast toast = Toast.makeText(context, message, length);
        toast.show();
    }

}
