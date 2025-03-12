package com.radar.flightTrack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {

    String name;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getLowerCaseName() {
        return name != null ? name.toLowerCase() : null;
    }
}
