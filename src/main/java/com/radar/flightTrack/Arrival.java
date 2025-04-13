package com.radar.flightTrack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Arrival {

    String airport;
    String timezone;
    String iata;
    String terminal;
    String gate;
    String scheduled;
    String icao;

    public String getAirport(){
        return airport;
    }
    public void setAirport(String airport){
        this.airport=airport;
    }

    public String getTimeZone(){
        return timezone;
    }
    public void setTimezone(String timezone){
        this.timezone=timezone;
    }

    public String getIata(){
        return iata;
    }
    public void setIata(String iata){
        this.iata = iata;
    }

    public String getTerminal(){
        return terminal;
    }

    public void setTerminal(String terminal){
        this.terminal=terminal;
    }

    public String getGate(){
        return gate;
    }

    public void setGate(String gate){
        this.gate=gate;
    }

    public String getScheduled(){
        return scheduled;
    }

    public void setScheduled(String scheduled){
        this.scheduled=scheduled;
    }

    public String getIcao(){
        return icao;
    }
    public void setIcao(String icao){
        this.icao = icao;
    }


}
