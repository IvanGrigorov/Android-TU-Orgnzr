package com.androidprojects.tudevs.tu_orgnzr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.androidprojects.tudevs.tu_orgnzr.Events.OnSaveNewProgrammInsertedEvent;

public class Edit_Program_Mode extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__program__mode);

        Spinner day_Of_Week_Spinner = (Spinner) findViewById(R.id.Day_Of_Week_Spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> weekdays_adapter = ArrayAdapter.createFromResource(this,
                R.array.WeekDays, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        weekdays_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        day_Of_Week_Spinner.setAdapter(weekdays_adapter);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> subjects_adapter = ArrayAdapter.createFromResource(this,
                R.array.Subjects, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        subjects_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        TableLayout container = (TableLayout)findViewById(R.id.Edit_Programm_Table);

        // Select all children of the table layout ---> table rows
        // Apllly to them the subject_adapter
        int count = container.getChildCount();
        for (int i=0; i < count - 1; i++) {
            View tableRow = container.getChildAt(i);
            View Spinner = ((TableRow) tableRow).getChildAt(1);
            ((Spinner) Spinner).setAdapter(subjects_adapter);
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> buildings_adapter = ArrayAdapter.createFromResource(this,
            R.array.Buildings, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        subjects_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Select all children of the table layout ---> table rows
        // Apllly to them the buildings_adapter
        for (int i=0; i < count - 1; i++) {
        View tableRow = container.getChildAt(i);
        View Spinner = ((TableRow) tableRow).getChildAt(3);
        ((Spinner) Spinner).setAdapter(buildings_adapter);
        }

        Button saveButton = (Button) findViewById(R.id.Save_Programm_Button);
        saveButton.setOnClickListener(new OnSaveNewProgrammInsertedEvent(this, this, container));
    }
}
