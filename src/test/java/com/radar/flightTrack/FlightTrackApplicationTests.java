package com.radar.flightTrack;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(FlightService.class)
class FlightTrackApplicationTests {
	String apiKey = System.getenv("apiKey");

	@Autowired
	private FlightService flightService;

	@Autowired
	private MockRestServiceServer mockServer;


    @Test
	void testGetFlights() throws JsonProcessingException {
		// Arrange
		String mockJson = """
            { "data" : [ { "flight_date": "2025-02-04",
			"flight_status": "scheduled",
			"departure": {
		        "airport": "Dublin International",
			    "timezone": "Europe/Dublin",
			    "iata": "DUB",
			    "iata": "EIDW",
			    "terminal": "2",
			    "gate": "409",
			    "delay": null,
			    "scheduled": "2025-02-04T07:40:00+00:00",
			    "estimated": "2025-02-04T07:40:00+00:00",
			    "actual": null,
			    "estimated_runway": null,
			    "actual_runway": null
				},
			"arrival": {
			    "airport": "Heathrow",
			    "timezone": "Europe/London",
			    "iata": "LHR",
			    "iata": "EGLL",
			    "terminal": "2",
				"gate": null,
				"baggage": null,
				"delay": null,
				"scheduled": "2025-02-04T09:05:00+00:00",
				"estimated": "2025-02-04T09:05:00+00:00",
				"actual": null,
				"estimated_runway": null,
				"actual_runway": null
				                                     },
                 "airline": {
                     "name": "British Airways",
                     "iata": "BA",
                     "iata": "BAW"
                 },
                 "flight": {
                     "number": "5954",
                     "iata": "BA5954",
				     "icao": "BAW5954",
				     "codeshared": {
				         "airline_name": "aer lingus",
				         "airline_iata": "ei",
				         "airline_icao": "ein",
				         "flight_number": "154",
				         "flight_iata": "ei154",
				         "flight_icao": "ein154"
				     }
				 },
"aircraft": null,
"live": null
}
]}""";

	// Define behavior for mock
	mockServer.expect(requestTo("https://api.aviationstack.com/v1/flights?access_key="+apiKey+"&airline_name=British%20Airways"))
			.andRespond(withSuccess(mockJson, MediaType.APPLICATION_JSON));


	List<Flight> flights = flightService.getFlights("British Airways");

	// Assertions

	assertEquals("BA5954", flights.get(0).getFlight().getIata());
}


}


