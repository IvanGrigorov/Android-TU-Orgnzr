package com.androidprojects.tudevs.tu_orgnzr.DaggerModules;

import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;

import com.androidprojects.tudevs.tu_orgnzr.Settings.CustomLocationListener;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
@Module
public class ListenerModule {

    @Provides
    @Named("custom")
    LocationListener provideLocationListener(Criteria criteria, @Named("Location") LocationManager locationManager) {
        return new CustomLocationListener(criteria, locationManager);
    }
}
