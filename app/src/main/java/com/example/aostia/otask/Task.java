package com.example.aostia.otask;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Task {
    public static List<ParseObject> returnList = new ArrayList<>();

    public static void createTask(String username) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("task", "The getFirst request failed.");
                } else {
                    Log.d("tsk", "Retrieved the object.");
                }
            }
        });
    }
}
