package com.androidprojects.tudevs.tu_orgnzr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.androidprojects.tudevs.tu_orgnzr.Events.AddPhotoToNoteEvent;
import com.androidprojects.tudevs.tu_orgnzr.Events.OnSaveNewEventInsertEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Create_New_Note_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__new__note_);

        Spinner days_Sinner = (Spinner) findViewById(R.id.Day_Of_Month);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> month_Days_Adapter = ArrayAdapter.createFromResource(this,
                R.array.Days_Of_Month, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        month_Days_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        days_Sinner.setAdapter(month_Days_Adapter);

        Spinner months_Spinner = (Spinner) findViewById(R.id.Select_Month);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> months_Adpater = ArrayAdapter.createFromResource(this,
                R.array.Months, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        months_Adpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        months_Spinner.setAdapter(months_Adpater);

        // Populate Spinner for the years dynamicly with the next two years including the current one

        String currentDate = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

        ArrayList<String> yearsValues = new ArrayList<String>();

        for (int i = 0; i < 4; i++) {
            int newDate = Integer.parseInt(currentDate) + i;
            yearsValues.add(Integer.toString(newDate));
        }

        Spinner years_Spinner = (Spinner) findViewById(R.id.Select_Year);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> years_Adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, yearsValues);
        // Specify the layout to use when the list of choices appears
        years_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        years_Spinner.setAdapter(years_Adapter);

        Button new_Event_Button = (Button) findViewById(R.id.Save_New_Event_Button);
        new_Event_Button.setOnClickListener(new OnSaveNewEventInsertEvent(this, this));

        FloatingActionButton floating_button_pic = (FloatingActionButton) findViewById(R.id.FloatingButton_Pic);
        floating_button_pic.setOnClickListener(new AddPhotoToNoteEvent(this, this));
    }

    // Decides what to do next after receiving a response after finishing the Calendar Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        // CREATE_EVENT for Calendar Intent
        if (requestCode == OnSaveNewEventInsertEvent.CREATE_EVENT) {
            // Make sure the request was successful or cancelled
            // Redirects to next Activit
            if ((resultCode == RESULT_OK) || (resultCode == RESULT_CANCELED)) {
                Intent openNewActivityIntent = new Intent(this, Display_Notes_Activity.class);
                startActivity(openNewActivityIntent);
                finish();
            }
        } else if (requestCode == AddPhotoToNoteEvent.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            // Bundle extras = data.getExtras();
            // Bitmap imageBitmap = (Bitmap) extras.get("data");
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
