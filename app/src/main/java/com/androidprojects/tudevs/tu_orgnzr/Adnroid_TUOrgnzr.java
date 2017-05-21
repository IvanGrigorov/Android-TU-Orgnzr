package com.androidprojects.tudevs.tu_orgnzr;

import android.app.Application;

import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.CriteriaModule;
import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.HelpersModule;
import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.ListenerModule;
import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.ManagerModules;
import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.PresenterModule;
import com.androidprojects.tudevs.tu_orgnzr.DaggerModules.WeatherModelsModules;
import com.androidprojects.tudevs.tu_orgnzr.Presenters.ProfileActivityPresenter;

import dagger.Component;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */

public class Adnroid_TUOrgnzr extends Application {

    private static Adnroid_TUOrgnzr appContext;
    private ApplicationComponent component;

    public static Adnroid_TUOrgnzr getContext() {
        return appContext;
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        this.component = DaggerAdnroid_TUOrgnzr_ApplicationComponent.builder()
                .helpersModule(new HelpersModule(this))
                .managerModules(new ManagerModules(this))
                //.listenerModule(new ListenerModule(this))
                .build();
    }

    @Component(modules = {HelpersModule.class, WeatherModelsModules.class,
            ListenerModule.class, ManagerModules.class,
            CriteriaModule.class, PresenterModule.class})
    public interface ApplicationComponent {
        void inject(Profile_Activity dependencyCaller);

        void inject(ProfileActivityPresenter dependencyCaller);

    }


}
