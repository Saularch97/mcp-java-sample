package com.br.mcp.openmeteomcp;

import com.br.mcp.openmeteomcp.model.WeatherResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class OpenMeteoService {

    private static final String BASE_URL = "https://api.open-meteo.com";

    private final RestClient restClient;

    public OpenMeteoService() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();
    }

    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    public String getWeatherForecastByLocation(double latitude, double longitude) {

        // talvez precise tirar scheme host e deixar path pois ja tem na baseurl

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.open-meteo.com")
                .path("/v1/forecast")
                .queryParam("latitude", -23.55)
                .queryParam("longitude", -46.63)
                .queryParam("current_weather", true)
                .build()
                .toUri();

        System.out.println("Making call to " + uri);

        // TODO openFeign would be an alternative to restClient?
        var forecast = restClient.get()
                .uri(uri)
                .retrieve()
                .body(WeatherResponse.class);

        assert forecast != null;

        return String.format("""
                Latitude: %.2f
                Longitude: %.2f
                Generation Time (ms): %.2f
                UTC Offset (s): %d
                Timezone: %s
                Timezone Abbreviation: %s
                Elevation: %.2f
            
                --- Current Weather Units ---
                Time Unit: %s
                Interval Unit: %s
                Temperature Unit: %s
                Windspeed Unit: %s
                Winddirection Unit: %s
                Is Day Unit: %s
                Weather Code Unit: %s
            
                --- Current Weather ---
                Time: %s
                Interval: %d
                Temperature: %.2f
                Windspeed: %.2f
                Winddirection: %d
                Is Day: %d
                Weather Code: %d
                """,
                forecast.latitude(),
                forecast.longitude(),
                forecast.generationTimeMs(),
                forecast.utcOffsetSeconds(),
                forecast.timezone(),
                forecast.timezoneAbbreviation(),
                forecast.elevation(),

                forecast.currentWeatherUnits().time(),
                forecast.currentWeatherUnits().interval(),
                forecast.currentWeatherUnits().temperature(),
                forecast.currentWeatherUnits().windspeed(),
                forecast.currentWeatherUnits().winddirection(),
                forecast.currentWeatherUnits().isDay(),
                forecast.currentWeatherUnits().weatherCode(),

                forecast.currentWeather().time(),
                forecast.currentWeather().interval(),
                forecast.currentWeather().temperature(),
                forecast.currentWeather().windspeed(),
                forecast.currentWeather().winddirection(),
                forecast.currentWeather().isDay(),
                forecast.currentWeather().weatherCode()
        );
    }

    public static void main(String[] args) {
        OpenMeteoService client = new OpenMeteoService();
        System.out.println(client.getWeatherForecastByLocation(-23.5, -46.5));
    }
}
