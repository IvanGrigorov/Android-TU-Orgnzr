package com.androidprojects.tudevs.tu_orgnzr.Settings;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class DataFromSourceReader {

    public static String loadJSONFromFile(String fileName, Context context) throws IOException {
        String JSONString = null;

        InputStream inputStream = context.getAssets().open(fileName);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        JSONString = new String(buffer, "UTF-8");
        return JSONString;

    }

}