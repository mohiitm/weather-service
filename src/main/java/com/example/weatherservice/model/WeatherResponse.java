package com.example.weatherservice.model;

import java.util.List;

public class WeatherResponse {
    private List<WeatherData> weatherDataList;

    // Getters and Setters
    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public void setWeatherDataList(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }
}
