package com.example.hikari.demo.feature.airport.data;

import com.example.hikari.demo.db.PGBeanPropertyRowMapper;
import com.example.hikari.demo.feature.airport.AirportDao;
import com.example.hikari.demo.paging.Pagination;
import com.example.hikari.demo.paging.PagingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AirportRepository {

    private JdbcTemplate template;
    private PagingBean page;
    private RowMapper<AirportDao> mapper;

    public AirportRepository(@Qualifier("airports_data") JdbcTemplate template, PagingBean page) {
        this.template = template;
        this.page = page;
        mapper = new PGBeanPropertyRowMapper<>(AirportDao.class);
    }

    public AirportDao getAirport(String code){
        String query = "select * from airports_data where airport_code=\'%s\'";
        query = String.format(query,code);
        return template.query(query,mapper).get(0);
    }

    public List<AirportDao> airports(){
        String select = "select * from airports_data\n";
        select = select + Pagination.toGrouping("airport_code",page.getPageRequest());
        select = select + Pagination.toOrdering(page.getPageRequest());
        select = select + Pagination.toLimitAndOffset(page.getPageRequest());

        return template.query(select,mapper);
    }

}
