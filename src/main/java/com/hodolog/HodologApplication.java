package com.hodolog;

import com.hodolog.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.SpringVersion;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class HodologApplication {

	public static void main(String[] args) {
		SpringApplication.run(HodologApplication.class, args);
	}

}
