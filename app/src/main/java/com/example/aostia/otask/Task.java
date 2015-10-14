package com.example.aostia.otask;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task {
    public static List<ParseObject> returnList = new ArrayList<>();

    public static void createTask(ParseUser user, String message, Date date) {

        ParseObject tasks = new ParseObject("Task");
        tasks.put("user", user);
        tasks.put("taskMessage", message);
        tasks.put("dueDate", date);
        tasks.put("completed", false);
        tasks.saveInBackground();

        //sendPushNotification(user, "Test", date);
    }

    public static String customDateToString(Date date) {
        String customToString = date.toString();
        customToString = customToString.substring(0, 10);

        return customToString;
    }
}
