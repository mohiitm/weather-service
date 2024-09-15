package com.example.weatherservice.controller;

import com.example.weatherservice.model.WeatherResponse;
import com.example.weatherservice.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/weather")
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@Operation(summary = "Get 3-day weather forecast for a city", description = "Fetches high and low temperatures for the next 3 days and generates alerts based on weather conditions like rain, wind, and thunderstorms.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Weather data successfully retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid city name provided"),
			@ApiResponse(responseCode = "500", description = "Internal server error while fetching weather data") })
	@GetMapping
	public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
		try {
			WeatherResponse weatherResponse = weatherService.getWeather(city);
			return ResponseEntity.ok(weatherResponse);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null); // Return 400 Bad Request for invalid city
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null); // Return 500 Internal Server Error for other issues
		}
	}
}
