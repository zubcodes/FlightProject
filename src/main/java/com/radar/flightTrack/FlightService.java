package com.radar.flightTrack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class FlightService {
    String apiKey = System.getenv("apiKey");
    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    public RestClient restClient;
    String baseUrl = "https://api.aviationstack.com/v1/";
    String uriAirlinesName = "flights?access_key="+apiKey+"&airline_name=";
    String uriIataQuery =  "flights?access_key="+apiKey+"&flight_iata=";

    public FlightService(RestClient.Builder builder) {
        restClient =  builder.baseUrl(baseUrl).build();
    }

    public List<Flight> getFlights(String airline) throws JsonProcessingException {
        logger.info("Making request to " + uriAirlinesName);
        String responseJson = restClient.get().uri(uriAirlinesName + airline).retrieve().body(String.class);
        FlightData flightData = new ObjectMapper().readValue(responseJson, FlightData.class); //mapping values of response json to flightdata class
        List<Flight> data = flightData.getData();  //flight data object now has values read and wrote, you can do get data to retrieve that data
        logger.info(data.toString());
        return data;
    }

    public List<Map<String, Object>> anotherRestCall(String iata) throws JsonProcessingException {
        String responseJson = restClient.get().uri(uriIataQuery + iata).retrieve().body(String.class);
        OneFlightDetails thatFlightsData = new ObjectMapper().readValue(responseJson, OneFlightDetails.class);
        List<Map<String, Object>> thatData = thatFlightsData.getData();
        return thatData;
    }



}