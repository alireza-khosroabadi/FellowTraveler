package com.khosroabadi.fellowtraveler.tools;

/**
 * Created by khosroabadi on 9/17/2017.
 */

public enum ClientRequestType {

    PAIR("PAIR"),
    LOCATION("LOCATION");


    private String requestType;

    ClientRequestType(String requestType){
        this.requestType = requestType;
    }

    public String getRequestType(){
        return requestType;
    }

}
