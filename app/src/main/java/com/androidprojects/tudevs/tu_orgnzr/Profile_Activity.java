package com.androidprojects.tudevs.tu_orgnzr;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.tudevs.tu_orgnzr.Config.Config;
import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.Models.WeatherModels.WeatherModel;
import com.androidprojects.tudevs.tu_orgnzr.Presenters.ProfileActivityPresenter;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadEventTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadProgrammTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.Settings.CustomLocationListener;
import com.androidprojects.tudevs.tu_orgnzr.Settings.GraphDesigner;
import com.androidprojects.tudevs.tu_orgnzr.Settings.Requirements;
import com.androidprojects.tudevs.tu_orgnzr.databinding.ActivityProfileBinding;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.zip.DataFormatException;

import javax.inject.Inject;
import javax.inject.Named;

public class Profile_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOCATION_PERMISSION = 1;

    // Injecting Dependencies

    @Inject
    public ReadEventTableHelper readEventTableHelper;
    @Inject
    public ReadProgrammTableHelper readProgrammTableHelper;
    @Inject
    public WeatherModel weatherModel;
    @Inject
    @Named("Location")
    public LocationManager locationManager;
    @Inject
    Criteria criteria;
    // Create Listener to listen and update location
    @Inject
    ProfileActivityPresenter profileActivityPresenter;
    ActivityProfileBinding activtyBinding;
    //private ReadEventTableHelper readEventTableHelper;
    private double longitude;
    private double latitude;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        this.activtyBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_);
        injectComponents();
        profileActivityPresenter.attach(this);
        //this.injectDependencies();
        // Creating Criteria how to use the location Manager

        if (this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && Requirements.hasInternetConnectivity(this)) {
            this.provider = (this.locationManager.getProvider(LocationManager.NETWORK_PROVIDER)).getName();
        } else {
            this.provider = locationManager.getBestProvider(criteria, true);

        }

        // Asks for permission to use location properties if it is not granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);

        }
        //MainActivityBinding thisActivity = MainActivityBinding.inflate();
        this.locationManager.requestLocationUpdates(this.provider, 200, 10, profileActivityPresenter.getLocationListener());


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activtyBinding.appBar.toolbar);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, activtyBinding.drawerLayout, activtyBinding.appBar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (activtyBinding.drawerLayout != null) {
            activtyBinding.drawerLayout.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        final String value = getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Config.key, Config.defaultValue);

        TextView profile_Name_TextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.Profile_Name);
        profile_Name_TextView.setText(value);

        navigationView.setNavigationItemSelectedListener(this);

        //TextView greeting_Message = (TextView) findViewById(R.id.Greeting);
        activtyBinding.appBar.profile.Greeting.setText(String.format(getString(R.string.GreetMessage), value));

        // TODO: Add weather details

        //String currentDate = new SimpleDateFormat("dd:MM:yyyy").format(Calendar.getInstance().getTime());
        //String currentDay = new SimpleDateFormat("EEEE").format(Calendar.getInstance().getTime());

        //TextView date_View = (TextView) findViewById(R.id.Date);
        //activtyBinding.appBar.profile.Date.setText(currentDate);

        //TextView day_of_week_View = (TextView) findViewById(R.id.Day_Of_Week);
        //activtyBinding.appBar.profile.DayOfWeek.setText(currentDay);

        this.profileActivityPresenter.updateWeatherInfo();
        this.profileActivityPresenter.provideWeatherSuggestion();
        this.profileActivityPresenter.readLatestActivity();
        this.profileActivityPresenter.getCurrentDateAndDay();

        // TODO: ADD info about the future activity
    }

    public void injectComponents() {
        ((Adnroid_TUOrgnzr) this.getApplication()).getComponent().inject(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.profile_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_program) {

            Intent intent = new Intent(getApplicationContext(), Programm_Set_Activity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_events) {
            Intent intent = new Intent(getApplicationContext(), Display_Notes_Activity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_map) {
            double[] desitinationCoordinates = null;
            try {
                desitinationCoordinates = this.profileActivityPresenter.getCoordinatesForNextLecture(Calendar.getInstance());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // TODO:: Check if GPS is enabled

            if ((!this.locationManager.isProviderEnabled(this.provider)) &&
                    (!this.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Enable GPS")
                        .setMessage("Please enable your GPS !")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                //AlertDialog alertDialog = dialog.create();
                dialog.show();

            } else if (Requirements.isGoogleMapsInstalled(this)) {
                // Ask for lacation permission if it is not granted
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);

                }

                if (this.provider.equals(LocationManager.GPS_PROVIDER)) {
                    if (this.locationManager.getLastKnownLocation(this.provider) == null) {
                        Toast showNoLocationToast = new Toast(this);
                        String lineSeparator = System.getProperty("line.separator");
                        showNoLocationToast.setText("You are using GPS location provider which requires some to calculate your location. " + lineSeparator + " Please try again later.");
                        return false;
                    }
                }
                this.longitude = this.locationManager.getLastKnownLocation(this.provider).getLongitude();
                this.latitude = this.locationManager.getLastKnownLocation(this.provider).getLatitude();
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", this.latitude, this.longitude, desitinationCoordinates[0], desitinationCoordinates[1]);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Install Google Maps")
                        .setMessage("Please install Google Maps to proceed")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                dialog.show();
            }
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.log_out) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] requestingPermissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION) {
            if ((grantResults.length) > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Log.d("String", Double.toString(this.longitude));
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f", this.latitude, this.longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        }
    }

    @Override
    public void onPause() {
        ((CustomLocationListener) this.profileActivityPresenter.getLocationListener()).unregisterLocationListeners();
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((CustomLocationListener) this.profileActivityPresenter.getLocationListener()).registerLocationListeners(criteria);
    }

    public void setWeatherModel(WeatherModel weatherModel) {
        this.activtyBinding.appBar.profile.setWeatherModel(weatherModel);
    }

    public void designGraph(WeatherModel weatherModel) {
        try {
            GraphDesigner.designGraph(activtyBinding.appBar.profile.weatherGraph,
                    weatherModel.provideWeatherWeekInfoForGraph(new DataPoint[10], new DataPoint[10]),
                    weatherModel.getDayNamesForWeeks());
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }

    public void setWeatherSuggestion(String suggestion) {
        this.activtyBinding.appBar.profile.suggestion.setText(suggestion);
    }

    // TODO: Make it with data - view binding
    public void setInfoAboutLatestActivity(Cursor allEvents) {
        if ((allEvents != null) && (allEvents.getCount() > 0)) {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            try {

                while (allEvents.moveToNext()) {

                    View v = vi.inflate(R.layout.note_inflation_template, null);

                    // Fill in any details dynamically here
                    TextView note_title = (TextView) v.findViewById(R.id.Note_Title);
                    note_title.setText(allEvents.getString(allEvents.getColumnIndex(ProgrammSQLContract.EventsTable.EVENTS_NAME_COLUMN)));

                    TextView note_deascription = (TextView) v.findViewById(R.id.Note_Description);
                    note_deascription.setText(allEvents.getString(allEvents.getColumnIndex(ProgrammSQLContract.EventsTable.EVENT_DESCRIPTION)));

                    TextView note_date = (TextView) v.findViewById(R.id.Note_Date);
                    note_date.setText(allEvents.getString(allEvents.getColumnIndex(ProgrammSQLContract.EventsTable.EVENT_DATE)));

                    // Insert into main view
                    ViewGroup insertPoint = (ViewGroup) findViewById(R.id.Latest_Event_View);
                    insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                }

            } finally {

                allEvents.close();
            }
        }
        // Make Toast for info if there are no stored activity
        else {
            Toast.makeText(this, "There are no upcomming events stored", Toast.LENGTH_LONG).show();
        }
    }

    public void setDateAndDayInformation(String date, String day) {
        this.activtyBinding.appBar.profile.Date.setText(date);
        this.activtyBinding.appBar.profile.DayOfWeek.setText(day);

    }
}
