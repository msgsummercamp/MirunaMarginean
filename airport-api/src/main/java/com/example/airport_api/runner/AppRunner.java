package com.example.airport_api.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.airport_api.service.AirportAPIService;

@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    private AirportAPIService airportAPIService;

    @Override
    public void run(String... args) {
        airportAPIService.testService();
    }
}
