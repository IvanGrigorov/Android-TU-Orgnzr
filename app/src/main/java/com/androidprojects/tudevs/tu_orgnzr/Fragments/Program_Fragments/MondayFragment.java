package com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidprojects.tudevs.tu_orgnzr.Config.ColorsEnum;
import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.Events.EditProgrammOnClickEvent;
import com.androidprojects.tudevs.tu_orgnzr.R;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadProgrammTableHelper;

/**
 * Created by Ivan Grigorov on 17/04/2016.
 * Inflates the Monday Fragment
 */
public class MondayFragment extends Fragment {

    // Helper to read from the database(Programm Table)
    ReadProgrammTableHelper readProgrammHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the XML for the fragment
        View monday = inflater.inflate(R.layout.fragment_monday_program, container, false);
        TextView day_of_week_textview = (TextView) monday.findViewById(R.id.Day_Of_Week);
        readProgrammHelper = new ReadProgrammTableHelper(monday.getContext(), day_of_week_textview.getText().toString());
        Button editButton = (Button) monday.findViewById(R.id.Edit_Button);
        editButton.setOnClickListener(new EditProgrammOnClickEvent(getContext(), getActivity()));
        Cursor allSubjects = readProgrammHelper.readRows();

        // Read all the rows from the database and insert the information in the activity
        LinearLayout imprtedTable = (LinearLayout) monday.findViewById(R.id.Imported_Table);
        TableLayout tableContainer = (TableLayout) imprtedTable.findViewById(R.id.Programm_Table);

        String currentLecture = "";
        String currentColor = ColorsEnum.randomColor().getValue();
        int indexOfTableViewRow = allSubjects.getCount() - 1;
        if (allSubjects != null) {
            try {
                while (allSubjects.moveToNext()) {
                    View tableRow = tableContainer.getChildAt(indexOfTableViewRow);
                    View time = ((TableRow) tableRow).getChildAt(0);
                    View lecture = ((TableRow) tableRow).getChildAt(1);
                    View lecturer = ((TableRow) tableRow).getChildAt(2);
                    View building = ((TableRow) tableRow).getChildAt(3);
                    // Adding different colors to each subject from table
                    if (currentLecture.equals(allSubjects.getString(allSubjects.getColumnIndex(ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN)))) {
                        tableRow.setBackgroundColor(Color.parseColor(currentColor));
                        currentLecture = allSubjects.getString(allSubjects.getColumnIndex(ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN));
                    } else {
                        String oldColor = currentColor;
                        while (currentColor.equals(oldColor)) {
                            currentColor = ColorsEnum.randomColor().getValue();
                        }
                        tableRow.setBackgroundColor(Color.parseColor(currentColor));
                        currentLecture = allSubjects.getString(allSubjects.getColumnIndex(ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN));
                    }
                    ((TextView) lecture).setText(allSubjects.getString(allSubjects.getColumnIndex(ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN)));
                    ((TextView) building).setText(allSubjects.getString(allSubjects.getColumnIndex(ProgrammSQLContract.SubjectTable.BUILDING_COLUMN)));

                    indexOfTableViewRow--;
                }
            } finally {
                allSubjects.close();
            }
        }
        return monday;
    }
}
