package com.androidprojects.tudevs.tu_orgnzr;

import android.app.Application;

import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.HelpersModule;
import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.WeatherModelsModules;

import dagger.Component;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */

public class Adnroid_TUOrgnzr extends Application {

    private ApplicationComponent component;


    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = DaggerAdnroid_TUOrgnzr_ApplicationComponent.builder()
                .helpersModule(new HelpersModule(this))
                .build();
    }

    @Component(modules = {HelpersModule.class, WeatherModelsModules.class})
    public interface ApplicationComponent {
        void inject(Profile_Activity context);
    }


}
