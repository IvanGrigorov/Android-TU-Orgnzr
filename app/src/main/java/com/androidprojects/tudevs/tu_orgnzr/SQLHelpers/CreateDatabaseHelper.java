package com.androidprojects.tudevs.tu_orgnzr.SQLHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidprojects.tudevs.tu_orgnzr.Config.Config;
import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;

/**
 * Created by Ivan Grigorov on 20/04/2016.
 * Helps to initialize the Subject Table
 */
public class CreateDatabaseHelper extends SQLiteOpenHelper {

    public CreateDatabaseHelper(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
        Log.d("Constructor called 2: ", Integer.toString(Config.DATABASE_VERSION));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PROGRAMM_TABLE =
                "CREATE TABLE IF NOT EXISTS " + ProgrammSQLContract.SubjectTable.TABLE_NAME + "(" +
                        ProgrammSQLContract.SubjectTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        ProgrammSQLContract.SubjectTable.LECTURE_NAME_COLUMN + " TEXT NOT NULL, " +
                        ProgrammSQLContract.SubjectTable.DAY_OF_WEEK_COLUMN + " TEXT NOT NULL, " +
                        ProgrammSQLContract.SubjectTable.STARTS_AT_COLUMN + " TEXT NOT NULL, " +
                        ProgrammSQLContract.SubjectTable.ENDS_AT_COLUMN + " TEXT NOT NULL, " +
                        ProgrammSQLContract.SubjectTable.BUILDING_COLUMN + " TEXT OT NULL);";

        db.execSQL(SQL_CREATE_PROGRAMM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
}
