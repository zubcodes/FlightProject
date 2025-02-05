package com.radar.flightTrack;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FlightData {

    private List<Flight> data;  // Change from List<Map<String, Object>> to List<Flight>
    private Pagination pagination;

    // Getters and Setters
    public List<Flight> getData() {
        return data;
    }

    public void setData(List<Flight> data) {
        this.data = data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}