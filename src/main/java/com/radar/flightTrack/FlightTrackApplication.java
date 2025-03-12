package com.radar.flightTrack;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@SpringBootApplication
@Controller
@Configuration
@EnableCaching
@EnableScheduling
public class FlightTrackApplication {
	private static final Logger logger = LoggerFactory.getLogger(FlightTrackApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FlightTrackApplication.class, args);
	}
	/*@Bean
	CommandLineRunner loadData(FlightService flightCall) {
		return args -> flightCall.getFlights("");
	}*/


	public FlightService flightService;

	public FlightTrackApplication(FlightService flightService){
		this.flightService = flightService;
	}



	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/search")
	public String searchAirline(@RequestParam String airline, Model model) throws JsonProcessingException {
		List<Flight> flights = flightService.getFlights(airline);
		model.addAttribute("flights", flights);
		return "index";
	}


	@GetMapping("/flight/details")
	public String flightDetails(@RequestParam String iata, @RequestParam String airline, Model model) {
		Flight flight = flightService.getCachedFlight(iata, airline);
		if (flight == null) {
			return "error"; // Or handle the case where flight isn't found
		}
		model.addAttribute("flight", flight);
		return "flight";
	}


}
