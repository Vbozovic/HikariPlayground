package com.example.hikari.demo.feature.airport.api;

import com.example.hikari.demo.feature.airport.AirportDao;
import com.example.hikari.demo.feature.airport.data.AirportRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airport")
public class AirportController {

    private AirportRepository airportRepository;

    public AirportController(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @GetMapping("/{code}")
    public AirportDao airpot(@PathVariable("code") String code){
        return airportRepository.getAirport(code);
    }

    @GetMapping
    public List<AirportDao> listAirports(){
        return airportRepository.airports();
    }

}
