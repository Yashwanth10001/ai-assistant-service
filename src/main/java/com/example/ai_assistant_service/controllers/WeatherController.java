package com.example.ai_assistant_service.controllers;

import com.example.ai_assistant_service.services.WeatherClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherClientService weatherClientService;

    public WeatherController(WeatherClientService weatherClientService) {
        this.weatherClientService = weatherClientService;
    }

    @GetMapping("/current")
    public String getCurrent(@RequestParam String city) {
        return weatherClientService.getCurrentWeather(city);
    }

    @GetMapping("/forecast")
    public String getForecast(@RequestParam String city, @RequestParam int days) {
        return weatherClientService.getWeatherForecast(city, days);
    }
}
