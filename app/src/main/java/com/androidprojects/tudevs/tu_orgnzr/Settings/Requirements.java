package com.androidprojects.tudevs.tu_orgnzr.Settings;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ivan Grigorov on 13/08/2016.
 */
public final class Requirements {

    private static final String mapsPackageName = "com.google.android.apps.maps";

    public static boolean  isGoogleMapsInstalled(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(mapsPackageName, 0);
            return true;
        }
        catch(PackageManager.NameNotFoundException exception) {
            return false;
        }
    }


    //----------------------------------------------------------------------

    public static boolean hasInternetConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isActiveConnected = ((networkInfo != null) && networkInfo.isConnectedOrConnecting());

        return isActiveConnected;
    }
}
