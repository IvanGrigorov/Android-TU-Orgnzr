package com.androidprojects.tudevs.tu_orgnzr.SQLHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.Interfaces.IInsertValuesToDatabase;

/**
 * Created by Ivan Grigorov on 23/04/2016.
 * SQL Query builder to import new subject in database
 */
public class ImportNewSubjectHelper implements IInsertValuesToDatabase {

    ContentValues contentValues = new ContentValues();
    private CreateDatabaseHelper createDatabasHelper;
    private SQLiteDatabase db;
    private long newRowId;

    public ImportNewSubjectHelper(Context context) {
        this.createDatabasHelper = new CreateDatabaseHelper(context);
        this.db = this.createDatabasHelper.getWritableDatabase();
    }

    public ContentValues getContentValues() {
        return contentValues;
    }

    public void InsertValues() {
        this.newRowId = db.insert(ProgrammSQLContract.SubjectTable.TABLE_NAME, null, this.contentValues);
    }
}
