package com.radar.flightTrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class FlightTrackApplication {
	private static final Logger logger = LoggerFactory.getLogger(FlightTrackApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FlightTrackApplication.class, args);
	}
	@Bean
	CommandLineRunner loadData(FlightCall flightCall) {
		return args -> flightCall.someRestCall("");
	}

	@GetMapping("/movies")
	public String movieForm(Model model){
		model.addAttribute("movie", new FlightCall());
		return "movies";
	}

}
