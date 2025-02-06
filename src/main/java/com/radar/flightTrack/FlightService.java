package com.radar.flightTrack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

import static javax.management.timer.Timer.ONE_DAY;

@Service
public class FlightService {
    String apiKey = System.getenv("apiKey");
    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    public RestClient restClient;
    String baseUrl = "https://api.aviationstack.com/v1/";
    String uriAirlinesName = "flights?access_key="+apiKey+"&airline_name=";
    String uriIataQuery =  "flights?access_key="+apiKey+"&flight_iata=";
    private final Logger log = LoggerFactory.getLogger(FlightService.class);

    public FlightService(RestClient.Builder builder) {
        restClient =  builder.baseUrl(baseUrl).build();
    }

    @Cacheable(value = "flights", key = "#airline" , cacheManager = "cacheManager")
    public List<Flight> getFlights(String airline) throws JsonProcessingException {
        logger.info("Fetching from API for airline: " + uriAirlinesName);
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

    @Scheduled(fixedRate = ONE_DAY)
    @CacheEvict(value = {"flights"}, allEntries = true)
    public void clearCache() {
        log.debug("Cache '{}' cleared.", "flights");
    }


}