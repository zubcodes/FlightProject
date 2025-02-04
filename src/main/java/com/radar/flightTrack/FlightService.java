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

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    public RestClient restClient;
    String baseUrl = "https://api.aviationstack.com/v1/";
    String uri = "flights?access_key=XXX&airline_name=";

    public FlightService() {
        restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public List<Map<String, Object>> someRestCall(String airline) throws JsonProcessingException {
        logger.info("Making request to " + uri);
        String responseJson = restClient.get().uri(uri + airline).retrieve().body(String.class);
        FlightData flightData = new ObjectMapper().readValue(responseJson, FlightData.class); //mapping values of response json to flightdata class
        List<Map<String, Object>> data = flightData.getData();  //flight data object now has values read and wrote, you can do get data to retrieve that data
        logger.info(data.toString());
        return data;
    }



}