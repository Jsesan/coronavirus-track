package com.jsesan.coronavirustrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoronavirusTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoronavirusTrackApplication.class, args);
	}

}
