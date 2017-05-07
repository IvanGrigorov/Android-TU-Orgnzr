package com.androidprojects.tudevs.tu_orgnzr.Interfaces;

import android.content.ContentValues;

/**
 * Created by Ivan Grigorov on 30/04/2016.
 */
public interface IInsertValuesToDatabase {

    ContentValues contentValues = new ContentValues();

    ContentValues getContentValues();

    void InsertValues();
}
