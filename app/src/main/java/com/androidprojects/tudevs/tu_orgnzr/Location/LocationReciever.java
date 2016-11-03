package com.androidprojects.tudevs.tu_orgnzr.Location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Ivan Grigorov on 12/08/2016.
 */
public class LocationReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String key = LocationManager.KEY_LOCATION_CHANGED;
        Location location = (Location) intent.getExtras().get(key);
    }
}
