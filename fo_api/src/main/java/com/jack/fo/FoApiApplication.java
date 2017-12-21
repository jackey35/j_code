package com.jack.fo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan  
@EnableAutoConfiguration
@EnableConfigurationProperties
public class FoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoApiApplication.class, args);
	}
}
