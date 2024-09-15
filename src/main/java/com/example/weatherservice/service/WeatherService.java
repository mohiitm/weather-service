package com.example.weatherservice.service;

import com.example.weatherservice.model.WeatherData;
import com.example.weatherservice.model.WeatherResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private static final String API_KEY = "d2929e9483efc82c82c32ee7e02d563e";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&cnt=24";

    public WeatherResponse getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(API_URL, city, API_KEY);
        String apiResponse = restTemplate.getForObject(url, String.class);

        if (apiResponse == null) {
            throw new RuntimeException("Failed to fetch weather data");
        }

        return parseWeatherData(apiResponse);
    }

    private WeatherResponse parseWeatherData(String apiResponse) {
        JSONObject jsonObject = new JSONObject(apiResponse);
        JSONArray list = jsonObject.getJSONArray("list");

        List<WeatherData> weatherDataList = new ArrayList<>();
        // Assuming 8 data points per day (3-hour intervals), get data for next 3 days
        for (int i = 0; i < 3; i++) {
            int index = i * 8; // Get one data point per day (e.g., midday)
            if (index >= list.length()) break;
            JSONObject dayData = list.getJSONObject(index);
            JSONObject main = dayData.getJSONObject("main");
            JSONArray weatherArray = dayData.getJSONArray("weather");
            JSONObject wind = dayData.getJSONObject("wind");

            JSONObject weatherDetails = weatherArray.getJSONObject(0);

            double tempHigh = main.getDouble("temp_max") - 273.15; // Kelvin to Celsius
            double tempLow = main.getDouble("temp_min") - 273.15;  // Kelvin to Celsius
            double windSpeed = wind.getDouble("speed");   // Wind speed in meters per second

            String description = weatherDetails.getString("description");
            boolean rain = description.contains("rain");
            
            boolean thunderstorm = description.contains("thunderstorm");

            WeatherData weatherData = new WeatherData();
            weatherData.setDate(dayData.getString("dt_txt"));
            weatherData.setHigh(tempHigh);
            weatherData.setLow(tempLow);
            weatherData.setAlerts(getAlerts(tempHigh, windSpeed, rain, thunderstorm));

            weatherDataList.add(weatherData);
        }

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setWeatherDataList(weatherDataList);
        return weatherResponse;
    }


    private List<String> getAlerts(double tempHigh, double windSpeed, boolean rain, boolean thunderstorm) {
        List<String> alerts = new ArrayList<>();
        
        // Check if rain is predicted
        if (rain) {
            alerts.add("Carry umbrella");
        }
        
        // Check if temperature exceeds 40°C
        if (tempHigh > 40) {
            alerts.add("Use sunscreen lotion");
        }
        
        // Check if wind speed exceeds 10 mph (10 mph = 4.47 m/s)
        if (windSpeed > 4.47) {
            alerts.add("It's too windy, watch out!");
        }

        // Check if thunderstorms are predicted
        if (thunderstorm) {
            alerts.add("Don’t step out! A storm is brewing!");
        }

        return alerts;
    }

}
