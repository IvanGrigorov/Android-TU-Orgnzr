package com.androidprojects.tudevs.tu_orgnzr.SQLHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;

import javax.inject.Inject;

/**
 * Created by Ivan Grigorov on 23/04/2016.
 * SQL Query builder to read subjects from database
 */
public class ReadProgrammTableHelper {


    private CreateDatabaseHelper createDatabaseHelper;
    private SQLiteDatabase db;

    private long newRowId;

    @Inject
    public ReadProgrammTableHelper(Context context) {
        this.createDatabaseHelper = new CreateDatabaseHelper(context);
        this.db = this.createDatabaseHelper.getReadableDatabase();
    }


    public Cursor readRows(String selectionArgs) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProgrammSQLContract.SubjectTable._ID,
                ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN,
                ProgrammSQLContract.SubjectTable.STARTS_AT_COLUMN,
                ProgrammSQLContract.SubjectTable.ENDS_AT_COLUMN,
                ProgrammSQLContract.SubjectTable.DAY_OF_WEEK_COLUMN,
                ProgrammSQLContract.SubjectTable.BUILDING_COLUMN
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ProgrammSQLContract.SubjectTable._ID + " DESC";

        String selection = ProgrammSQLContract.SubjectTable.DAY_OF_WEEK_COLUMN + " = ?";

        String[] selArgs = {selectionArgs};
        Cursor c = db.query(
                ProgrammSQLContract.SubjectTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selArgs,                       // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Log.d("Cursor:", DatabaseUtils.dumpCursorToString(c));
        return c;
    }

    public Cursor readTheNextComingLectureInfo(String selectionArgs) {
        String[] projection = {ProgrammSQLContract.SubjectTable.BUILDING_COLUMN};


        String[] selArgs = {selectionArgs + "%"};
        String selection = ProgrammSQLContract.SubjectTable.STARTS_AT_COLUMN + " LIKE ?";
        Cursor c = this.db.query(
                ProgrammSQLContract.SubjectTable.TABLE_NAME,
                projection,
                selection,
                selArgs,
                null,
                null,
                null
        );

        return c;
    }


    public boolean checkIfProgrammIsSavedForEachDay(String dayParameter) {
        String[] projection = {ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN};
        String[] selArgs = {dayParameter};
        String selection = ProgrammSQLContract.SubjectTable.DAY_OF_WEEK_COLUMN + " = ?";
        Cursor c = this.db.query(
                ProgrammSQLContract.SubjectTable.TABLE_NAME,
                projection,
                selection,
                selArgs,
                null,
                null,
                null
        );
        if (c.getCount() <= 0) {
            c.close();
            return false;
        } else {
            c.close();
            return true;
        }
    }
}
