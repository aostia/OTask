package com.example.aostia.otask;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    public static final String PREFS_LAST_USER = "UserPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZgSjwGm0MVNEkaOdv04ky73pfDnKsNzrEhZIwB1s", "r2TuJJjXNuKxmOSTsLy93T3rewC9plZlsNtIyxWW");

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.usernameText);

        SharedPreferences settings = getSharedPreferences(PREFS_LAST_USER, MODE_PRIVATE);
        String lastName = settings.getString("LastUser", "");
        mEmailView.setText(lastName);

        mPasswordView = (EditText) findViewById(R.id.passwordText);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signInButton = (Button) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    public void attemptLogin() {


        final String username = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.

                    // Associate the device with a user
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("user",ParseUser.getCurrentUser());
                    installation.saveInBackground();

                    SharedPreferences settings = getSharedPreferences(PREFS_LAST_USER, MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("LastUser", username);
                    editor.commit();

                    /*
                    //SENDS PUSH NOTIFICATION
                    ParseQuery pushQuery = ParseInstallation.getQuery();
                    pushQuery.whereEqualTo("user", ParseUser.getCurrentUser());

                    // Send push notification to query
                    ParsePush push = new ParsePush();
                    push.setQuery(pushQuery); // Set our Installation query
                    push.setMessage("Please Work");
                    push.sendInBackground();
                    //END SENDS PUSH NOTIFICATION
                    */
                    //test commit

                    Intent intent = new Intent(LoginActivity.this, MyTasksActivity.class);
                    startActivity(intent);
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    CustomToast.showToast(getApplicationContext(), e.getMessage(), "short");
                }
            }
        });
    }

}



