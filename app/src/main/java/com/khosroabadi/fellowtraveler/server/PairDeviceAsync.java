package com.khosroabadi.fellowtraveler.server;

import android.content.Context;
import android.os.AsyncTask;

import com.khosroabadi.fellowtraveler.data.DeviceFacade;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khosroabadi on 9/14/2017.
 */

public class PairDeviceAsync extends AsyncTask<JSONObject, Void , Boolean> {

    private final Context context;
    private PairDeviceCallback pairDeviceCallback;

    public PairDeviceAsync(Context context , PairDeviceCallback pairDeviceCallback ){
        super();
        this.context = context;
        this.pairDeviceCallback = pairDeviceCallback;
    }

    @Override
    protected Boolean doInBackground(JSONObject... jsonObjects) {
            Boolean result;
        try {
           result = DeviceFacade.insertDevice(jsonObjects[0] , context);
        } catch (JSONException e) {
            e.printStackTrace();
            result = Boolean.FALSE;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean pairResult) {
      //  super.onPostExecute(pairResult);
        if (pairResult){
            pairDeviceCallback.success();
        }else {
            pairDeviceCallback.failure();
        }

        //super.onPostExecute(pairResult);
    }



    public interface PairDeviceCallback {
        void failure();
        void success();
    }
}
