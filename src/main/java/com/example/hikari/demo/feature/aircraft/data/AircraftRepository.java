package com.example.hikari.demo.feature.aircraft.data;

import com.example.hikari.demo.feature.aircraft.AircraftDao;
import com.example.hikari.demo.feature.aircraft.PGBeanPropertyRowMapper;
import com.example.hikari.demo.feature.aircraft.data.paging.PageRequest;
import com.example.hikari.demo.feature.aircraft.data.paging.Pagination;
import com.example.hikari.demo.feature.aircraft.data.paging.PagingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AircraftRepository {


    private JdbcTemplate template;
    private RowMapper<AircraftDao> mapper;
    private PagingBean page;

    public AircraftRepository(@Qualifier("aircraftTemplate") JdbcTemplate template, PagingBean page) {
        this.template = template;
        this.page = page;
        mapper = new PGBeanPropertyRowMapper<>(AircraftDao.class);
    }


    public AircraftDao getAircraft(String code){
        String query = "select * from aircrafts_data where aircraft_code=\'%s\'";
        query = String.format(query,code);
        return template.query(query,mapper).get(0);
    }

    public List<AircraftDao> listAircraft(){
        PageRequest pageRequest = page.getPageRequest();
        String query = "select * from aircrafts_data group by aircraft_code %s order by %s %s";
        if(pageRequest.getSortColumn() == null){
            query = String.format(query,"","aircraft_code",pageRequest.getSortOrder());
        }else{
            query = String.format(query,","+pageRequest.getSortColumn(),pageRequest.getSortColumn(),pageRequest.getSortOrder());
        }
        String limit = Pagination.toLimitAndOffset(pageRequest);
        query = query + "\n" + limit;
        return template.query(query, mapper);
    }

}
