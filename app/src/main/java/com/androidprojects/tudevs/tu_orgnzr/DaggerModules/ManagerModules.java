package com.androidprojects.tudevs.tu_orgnzr.DaggerModules;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
@Module
public class ManagerModules {

    private Context context;

    public ManagerModules(Context context) {
        this.context = context;
    }

    @Provides
    @Named("Location")
    LocationManager provideLocationManager() {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
}
