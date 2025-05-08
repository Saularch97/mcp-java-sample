package com.br.mcp.openmeteomcp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WeatherResponse(
        @JsonProperty("latitude") double latitude,
        @JsonProperty("longitude") double longitude,
        @JsonProperty("generationtime_ms") double generationTimeMs,
        @JsonProperty("utc_offset_seconds") int utcOffsetSeconds,
        @JsonProperty("timezone") String timezone,
        @JsonProperty("timezone_abbreviation") String timezoneAbbreviation,
        @JsonProperty("elevation") double elevation,
        @JsonProperty("current_weather_units") CurrentWeatherUnits currentWeatherUnits,
        @JsonProperty("current_weather") CurrentWeather currentWeather
) {
    public record CurrentWeatherUnits(
            @JsonProperty("time") String time,
            @JsonProperty("interval") String interval,
            @JsonProperty("temperature") String temperature,
            @JsonProperty("windspeed") String windspeed,
            @JsonProperty("winddirection") String winddirection,
            @JsonProperty("is_day") String isDay,
            @JsonProperty("weathercode") String weatherCode
    ) {}

    public record CurrentWeather(
            @JsonProperty("time") String time,
            @JsonProperty("interval") int interval,
            @JsonProperty("temperature") double temperature,
            @JsonProperty("windspeed") double windspeed,
            @JsonProperty("winddirection") int winddirection,
            @JsonProperty("is_day") int isDay,
            @JsonProperty("weathercode") int weatherCode
    ) {}
}
