package com.androidprojects.tudevs.tu_orgnzr.Presenters;

import android.app.Activity;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.androidprojects.tudevs.tu_orgnzr.Contracts.ProgrammSQLContract;
import com.androidprojects.tudevs.tu_orgnzr.Models.WeatherModels.WeatherModel;
import com.androidprojects.tudevs.tu_orgnzr.Profile_Activity;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadEventTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.SQLHelpers.ReadProgrammTableHelper;
import com.androidprojects.tudevs.tu_orgnzr.Settings.CustomLocationListener;
import com.androidprojects.tudevs.tu_orgnzr.Settings.DataFromSourceReader;
import com.androidprojects.tudevs.tu_orgnzr.WeatherAnalyzer.TreeConstructor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class ProfileActivityPresenter implements IPresenter {

    private ReadProgrammTableHelper readProgrammTableHelper;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private WeatherModel weatherModel;
    private TreeConstructor treeConstructor;
    private Activity contextBinded;
    private ReadEventTableHelper readEventTableHelper;


    @Inject
    public ProfileActivityPresenter(ReadEventTableHelper readEventTableHelper, ReadProgrammTableHelper readProgrammTableHelper, LocationListener locationListener
            , LocationManager locationManager, WeatherModel weatherModel, TreeConstructor treeConstructor) {
        this.locationListener = locationListener;
        this.locationManager = locationManager;
        this.readProgrammTableHelper = readProgrammTableHelper;
        this.weatherModel = weatherModel;
        this.treeConstructor = treeConstructor;
        this.readEventTableHelper = readEventTableHelper;
    }

    public LocationListener getLocationListener() {
        return this.locationListener;
    }

    public Activity getContextBinded() {
        return this.contextBinded;
    }

    public void setContextBinded(Activity contextBinded) {
        this.contextBinded = contextBinded;
    }

    public double[] getCoordinatesForNextLecture(Calendar calendar) throws JSONException, IOException {
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
        //here
        String targetSqlSelectValue = Integer.toString(currentHour) + ":" + Integer.toString(targetMinutes);
        //ReadProgrammTableHelper readProgrammTableHelper = new ReadProgrammTableHelper(this);

        Cursor readTable = readProgrammTableHelper.readTheNextComingLectureInfo(targetSqlSelectValue);
        readTable.moveToFirst();
        String buildingForTheNextLecture = readTable.getString(readTable.getColumnIndex(ProgrammSQLContract.SubjectTable.BUILDING_COLUMN)).replace(" ", "");
        JSONObject coordinatesJSON = new JSONObject(DataFromSourceReader.loadJSONFromFile("Coordinates.json", this.contextBinded));
        double latitude = coordinatesJSON.getJSONObject("Coordinates").getJSONObject(buildingForTheNextLecture).getDouble("latitude");
        double longitude = coordinatesJSON.getJSONObject("Coordinates").getJSONObject(buildingForTheNextLecture).getDouble("longitude");
        readTable.close();
        return new double[]{latitude, longitude};

    }

    @Override
    public void attach(Activity activity) {
        this.setContextBinded(activity);
        ((CustomLocationListener) this.locationListener).setContext(activity);
    }

    @Override
    public void detach() {
        this.contextBinded = null;
    }

    public void updateWeatherInfo() {

        if (this.contextBinded == null) {
            Log.d("ALERT: ", "Context for Profile presenter is not set.");
            throw new IllegalStateException("contextBinded is not set.");
        }

        JSONObject weatherInfo = null;

        try {
            weatherInfo = new JSONObject(DataFromSourceReader
                    .loadJSONFromFile("Weather.json", this.contextBinded));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            weatherModel.fillModelWithJSONData(weatherInfo);
            ((Profile_Activity) this.contextBinded).setWeatherModel(weatherModel);
            ((Profile_Activity) this.contextBinded).designGraph(weatherModel);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateTreeConstructor() {
        this.treeConstructor.setRootNode();
        treeConstructor.fillTreeFromJSON(treeConstructor.root);
    }

    public void provideWeatherSuggestion() {
        updateTreeConstructor();
        String[] valuesForTree = new String[]{weatherModel.getRainClassified(), weatherModel.getTempClassified(),
                weatherModel.getHumidClassified(), weatherModel.getWindClassified()};
        String outcome = treeConstructor.generateOutcome(treeConstructor.root, valuesForTree, 0);
        String activityMessage = "";
        if (outcome == "Yes") {
            activityMessage = "Enjoy the day";
        } else {
            activityMessage = "Stay home for comfort";
        }
        ((Profile_Activity) this.contextBinded).setWeatherSuggestion(activityMessage);
    }

    public void readLatestActivity() {
        Cursor allEvents = this.readEventTableHelper.readLatestActivity();
        ((Profile_Activity) this.contextBinded).setInfoAboutLatestActivity(allEvents);
    }

    public void getCurrentDateAndDay() {
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date());
        String currentDay = DateFormat.getDateInstance(DateFormat.DAY_OF_WEEK_FIELD).format(new Date());
        ((Profile_Activity) this.contextBinded).setDateAndDayInformation(currentDate, currentDay);
    }
}