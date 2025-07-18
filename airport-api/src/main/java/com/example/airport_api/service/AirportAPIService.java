package com.example.airport_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.airport_api.model.Airport;

import java.util.ArrayList;
import java.util.List;

@Service
public class AirportAPIService {

    @Value("${airport.api.url}")
    private String airportApiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AirportAPIService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String fetchApiResponse() {
        return restTemplate.getForObject(airportApiUrl, String.class);
    }

    public List<Airport> parseAirportData(String jsonResponse) {
        List<Airport> airports = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode airportArray = root.get("data");

            if (airportArray != null && airportArray.isArray()) {
                for (JsonNode airportNode : airportArray) {
                    JsonNode attributes = airportNode.get("attributes");

                    Airport airport = new Airport();
                    airport.setCode(attributes.path("iata").asText());
                    airport.setName(attributes.path("name").asText());
                    airport.setCity(attributes.path("city").asText());
                    airport.setCountry(attributes.path("country").asText());
                    airports.add(airport);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airports;
    }

    public void testService() {
        String response = fetchApiResponse();
        List<Airport> airports = parseAirportData(response);
        airports.forEach(System.out::println);
    }
}
