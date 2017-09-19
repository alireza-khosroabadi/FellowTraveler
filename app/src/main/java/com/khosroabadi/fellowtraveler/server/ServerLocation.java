package com.khosroabadi.fellowtraveler.server;

import android.app.Activity;

import com.khosroabadi.fellowtraveler.interfaces.IServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khosroabadi on 9/13/2017.
 */

public class ServerLocation implements IServer {

    @Override
    public JSONArray job(JSONObject object, Activity context) throws JSONException {
        return null;
    }
}
