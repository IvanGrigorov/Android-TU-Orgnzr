package com.androidprojects.tudevs.tu_orgnzr.DaggerModules;

import android.content.Context;

import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadEventTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadProgrammTableHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
@Module
public class HelpersModule {

    private Context context;


    public HelpersModule(Context context) {
        this.context = context;
    }

    @Provides
    ReadEventTableHelper providesEventHelper() {
        return new ReadEventTableHelper(this.context);
    }

    @Provides
    ReadProgrammTableHelper provideProgrammHelper() {
        return new ReadProgrammTableHelper(this.context);
    }
}
