package com.radar.flightTrack;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@AutoConfigureCache(cacheProvider = CacheType.SIMPLE) //need this because even if you autowire your cachemanager it doesn't take your cacheconfig
@RestClientTest(FlightService.class)
class CacheTests {
    String apiKey = System.getenv("apiKey");

    @Autowired
    FlightService flightService;

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp(){
        Assertions.assertNotNull(flightService);
        Assertions.assertNotNull(cacheManager);
    }

    @Test
    void shouldCacheOnService() throws IOException {

        // Arrange
        String mockJson = new String(Files.readAllBytes(Paths.get("src/test/sample-response.json")));

        // Define behavior for mock
        mockServer.expect(requestTo("https://api.aviationstack.com/v1/flights?access_key="+apiKey+"&airline_name=British%20Airways"))
                .andRespond(withSuccess(mockJson, MediaType.APPLICATION_JSON));
        //Act
        List<Flight> flights = flightService.getFlights("British Airways");
        //Assert
        Object cachedValue = cacheManager.getCache("flights").get("British Airways").get();
        Assertions.assertNotNull(cachedValue, "cache should not be null");
        System.out.println("Cached value" + cachedValue);

    }
}
