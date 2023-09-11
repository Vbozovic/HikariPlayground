package com.example.hikari.demo.db;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class TemplateConfiguration implements ApplicationContextAware {

    private DataSource mainDataSource;

    @Value("${database.metadata.introspect}")
    private List<String> tables;

    public TemplateConfiguration(DataSource mainDataSource) {
        this.mainDataSource = mainDataSource;
    }


    private JdbcTemplate createTemplate(DataSource dataSource){
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        tables.forEach((tName)->{
            var template = new JdbcTemplate(mainDataSource);
            ((ConfigurableApplicationContext)applicationContext).getBeanFactory().registerSingleton(tName,template);
        });
    }
}
