package com.androidprojects.tudevs.tu_orgnzr.DaggerModules;

import android.location.LocationListener;
import android.location.LocationManager;

import com.androidprojects.tudevs.tu_orgnzr.Models.WeatherModels.WeatherModel;
import com.androidprojects.tudevs.tu_orgnzr.Presenters.ProfileActivityPresenter;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadEventTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadProgrammTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.WeatherAnalyzer.TreeConstructor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
@Module
public class PresenterModule {


    @Provides
    ProfileActivityPresenter providesProfileActivityPresenter(ReadEventTableHelper readEventTableHelper,
                                                              ReadProgrammTableHelper readProgrammTableHelper,
                                                              @Named("custom") LocationListener locationListener,
                                                              @Named("Location") LocationManager locationManager,
                                                              WeatherModel weatherModel,
                                                              TreeConstructor treeConstructor) {
        return new ProfileActivityPresenter(readEventTableHelper, readProgrammTableHelper, locationListener, locationManager, weatherModel, treeConstructor);
    }
}
