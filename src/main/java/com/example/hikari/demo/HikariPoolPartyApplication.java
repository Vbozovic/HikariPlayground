package com.example.hikari.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HikariPoolPartyApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext run = SpringApplication.run(HikariPoolPartyApplication.class, args);
	}

}