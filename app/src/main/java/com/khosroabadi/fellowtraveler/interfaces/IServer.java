package com.khosroabadi.fellowtraveler.interfaces;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khosroabadi on 9/13/2017.
 */

public interface IServer {

    public JSONArray job(JSONObject object , Activity context) throws JSONException;
}
