package com.example.aostia.otask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TimePicker;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreateTaskActivity extends Activity {
    //private String username;
    private TimePicker timePicker;
    private DatePicker datePicker;
    private EditText messageText;
    private String username;
    private ParseUser userNewTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        //username = ParseUser.getCurrentUser().getUsername().toString();
        username = getIntent().getStringExtra("username").toString();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    userNewTask = objects.get(0);
                } else {
                    // Something went wrong.
                }
            }
        });

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);

        datePicker = (DatePicker)findViewById(R.id.datePicker);

        messageText = (EditText)findViewById(R.id.createMessageText);

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();
        tabHost.getTabWidget().setDividerDrawable(R.drawable.abc_list_divider_mtrl_alpha);

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.createMessageTab);
        spec1.setIndicator("Message");

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2");
        spec2.setContent(R.id.createDateTab);
        spec2.setIndicator("Date");

        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab 3");
        spec3.setContent(R.id.createTimeTab);
        spec3.setIndicator("Time");

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_task, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createTask(View view) {

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();

        Task.createTask(userNewTask, messageText.getText().toString(), date);

        Intent inent = new Intent(CreateTaskActivity.this, MyTasksActivity.class);
        startActivity(inent);

    }
}
