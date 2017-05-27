package com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.androidprojects.tudevs.tu_orgnzr.Events.EditProgrammOnClickEvent;
import com.androidprojects.tudevs.tu_orgnzr.R;
import com.androidprojects.tudevs.tu_orgnzr.databinding.FragmentWednesdayProgramBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan Grigorov on 17/04/2016.
 * Inflates the Monday Fragment
 */
public class WednesdayFragment extends CustomWeekDayFragment {

    // Helper to read from the database(Programm Table)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the XML for the fragment
        FragmentWednesdayProgramBinding wednesday = DataBindingUtil.inflate(inflater, R.layout.fragment_wednesday_program, container, false);
        TextView day_of_week_textview = wednesday.DayOfWeek;
        Button editButton = wednesday.ImportedTable.EditButton;
        editButton.setOnClickListener(new EditProgrammOnClickEvent(this.getActivity().getApplicationContext(), getActivity()));

        // Read all the rows from the database and insert the information in the activity
        TableLayout tableContainer = wednesday.ImportedTable.ProgrammTable;

        getProgrammForSpecificDay(day_of_week_textview.getText().toString()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(programmDAOs -> {
                    loadProgramTable(programmDAOs, tableContainer);
                });

        return wednesday.getRoot();
    }
}
