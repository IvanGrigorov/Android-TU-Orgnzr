package com.androidprojects.tudevs.tu_orgnzr.SQLHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.Interfaces.IInsertValuesToDatabase;

/**
 * Created by Ivan Grigorov on 30/04/2016.
 */
public class ImportNewEventHelper implements IInsertValuesToDatabase {

    private CreateEventDatabaseHelper createEbentDatabasHelper;
    private SQLiteDatabase db;
    //ContentValues contentValues = new ContentValues();
    private long newRowId;

    public ImportNewEventHelper(Context context) {
        this.createEbentDatabasHelper = new CreateEventDatabaseHelper(context);
        this.db = this.createEbentDatabasHelper.getWritableDatabase();
    }

    @Override
    public ContentValues getContentValues() {
        return contentValues;
    }

    @Override
    public void InsertValues() {
        this.newRowId = db.insert(ProgrammSQLContract.EventsTable.TABLE_NAME, null, contentValues);
    }
}
