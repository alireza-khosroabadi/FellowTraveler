package com.khosroabadi.fellowtraveler.factory;

import com.khosroabadi.fellowtraveler.interfaces.IServer;
import com.khosroabadi.fellowtraveler.server.ServerLocation;
import com.khosroabadi.fellowtraveler.server.ServerPairDevice;

/**
 * Created by khosroabadi on 9/13/2017.
 */

public class ServerFactory {

    public static IServer serverFactory(String requestType){
        IServer job = null ;

        switch (requestType){
            case "PAIR":
                job = new ServerPairDevice();
                break;
            case "LOCATION":
                job = new ServerLocation();
                break;
        }

        return job;

    }
}
