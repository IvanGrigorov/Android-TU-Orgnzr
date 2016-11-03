package com.androidprojects.tudevs.tu_orgnzr.SQLHelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;

/**
 * Created by Ivan Grigorov on 30/04/2016.
 * SQL Query builder to read events from database
 */
public class ReadEventTableHelper {

    private CreateEventDatabaseHelper createEventDatabasHelper;
    private SQLiteDatabase db;

    private long newRowId;

    public ReadEventTableHelper(Context context) {
        this.createEventDatabasHelper = new CreateEventDatabaseHelper(context);
        this.db = this.createEventDatabasHelper.getReadableDatabase();
    }


    public Cursor readRows() {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProgrammSQLContract.EventsTable._ID,
                ProgrammSQLContract.EventsTable.EVENTS_NAME_COLUMN,
                ProgrammSQLContract.EventsTable.EVENT_DESCRIPTION,
                ProgrammSQLContract.EventsTable.EVENT_DATE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ProgrammSQLContract.EventsTable.EVENT_DATE + " DESC";

        Cursor c = db.query(
                ProgrammSQLContract.EventsTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                       // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Log.d("Cursor:", DatabaseUtils.dumpCursorToString(c));
        return c;
    }

    public Cursor readLatestActivity() {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ProgrammSQLContract.EventsTable._ID,
                ProgrammSQLContract.EventsTable.EVENTS_NAME_COLUMN,
                ProgrammSQLContract.EventsTable.EVENT_DESCRIPTION,
                ProgrammSQLContract.EventsTable.EVENT_DATE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ProgrammSQLContract.EventsTable.EVENT_DATE + " ASC LIMIT 1";

        Cursor c = db.query(
                ProgrammSQLContract.EventsTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                       // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        Log.d("Cursor:", DatabaseUtils.dumpCursorToString(c));
        return c;
    }
}
