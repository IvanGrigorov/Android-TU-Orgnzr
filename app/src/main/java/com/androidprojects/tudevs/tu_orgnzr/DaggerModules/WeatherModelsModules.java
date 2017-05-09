package com.androidprojects.tudevs.tu_orgnzr.DaggerModules;

import com.androidprojects.tudevs.tu_orgnzr.Models.WeatherModels.WeatherModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
@Module
public class WeatherModelsModules {


    @Provides
    WeatherModel provideWeatherModel() {
        return new WeatherModel();
    }

}
