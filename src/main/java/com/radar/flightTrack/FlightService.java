package com.radar.flightTrack;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static javax.management.timer.Timer.ONE_WEEK;

@Service
public class FlightService {
    String apiKey = System.getenv("apiKey");
    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    public RestClient restClient;
    String baseUrl = "https://api.aviationstack.com/v1/";
    String uriAirlinesName = "flights?access_key="+apiKey+"&airline_name=";
    String uriIataQuery =  "flights?access_key="+apiKey+"&flight_iata=";
    private final Logger log = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    private CacheManager cacheManager;

    public FlightService(RestClient.Builder builder) {
        restClient =  builder.baseUrl(baseUrl).build();
    }

    @Cacheable(value = "flights", key = "#airline" , cacheManager = "cacheManager")
    public List<Flight> getFlights(String airline) throws JsonProcessingException {
        logger.info("Fetching from API for airline: {}", airline);
        String responseJson = restClient.get().uri(uriAirlinesName + airline).retrieve().body(String.class);
        FlightData flightData = new ObjectMapper().readValue(responseJson, FlightData.class); //mapping values of response json to flightdata class
        List<Flight> data = flightData.getData();  //flight data object now has values read and wrote, you can do get data to retrieve that data
        logger.info("Data fetched: {}", data);
        return data;
    }

    public Flight getCachedFlight(String iata, String airline) {
        Cache cache = cacheManager.getCache("flights");
        if (cache == null){
            logger.info("Cached flights does not exist");
            return null;
        }

        //log entire content
        logger.info("Cached contents: {}", cache.getNativeCache());

        Cache.ValueWrapper wrapper = cache.get(airline);
        if (wrapper == null) {
            logger.info("No cache entry found for: {}", airline);
            return null;
        }

        List<Flight> flights = (List<Flight>) wrapper.get(); // Get the cached Flig  htData object
        if (flights == null){
            logger.info("cache entry for '{}' is null.", airline);
            return null;
        }

        // Now search for the specific flight inside the FlightData object
        return flights.stream()
                .filter(flight -> flight.getFlight().getIata().equals(iata))
                .findFirst()
                .orElse(null);
    }



    @Scheduled(fixedRate = ONE_WEEK)
    @CacheEvict(value = {"flights"}, allEntries = true)
    public void clearCache() {
        log.debug("Cache '{}' cleared.", "flights");
    }

}