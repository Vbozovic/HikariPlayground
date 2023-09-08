package com.example.hikari.demo.feature.aircraft.api;

import com.example.hikari.demo.feature.aircraft.AircraftDao;
import com.example.hikari.demo.feature.aircraft.data.AircraftRepository;
import com.example.hikari.demo.feature.aircraft.data.paging.PagingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/aircraft")
public class AircraftController {

    private AircraftRepository repository;
    private PagingBean page;

    public AircraftController(AircraftRepository repository, PagingBean page) {
        this.repository = repository;
        this.page = page;
    }

    @GetMapping("/{code}")
    public AircraftDao getAircraft(@PathVariable("code") String code){
        return repository.getAircraft(code);
    }

    @GetMapping("/all")
    public List<AircraftDao> allAircraft(){
        return repository.listAircraft();
    }

}
