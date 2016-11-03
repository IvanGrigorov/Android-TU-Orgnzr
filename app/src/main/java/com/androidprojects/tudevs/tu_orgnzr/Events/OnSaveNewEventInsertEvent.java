package com.androidprojects.tudevs.tu_orgnzr.Events;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.androidprojects.tudevs.tu_orgnzr.Config.Config;
import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.Create_New_Note_Activity;
import com.androidprojects.tudevs.tu_orgnzr.Display_Notes_Activity;
import com.androidprojects.tudevs.tu_orgnzr.Profile_Activity;
import com.androidprojects.tudevs.tu_orgnzr.R;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ImportNewEventHelper;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Ivan Grigorov on 30/04/2016.
 * Event is triggered when saving new event
 */
public class OnSaveNewEventInsertEvent extends AbstractEvent implements View.OnClickListener {


    // The request id constant for requesting response from Calendar intent
    public static final int CREATE_EVENT = 1;

    public OnSaveNewEventInsertEvent(Context context, Activity activity) {
        super(context, activity);
    }

    @Override
    public void onClick(View v) {

        // Get user inputs and generate new event based on the input
        String eventTitle = ((EditText) this.activity.findViewById(R.id.Title_Of_Note_Input)).getText().toString();
        String eventDescription = ((EditText) this.activity.findViewById(R.id.Description_Of_Note_Input)).getText().toString();

        // Building the date
        String eventDate = "";
        eventDate = eventDate + ((Spinner) this.activity.findViewById(R.id.Day_Of_Month)).getSelectedItem().toString() + ":";
        eventDate = eventDate + ((Spinner) this.activity.findViewById(R.id.Select_Month)).getSelectedItem().toString() + ":";
        eventDate = eventDate + ((Spinner) this.activity.findViewById(R.id.Select_Year)).getSelectedItem().toString();
        ImportNewEventHelper newEventHelper = new ImportNewEventHelper(this.context);
        newEventHelper.getContentValues().put(ProgrammSQLContract.EventsTable.EVENTS_NAME_COLUMN, eventTitle);
        newEventHelper.getContentValues().put(ProgrammSQLContract.EventsTable.EVENT_DESCRIPTION, eventDescription);
        newEventHelper.getContentValues().put(ProgrammSQLContract.EventsTable.EVENT_DATE, eventDate);

        // Return month as a number from 1 to 12
        int numberOfSelectedMonth = -1;
        switch (((Spinner) this.activity.findViewById(R.id.Select_Month)).getSelectedItem().toString()) {
            case ("January"):
                numberOfSelectedMonth = 0;
                break;
            case ("February"):
                numberOfSelectedMonth = 1;
                break;
            case ("March"):
                numberOfSelectedMonth = 2;
                break;
            case ("April"):
                numberOfSelectedMonth = 3;
                break;
            case ("May"):
                numberOfSelectedMonth = 4;
                break;
            case ("June"):
                numberOfSelectedMonth = 5;
                break;
            case ("July"):
                numberOfSelectedMonth = 6;
                break;
            case ("August"):
                numberOfSelectedMonth = 7;
                break;
            case ("September"):
                numberOfSelectedMonth = 8;
                break;
            case ("October"):
                numberOfSelectedMonth = 9;
                break;
            case ("November"):
                numberOfSelectedMonth = 10;
                break;
            case ("December"):
                numberOfSelectedMonth = 11;
                break;
            default:
                numberOfSelectedMonth = -1;
                break;
        }

        // Check if maximum number of events is reached

        final String value = this.activity.getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Config.eventKey, Config.defaultValue);

        // If it is the first event
        if (value.equals(Config.defaultValue)) {
            int startNumber = 1;
            this.activity.getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putString(Config.eventKey, Integer.toString(startNumber).toString()).commit();
            Toast.makeText(this.activity.getApplicationContext(), "New Event is stored.", Toast.LENGTH_SHORT).show();

            newEventHelper.InsertValues();

            // Add the event to the native calendar (default time for notification 10:00 AM)

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(((Spinner) this.activity.findViewById(R.id.Day_Of_Month)).getSelectedItem().toString()));
            cal.set(Calendar.MONTH, numberOfSelectedMonth);
            cal.set(Calendar.YEAR, Integer.parseInt(((Spinner) this.activity.findViewById(R.id.Select_Year)).getSelectedItem().toString()));
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 * 60 * 1000);
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            intent.putExtra(CalendarContract.Events.CALENDAR_TIME_ZONE, TimeZone.getDefault().getID());
            intent.putExtra(CalendarContract.Events.TITLE, eventTitle);
            intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDescription);
            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
            this.activity.startActivityForResult(intent, CREATE_EVENT);

        }

        // If maximum is reached
        else if (value.equals("20")) {
            Toast.makeText(this.activity.getApplicationContext(), "Maximum amount of Events(" + value + ") is reached.", Toast.LENGTH_SHORT).show();
        }

        // Default behaviour
        else {
            int newEventNumber = Integer.parseInt(value);
            newEventNumber++;
            Toast.makeText(this.activity.getApplicationContext(), "New Event is stored.", Toast.LENGTH_SHORT).show();
            this.activity.getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putString(Config.eventKey, Integer.toString(newEventNumber).toString()).commit();

            newEventHelper.InsertValues();

            // Add the event to the native calendar (default time for notification 10:00 AM)

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(((Spinner) this.activity.findViewById(R.id.Day_Of_Month)).getSelectedItem().toString()));
            cal.set(Calendar.MONTH, numberOfSelectedMonth);
            cal.set(Calendar.YEAR, Integer.parseInt(((Spinner) this.activity.findViewById(R.id.Select_Year)).getSelectedItem().toString()));
            cal.set(Calendar.HOUR_OF_DAY, 10);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 * 60 * 1000);
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            intent.putExtra(CalendarContract.Events.CALENDAR_TIME_ZONE, TimeZone.getDefault().getID());
            intent.putExtra(CalendarContract.Events.TITLE, eventTitle);
            intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDescription);
            intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
            this.activity.startActivityForResult(intent, CREATE_EVENT);

        }
    }

}
