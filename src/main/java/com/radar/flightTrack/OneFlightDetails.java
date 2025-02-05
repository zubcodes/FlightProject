package com.radar.flightTrack;

import java.util.List;
import java.util.Map;

public class OneFlightDetails {
    public List<Map<String, Object>> data;
    private Map<String, Object>pagination;

    public List<Map<String, Object>> getData (){
        return data;
    };

    public void setData(List<Map<String,Object>> data){
        this.data = data;
    }

    public Map<String, Object> getPagination() {
        return pagination;
    }

    public void setPagination(Map<String, Object> pagination) {
        this.pagination = pagination;
    }
}
