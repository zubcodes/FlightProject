package com.radar.flightTrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FlightCall {

    public RestClient restClient;
    private static final Logger logger = LoggerFactory.getLogger(FlightCall.class);
    String uri = "flights?access_key=XXX&airline_name=sichuan airlines";

    public FlightCall(WebClient.Builder webClientBuilder) {
        restClient = RestClient.builder().baseUrl("https://api.aviationstack.com/v1/").build();
    }

    public String someRestCall(String statesOfFlight) {
        logger.info("Making request to " + uri);
        String call = restClient.get().uri(uri ,statesOfFlight).retrieve().body(String.class);
        logger.info(call);
        return call;
    }



}