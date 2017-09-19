package com.khosroabadi.fellowtraveler.client;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khosroabadi.fellowtraveler.R;
import com.khosroabadi.fellowtraveler.service.GPSTracker;
import com.khosroabadi.fellowtraveler.tools.ClientRequestType;
import com.khosroabadi.fellowtraveler.tools.VectorUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by khosroabadi on 9/12/2017.
 */

public class Client extends AsyncTask<Void, Void, String> {

    String dstAddress;
    int dstPort;
    String response = "";
    private Context mContext;
    String deviceName;
    private GoogleMap googleMap;
    private String requestType;

    public Client(String addr, int port, ClientRequestType requestType , Context context , String deviceName , GoogleMap googleMap) {
        dstAddress = addr;
        dstPort = port;
        this.mContext = context;
        this.deviceName = deviceName;
        this.googleMap = googleMap;
        this.requestType = requestType.getRequestType();
    }

    @Override
    protected String doInBackground(Void... arg0) {

        Socket socket = null;
        String serverResult = null;
        try {
            socket = new Socket(dstAddress, dstPort);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);


            JSONObject jsonObject = new JSONObject();

            GPSTracker gpsTracker = new GPSTracker(mContext);

            jsonObject.put("RequestType" , requestType);
            jsonObject.put("LAT" , /*String.valueOf(gpsTracker.getLatitude())*/ "35.7712981");
            jsonObject.put("LANG" , /*String.valueOf(gpsTracker.getLongitude())*/ "51.4142278");
            jsonObject.put("Name" , deviceName);
           // String text = latAsync+":"+lonAsync;

           // String sendMessage = text + "\n";
            bw.write(jsonObject.toString()+ "\n");
            bw.flush();
            System.out.println("Message sent to the server : "+jsonObject.toString());

            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            response = br.readLine();
            serverResult = br.readLine();
            System.out.println("Message received from the server : " +response);

            return br.readLine();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            response = "JSONException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return serverResult;
    }

    @Override
    protected void onPostExecute(String result) {
if (result!= null) {
    try {
        JSONArray jsonArray = new JSONArray(result);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);

            LatLng position = new LatLng(Double.valueOf(object.getString("LAT")), Double.valueOf(object.getString("LANG")));
            MarkerOptions markerOptions = new MarkerOptions().position(position).title(object.getString("Name"));
            markerOptions.icon(VectorUtils.bitmapDescriptorFromVector(mContext, R.drawable.ic_car));
            googleMap.addMarker(markerOptions).showInfoWindow();
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }

}

       // textResponse.setText(response);
        super.onPostExecute(result);
    }

}
