package com.radar.flightTrack;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@Controller
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
	public String flightDetails(@RequestParam String iata, Model model) throws JsonProcessingException {
		List<Map<String, Object>> details = flightService.anotherRestCall(iata);
		model.addAttribute("details", details);
		return "flight";
	}




}
