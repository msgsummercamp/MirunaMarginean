package com.example.airport_api.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Airport {
    private String code;
    private String name;
    private String city;
    private String country;
}
