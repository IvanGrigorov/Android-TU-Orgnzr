package com.androidprojects.tudevs.tu_orgnzr.Models;

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

    public WeatherModel(double rainProbabilty, double humidValue, double tempValue, double windSpeedValue) {
        this.rainProbability = rainProbabilty;
        this.humidValue = humidValue;
        this.tempValue = tempValue;
        this.windSpeedValue = windSpeedValue;
    }

    public WeatherModel() {

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
}
