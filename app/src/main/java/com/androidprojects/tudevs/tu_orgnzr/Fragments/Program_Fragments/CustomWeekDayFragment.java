package com.androidprojects.tudevs.tu_orgnzr.Fragments.Program_Fragments;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidprojects.tudevs.tu_orgnzr.Adnroid_TUOrgnzr;
import com.androidprojects.tudevs.tu_orgnzr.Config.ColorsEnum;
import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.ProgrammDAO;

import io.reactivex.Observable;

public class CustomWeekDayFragment extends Fragment {

    protected void loadProgramTable(ProgrammDAO[] programDAOs, TableLayout tableLayout) {
        if (programDAOs == null) {
            return;
        }
        int indexOfTableViewRow = programDAOs.length - 1;
        String currentLecture = "";
        String currentColor = ColorsEnum.randomColor().getValue();

        for (ProgrammDAO programDAO : programDAOs) {
            View tableRow = tableLayout.getChildAt(indexOfTableViewRow);
            View time = ((TableRow) tableRow).getChildAt(0);
            View lecture = ((TableRow) tableRow).getChildAt(1);
            View lecturer = ((TableRow) tableRow).getChildAt(2);
            View building = ((TableRow) tableRow).getChildAt(3);
            // Adding different colors to each subject from table
            if (currentLecture.equals(programDAO.getLecture())) {
                tableRow.setBackgroundColor(Color.parseColor(currentColor));
                currentLecture = programDAO.getLecture();
            } else {
                String oldColor = currentColor;
                while (currentColor.equals(oldColor)) {
                    currentColor = ColorsEnum.randomColor().getValue();
                }
                tableRow.setBackgroundColor(Color.parseColor(currentColor));
                currentLecture = programDAO.getLecture();
            }
            ((TextView) lecture).setText(programDAO.getLecture());
            ((TextView) building).setText(programDAO.getBuilding());

            indexOfTableViewRow--;
        }
    }

    protected Observable<ProgrammDAO[]> getProgrammForSpecificDay(String day) {
        return Observable.create(e -> {
            ProgrammDAO[] programForTheDay = Adnroid_TUOrgnzr.getDataBase().programmDAOContractor().returnAllSubjects(day);
            if (programForTheDay == null) {
                e.onComplete();
            } else {
                e.onNext(programForTheDay);
            }
        });
    }

}