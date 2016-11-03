package com.androidprojects.tudevs.tu_orgnzr.SQLHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidprojects.tudevs.tu_orgnzr.Config.Config;
import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;

/**
 * Created by Ivan Grigorov on 30/04/2016.
 * Helps to initialize the Event Table
 */
public class CreateEventDatabaseHelper extends SQLiteOpenHelper{

    public CreateEventDatabaseHelper(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);

        // Update version for next changing
        // Increment only the first time ()
        if (Config.DATABASE_VERSION == 1) {
            Config.DATABASE_VERSION += 1;
            Log.d("Constructor called: ", Integer.toString(Config.DATABASE_VERSION));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_EVENT_TABLE =
                "CREATE TABLE IF NOT EXISTS " + ProgrammSQLContract.EventsTable.TABLE_NAME + "(" +
                        ProgrammSQLContract.EventsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        ProgrammSQLContract.EventsTable.EVENTS_NAME_COLUMN + " TEXT NOT NULL, " +
                        ProgrammSQLContract.EventsTable.EVENT_DESCRIPTION + " TEXT NOT NULL, " +
                        ProgrammSQLContract.EventsTable.EVENT_DATE + " TEXT NOT NULL " + ");";

        db.execSQL(SQL_CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String SQL_CREATE_EVENT_TABLE =
                "CREATE TABLE IF NOT EXISTS " + ProgrammSQLContract.EventsTable.TABLE_NAME + "(" +
                        ProgrammSQLContract.EventsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        ProgrammSQLContract.EventsTable.EVENTS_NAME_COLUMN + " TEXT NOT NULL, " +
                        ProgrammSQLContract.EventsTable.EVENT_DESCRIPTION + " TEXT NOT NULL, " +
                        ProgrammSQLContract.EventsTable.EVENT_DATE + " TEXT NOT NULL " + ");";

        db.execSQL(SQL_CREATE_EVENT_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
