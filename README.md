# Weather Service Microservice

## Overview

This microservice fetches weather forecast data for the next 3 days based on a city input and provides alerts for the following conditions:
- **Carry umbrella**: If rain is predicted.
- **Use sunscreen lotion**: If the temperature goes above 40°C.
- **It’s too windy, watch out!**: If wind speeds exceed 10 mph.
- **Don’t step out! A storm is brewing!**: If thunderstorms are predicted.

The weather data is retrieved from the [OpenWeatherMap API](https://openweathermap.org/) and processed to provide high and low temperatures, along with relevant alerts for each day.

### Features
- Get high and low temperatures for the next 3 days.
- Trigger specific alerts based on weather conditions.
- Modular and scalable design for adding more weather conditions in the future.

### API Endpoint

- **GET** `/weather`
  - **Parameters**: 
    - `city`: Name of the city for which the weather forecast should be fetched.
  - **Response**:
    ```json
    {
      "weatherDataList": [
        {
          "date": "2024-09-16 12:00:00",
          "high": 29.5,
          "low": 20.3,
          "alerts": ["Carry umbrella"]
        },
        ...
      ]
    }
    ```

### Prerequisites
- Java 17
- Maven
- OpenWeatherMap API Key

### Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/weather-service.git
   cd weather-service