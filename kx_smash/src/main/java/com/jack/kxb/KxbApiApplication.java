package com.jack.kxb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan  
@ServletComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties

public class KxbApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KxbApiApplication.class, args);
	}
}
