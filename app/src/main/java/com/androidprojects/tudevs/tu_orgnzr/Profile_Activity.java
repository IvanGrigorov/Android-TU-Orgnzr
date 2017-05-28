package com.androidprojects.tudevs.tu_orgnzr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojects.tudevs.tu_orgnzr.Config.Config;
import com.androidprojects.tudevs.tu_orgnzr.Models.WeatherModels.WeatherModel;
import com.androidprojects.tudevs.tu_orgnzr.Presenters.ProfileActivityPresenter;
import com.androidprojects.tudevs.tu_orgnzr.RoomLibraryDAO.EventsDAO;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Profile_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOCATION_PERMISSION = 1;

    // Injecting Dependencies
    @Inject
    Criteria criteria;
    // Create Listener to listen and update location
    @Inject
    ProfileActivityPresenter profileActivityPresenter;
    ActivityProfileBinding activtyBinding;
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


        if (!checkPermissions()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        } else {
            activateLocationProviderIfEnabled();
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activtyBinding.appBar.toolbar);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, activtyBinding.drawerLayout, activtyBinding.appBar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (activtyBinding.drawerLayout != null) {
            activtyBinding.drawerLayout.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = this.activtyBinding.navView;


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
        getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(eventsDAO -> this.setInfoAboutLatestActivity(eventsDAO));
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
            onPause();
        } else if (id == R.id.nav_events) {
            Intent intent = new Intent(getApplicationContext(), Display_Notes_Activity.class);
            startActivity(intent);
            onPause();

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_map) {
            // TODO:: Check if GPS is enabled

            if (!checkPermissions() || this.provider == null) {
                Toast.makeText(this, "Activate Location permissions from app settings menu", Toast.LENGTH_LONG).show();
                return false;
            }
            if (!Requirements.hasInternetConnectivity(this)) {
                Toast.makeText(this, "We need internet connectivity for this feature.", Toast.LENGTH_LONG).show();
                return false;
            }
            if ((!this.profileActivityPresenter.getLocationManager().isProviderEnabled(this.provider)) &&
                    (!this.profileActivityPresenter.getLocationManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER))) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Enable GPS")
                        .setMessage("Please enable your GPS !")
                        .setPositiveButton("OK", (dialog1, which) -> {
                            return;
                        });
                //AlertDialog alertDialog = dialog.create();
                dialog.show();

            } else if (Requirements.isGoogleMapsInstalled(this)) {
                // TODO: Consider calling
                double longitude = ((CustomLocationListener) this.profileActivityPresenter.getLocationListener()).getLongitude();
                double latitude = ((CustomLocationListener) this.profileActivityPresenter.getLocationListener()).getLatitude();

                try {
                    this.profileActivityPresenter.
                            getProgrammForDay(this.profileActivityPresenter.getDateTimeForNextLecture(Calendar.getInstance()))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(coordinates -> {
                                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", latitude, longitude, coordinates[0], coordinates[1]);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                startActivity(intent);
                                onPause();
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }


                //}
            } else {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("Install Google Maps")
                        .setMessage("Please install Google Maps to proceed")
                        .setPositiveButton("OK", (dialog12, which) -> {
                            return;
                        });
                dialog.show();
            }
        } else if (id == R.id.nav_profile)

        {

        } else if (id == R.id.nav_settings)

        {

        } else if (id == R.id.log_out)

        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] requestingPermissions, int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION) {
            if ((grantResults.length) > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    this.profileActivityPresenter.getLocationManager().requestLocationUpdates(this.provider, 200, 10, profileActivityPresenter.getLocationListener());

                }
            } else {
                Toast.makeText(this, "For using map functionality you should enable location permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPause() {
        if (checkPermissions()) {
            ((CustomLocationListener) this.profileActivityPresenter.getLocationListener()).unregisterLocationListeners();
        }
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            ((CustomLocationListener) this.profileActivityPresenter.getLocationListener()).registerLocationListeners(criteria);
            activateLocationProviderIfEnabled();
        }
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

    public void setInfoAboutLatestActivity(EventsDAO eventsDAO) {
        if (eventsDAO == null) {
            Toast.makeText(this, "There are no upcomming events stored", Toast.LENGTH_LONG).show();

        } else {
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View v = vi.inflate(R.layout.note_inflation_template, null);

            // Fill in any details dynamically here
            TextView note_title = (TextView) v.findViewById(R.id.Note_Title);
            note_title.setText(eventsDAO.getEventName());

            TextView note_deascription = (TextView) v.findViewById(R.id.Note_Description);
            note_deascription.setText(eventsDAO.getEventDescription());

            TextView note_date = (TextView) v.findViewById(R.id.Note_Date);
            note_date.setText(eventsDAO.getEventDate());

            // Insert into main view
            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.Latest_Event_View);
            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

    }

    public void setDateAndDayInformation(String date, String day) {
        this.activtyBinding.appBar.profile.Date.setText(date);
        this.activtyBinding.appBar.profile.DayOfWeek.setText(day);

    }

    private Observable<EventsDAO> getObservable() {
        return Observable.create(e -> {
            EventsDAO eventsDAO = this.profileActivityPresenter.readLatestActivity();
            if (eventsDAO == null) {
                e.onComplete();
            } else {
                e.onNext(eventsDAO);
            }
        });
    }

    private boolean checkPermissions() {
        return !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED);

    }

    private void activateLocationProviderIfEnabled() {
        if (this.profileActivityPresenter.getLocationManager().isProviderEnabled(LocationManager.NETWORK_PROVIDER) && Requirements.hasInternetConnectivity(this)) {
            this.provider = (this.profileActivityPresenter.getLocationManager().getProvider(LocationManager.NETWORK_PROVIDER)).getName();
        } else {
            this.provider = this.profileActivityPresenter.getLocationManager().getBestProvider(criteria, false);
        }

    }

}
