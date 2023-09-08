package com.example.hikari.demo.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties("main.datasource")
    public DataSourceProperties mainDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "main.datasource.hikari")
    public DataSource mainDataSource(DataSourceProperties mainDataSourceProperties){
        HikariDataSource dataSource = mainDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName(mainDataSourceProperties.getName());
        return dataSource;
    }

}
