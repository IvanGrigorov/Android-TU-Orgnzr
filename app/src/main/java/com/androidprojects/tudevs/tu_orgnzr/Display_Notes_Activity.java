package com.androidprojects.tudevs.tu_orgnzr;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.Events.NewNoteOnClickEvent;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadEventTableHelper;

public class Display_Notes_Activity extends AppCompatActivity {

    private ReadEventTableHelper readEventTableHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__notes_);

        Button new_Event_Button = (Button) findViewById(R.id.Create_New_Note_Button);
        new_Event_Button.setOnClickListener(new NewNoteOnClickEvent(this, this));

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.readEventTableHelper = new ReadEventTableHelper(this);
        Cursor allEvents = this.readEventTableHelper.readRows();

        if (allEvents != null) {
            try {
                while (allEvents.moveToNext()) {
                    View v = vi.inflate(R.layout.note_inflation_template, null);

                    // Fill in any details dynamically here
                    TextView note_title = (TextView) v.findViewById(R.id.Note_Title);
                    note_title.setText(allEvents.getString(allEvents.getColumnIndex(ProgrammSQLContract.EventsTable.EVENTS_NAME_COLUMN)));

                    TextView note_deascription = (TextView) v.findViewById(R.id.Note_Description);
                    note_deascription.setText(allEvents.getString(allEvents.getColumnIndex(ProgrammSQLContract.EventsTable.EVENT_DESCRIPTION)));

                    TextView note_date = (TextView) v.findViewById(R.id.Note_Date);
                    note_date.setText(allEvents.getString(allEvents.getColumnIndex(ProgrammSQLContract.EventsTable.EVENT_DATE)));

                    // Insert into main view
                    ViewGroup insertPoint = (ViewGroup) findViewById(R.id.View_Notes_Layout);
                    insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }
            } finally {
                allEvents.close();
            }
        }
    }
}
