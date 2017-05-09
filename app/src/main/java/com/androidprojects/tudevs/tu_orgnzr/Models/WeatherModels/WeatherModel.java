package com.androidprojects.tudevs.tu_orgnzr.Models.WeatherModels;

import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.zip.DataFormatException;

import javax.inject.Inject;

/**
 * Created by Ivan Grigorov on 30/04/2017.
 * Contact at ivangrigorov9 at gmail.com
 */
public class WeatherModel {

    private String humidClassified;
    private double humidValue;
    private String rainClassified;
    private String rainType;
    private double rainProbability;
    private String tempClassified;
    private double tempValue;
    private String windClassified;
    private double windSpeedValue;
    private double[] maxTempForWeek;
    private double[] minTempForWeek;
    private String[] dayNamesForWeeks;

    //public WeatherModel(double rainProbabilty, double humidValue, double tempValue, double windSpeedValue) {
    //   this.rainProbability = rainProbabilty;
    //   this.humidValue = humidValue;
    //   this.tempValue = tempValue;
    //    this.windSpeedValue = windSpeedValue;
    //}
    @Inject
    public WeatherModel() {

    }

    public String[] getDayNamesForWeeks() throws DataFormatException {
        if (this.dayNamesForWeeks == null) {
            throw new DataFormatException("Missing information for the week. You should call the fillModelWithJSON Method first.");
        }
        return this.dayNamesForWeeks;
    }

    public String getHumidClassified() {
        return humidClassified;
    }

    public void setHumidClassified(String humidClassified) {
        this.humidClassified = humidClassified;
    }

    public double getHumidValue() {
        return humidValue;
    }

    public void setHumidValue(double humidValue) {
        this.humidValue = humidValue;
        if (humidValue < 30) {
            this.setHumidClassified("Low");
        }
        else if (humidValue < 70) {
            this.setHumidClassified("Normal");
        }
        else {
            this.setHumidClassified("High");
        }
    }

    public String getRainClassified() {
        return rainClassified;
    }

    public void setRainClassified(String rainClassified) {
        this.rainClassified = rainClassified;
    }

    public String getRainType() {
        return rainType;
    }

    public void setRainType(String rainType) {
        this.rainType = rainType;
    }

    public double getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(double rainProbability) {
        this.rainProbability = rainProbability;
        if (rainProbability < 30) {
            this.setRainClassified("Low");
        }
        else if (rainProbability < 70) {
            this.setRainClassified("Normal");
        }
        else {
            this.setRainClassified("High");
        }
    }

    public String getTempClassified() {
        return tempClassified;
    }

    public void setTempClassified(String tempClassified) {
        this.tempClassified = tempClassified;
    }

    public double getTempValue() {
        return tempValue;
    }

    public void setTempValue(double tempValue) {
        this.tempValue = tempValue;
        if (tempValue < 10) {
            this.setTempClassified("Cool");
            this.rainType = "Snow";
        }
        else if (tempValue < 20) {
            this.setTempClassified("Mild");
            this.rainType = "Rain";
        }
        else {
            this.setTempClassified("Hot");
            this.rainType = "Rain";
        }
    }

    public String getWindClassified() {
        return windClassified;
    }

    public void setWindClassified(String windClassified) {
        this.windClassified = windClassified;
    }

    public double getWindSpeedValue() {
        return windSpeedValue;
    }

    public void setWindSpeedValue(double windSpeedValue) {
        this.windSpeedValue = windSpeedValue;
        if (windSpeedValue < 30) {
            this.setWindClassified("Weak");
        }
        else if (windSpeedValue < 70) {
            this.setWindClassified("Normal");
        }
        else {
            this.setWindClassified("Strong");
        }
    }

    public AbstractList<AbstractMap.SimpleEntry<String, DataPoint[]>> provideWeatherWeekInfoForGraph(DataPoint[] maxTemps, DataPoint[] minTemps) throws DataFormatException {

        if ((this.dayNamesForWeeks == null) || (this.maxTempForWeek == null) || (this.minTempForWeek == null)) {
            throw new DataFormatException("Missing information for the week. You should call the fillModelWithJSON Method first.");
        } else {
            for (int i = 0; i < this.dayNamesForWeeks.length; i++) {
                maxTemps[i] = new DataPoint(i, this.maxTempForWeek[i]);
                minTemps[i] = new DataPoint(i, this.minTempForWeek[i]);
            }
        }
        ArrayList<AbstractMap.SimpleEntry<String, DataPoint[]>> tempValues = new ArrayList<AbstractMap.SimpleEntry<String, DataPoint[]>>();
        tempValues.add(new AbstractMap.SimpleEntry<String, DataPoint[]>("max", maxTemps));
        tempValues.add(new AbstractMap.SimpleEntry<String, DataPoint[]>("min", minTemps));

        return tempValues;
    }

    public void fillModelWithJSONData(JSONObject jsonInfo) throws JSONException {
        this.maxTempForWeek = new double[10];
        this.minTempForWeek = new double[10];
        this.dayNamesForWeeks = new String[10];
        double humidity = jsonInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").getDouble("humidity");
        double rainProbability = jsonInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").getDouble("rain");
        double temperature = jsonInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getDouble("temp");
        double wind = jsonInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("wind").getDouble("speed");
        this.setHumidValue(humidity);
        this.setWindSpeedValue(wind);
        this.setTempValue(temperature);
        this.setRainProbability(rainProbability);
        JSONArray weekForecast = jsonInfo.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast");
        for (int i = 0; i < weekForecast.length(); i++) {
            JSONObject dayForecast = weekForecast.getJSONObject(i);
            this.maxTempForWeek[i] = dayForecast.getDouble("high");
            this.minTempForWeek[i] = dayForecast.getDouble("low");
            this.dayNamesForWeeks[i] = dayForecast.getString("day");
        }
    }
}
