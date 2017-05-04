package com.androidprojects.tudevs.tu_orgnzr;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
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
import android.support.v7.widget.Toolbar;
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
import com.androidprojects.tudevs.tu_orgnzr.Models.WeatherModel;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadEventTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadProgrammTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.Settings.Requirements;
import com.androidprojects.tudevs.tu_orgnzr.WeatherAnalyzer.TreeConstructor;
import com.androidprojects.tudevs.tu_orgnzr.databinding.ActivityProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Profile_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int LOCATION_PERMISSION = 1;
    private static Context currentContext;
    Criteria criteria;
    private ReadEventTableHelper readEventTableHelper;
    private double longitude;
    private double latitude;
    private String provider;
    private LocationManager locationManager;
    // Create Listener to listen and update location
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            registerLocationListeners();
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    // Make this method in new class
    public static String loadJSONFromFile(String fileName) throws IOException {
        String JSONString = null;

        InputStream inputStream = currentContext.getAssets().open(fileName);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        JSONString = new String(buffer, "UTF-8");
        return JSONString;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        ActivityProfileBinding activtyBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_);

        currentContext = this;
        // Creating Criteria how to use the location Manager
        String svc = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(svc);
        this.criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
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
        this.locationManager.requestLocationUpdates(this.provider, 200, 10, locationListener);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        final String value = getSharedPreferences(Config.USER_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(Config.key, Config.defaultValue);

        TextView profile_Name_TextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.Profile_Name);
        profile_Name_TextView.setText(value);

        navigationView.setNavigationItemSelectedListener(this);

        TextView greeting_Message = (TextView) findViewById(R.id.Greeting);
        greeting_Message.setText(String.format(getString(R.string.GreetMessage), value));

        // TODO: Add weather details

        String currentDate = new SimpleDateFormat("dd:MM:yyyy").format(Calendar.getInstance().getTime());
        String currentDay = new SimpleDateFormat("EEEE").format(Calendar.getInstance().getTime());

        TextView date_View = (TextView) findViewById(R.id.Date);
        date_View.setText(currentDate);

        TextView day_of_week_View = (TextView) findViewById(R.id.Day_Of_Week);
        day_of_week_View.setText(currentDay);


        // Adding latest event

        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.readEventTableHelper = new ReadEventTableHelper(this);
        Cursor allEvents = this.readEventTableHelper.readLatestActivity();

        if (allEvents != null) {
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

        // TODO: Add weather info
        JSONObject weatherInfo = null;
        try {
            weatherInfo = new JSONObject(loadJSONFromFile("Weather.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //weatherInfo
        try {
            double humidity = weatherInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").getDouble("humidity");
            double rainProbability = weatherInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").getDouble("rain");
            double temperature = weatherInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getDouble("temp");
            double wind = weatherInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("wind").getDouble("speed");
            WeatherModel weatherModel = new WeatherModel(rainProbability, humidity, temperature, wind);
            weatherModel.setHumidValue(humidity);
            weatherModel.setWindSpeedValue(wind);
            weatherModel.setTempValue(temperature);
            weatherModel.setRainProbability(rainProbability);
            activtyBinding.appBar.profile.setWeatherModel(weatherModel);
            String[] valuesForTree = new String[] {weatherModel.getRainClassified(), weatherModel.getTempClassified(),
                    weatherModel.getHumidClassified(), weatherModel.getWindClassified() };
            TreeConstructor treeConstructor = new TreeConstructor();
            treeConstructor.setRootNode();
            treeConstructor.fillTreeFromJSON(treeConstructor.root);
            String outcome =  treeConstructor.generateOutcome(treeConstructor.root, valuesForTree, 0);
            String activityMessage = "";
            if (outcome == "Yes") {
                activityMessage = "Enjoy the day";
            }
            else {
                activityMessage = "Stay home for comfort";
            }
            activtyBinding.appBar.profile.suggestion.setText(activityMessage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // TODO: ADD info about the future activity
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
                desitinationCoordinates = getCoordinatesForNextLecture(Calendar.getInstance());
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
        unregisterLocationListeners();
        super.onPause();

    }

    private void unregisterLocationListeners() {

        // Ask for lacation permission if it is not granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);

        }


        locationManager.removeUpdates(this.locationListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerLocationListeners();
    }

    private void registerLocationListeners() {

        unregisterLocationListeners();

        String bestAvailableProvider = locationManager.getBestProvider(this.criteria, true);
        String bestProvider = locationManager.getBestProvider(this.criteria, false);

        if (bestAvailableProvider == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setTitle("Location detection Problem")
                    .setMessage("There is a problem with the locationDetection. Please restart the application")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alert.show();
        } else if (bestAvailableProvider.equals(bestProvider)) {

            // Ask for lacation permission if it is not granted
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);

            }

            locationManager.requestLocationUpdates(bestProvider, 200, 10, this.locationListener);
        } else {
            // Ask for lacation permission if it is not granted
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION);

            }

            locationManager.requestLocationUpdates(bestAvailableProvider, 200, 10, this.locationListener);

        }
    }

    private double[] getCoordinatesForNextLecture(Calendar calendar) throws JSONException, IOException {
        String currentDay = new SimpleDateFormat("EEEE").format(Calendar.getInstance().getTime());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinutes = calendar.get(Calendar.MINUTE);
        int targetMinutes = -1;

        if (currentMinutes < 30 && currentHour < 13 && currentHour > 7) {
            targetMinutes = 30;
        } else if (currentMinutes >= 30 && currentHour < 13 && currentHour > 7) {
            currentHour++;
            if (currentHour < 13) {
                targetMinutes = 30;
            } else {
                targetMinutes = 45;
            }
        } else if (currentMinutes < 45 && currentHour >= 13) {
            targetMinutes = 45;
        } else {
            targetMinutes = 45;
            currentHour++;
        }
        if (((currentHour < 7) || ((currentHour == 7) && (targetMinutes < 30)))
                || ((currentHour > 18) || ((currentHour == 18) && (targetMinutes > 45)))) {

            //TODO: Get the time for the first lecture in the next day
            currentHour = 7;
            targetMinutes = 30;
        }

        String targetSqlSelectValue = Integer.toString(currentHour) + ":" + Integer.toString(targetMinutes);
        ReadProgrammTableHelper readProgrammTableHelper = new ReadProgrammTableHelper(this, targetSqlSelectValue);

        Cursor readTable = readProgrammTableHelper.readTheNextComingLectureInfo();
        readTable.moveToFirst();
        String buildingForTheNextLecture = readTable.getString(readTable.getColumnIndex(ProgrammSQLContract.SubjectTable.BUILDING_COLUMN)).replace(" ", "");
        JSONObject coordinatesJSON = new JSONObject(loadJSONFromFile("Coordinates.json"));
        double latitude = coordinatesJSON.getJSONObject("Coordinates").getJSONObject(buildingForTheNextLecture).getDouble("latitude");
        double longitude = coordinatesJSON.getJSONObject("Coordinates").getJSONObject(buildingForTheNextLecture).getDouble("longitude");
        readTable.close();
        return new double[]{latitude, longitude};

    }

}
