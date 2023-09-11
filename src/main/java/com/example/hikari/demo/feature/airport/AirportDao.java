package com.example.hikari.demo.feature.airport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AirportDao {

    private String airportCode;
    private Map<String,String> airportName;
    private Map<String,String> city;
    private String coordinates;
    private String timezone;

}
