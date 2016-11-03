package com.androidprojects.tudevs.tu_orgnzr.Interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.CreateDatabaseHelper;

/**
 * Created by Ivan Grigorov on 30/04/2016.
 */
public interface IInsertValuesToDatabase {

    ContentValues contentValues = new ContentValues();

    public ContentValues getContentValues();

    public void InsertValues();
}
