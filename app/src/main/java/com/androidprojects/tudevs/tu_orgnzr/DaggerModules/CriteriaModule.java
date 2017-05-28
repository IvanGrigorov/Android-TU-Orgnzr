package com.androidprojects.tudevs.tu_orgnzr.DaggerModules;

import android.location.Criteria;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan Grigorov on 08/05/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
@Module
public class CriteriaModule {


    @Provides
    Criteria providesAppContext() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        return criteria;
    }

}
