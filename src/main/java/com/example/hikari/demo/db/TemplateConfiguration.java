package com.example.hikari.demo.db;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class TemplateConfiguration {


    @Bean
    public JdbcTemplate aircraftTemplate(DataSource mainDataSource){
        JdbcTemplate template = new JdbcTemplate(mainDataSource);
        return template;
    }

}
