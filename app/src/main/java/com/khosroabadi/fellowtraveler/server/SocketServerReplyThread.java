package com.khosroabadi.fellowtraveler.server;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.khosroabadi.fellowtraveler.data.DeviceContract;
import com.khosroabadi.fellowtraveler.service.GPSTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by khosroabadi on 9/16/2017.
 */

public class SocketServerReplyThread extends Thread {


    private Socket hostThreadSocket;
    private AppCompatActivity activity;
    private String deviceName;

    public SocketServerReplyThread(Socket socket, AppCompatActivity appCompatActivity , String deviceName) {
        this.hostThreadSocket = socket;
        this.activity = appCompatActivity;
        this.deviceName = deviceName;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        // String msgReply = "Hello from IServer, you are #" + cnt;



        try {
            outputStream = hostThreadSocket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            JSONArray locationList = getPairedDevice(hostThreadSocket.getInetAddress());
            JSONObject serverLocation = new JSONObject();
            GPSTracker gpsTracker = new GPSTracker(activity);
            serverLocation.put(DeviceContract.DeviceEntry.COLUMN_NAME_NAME , deviceName);
            serverLocation.put(DeviceContract.DeviceEntry.COLUMN_NAME_LANG , String.valueOf(gpsTracker.getLongitude()) );
            serverLocation.put(DeviceContract.DeviceEntry.COLUMN_NAME_LAT , String.valueOf(gpsTracker.getLatitude()));
            locationList.put(serverLocation);
            printStream.print(locationList.toString());
            printStream.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           // message += "Something wrong! " + e.toString() + "\n";
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private JSONArray getPairedDevice(InetAddress inetAddress) throws JSONException {
        JSONArray resultList = new JSONArray();
        String[] projection = {
                DeviceContract.DeviceEntry.COLUMN_NAME_NAME,
                DeviceContract.DeviceEntry.COLUMN_NAME_LANG,
                DeviceContract.DeviceEntry.COLUMN_NAME_LAT

        };

        String selection = DeviceContract.DeviceEntry.COLUMN_NAME_IP + "!=?";
        String[] selectionArgs = {inetAddress.toString()};

        Cursor cursor = activity.getContentResolver().query(DeviceContract.DeviceEntry.DEVICE_CONTENT_URI , projection,selection,selectionArgs,null);

        while (cursor.moveToNext()){
            JSONObject object = new JSONObject();
            object.put(DeviceContract.DeviceEntry.COLUMN_NAME_LAT , cursor.getString(cursor.getColumnIndexOrThrow(DeviceContract.DeviceEntry.COLUMN_NAME_LAT)));
            object.put(DeviceContract.DeviceEntry.COLUMN_NAME_LANG , cursor.getString(cursor.getColumnIndexOrThrow(DeviceContract.DeviceEntry.COLUMN_NAME_LANG)));
            object.put(DeviceContract.DeviceEntry.COLUMN_NAME_NAME , cursor.getString(cursor.getColumnIndexOrThrow(DeviceContract.DeviceEntry.COLUMN_NAME_NAME)));
            resultList.put(object);
        }
        return resultList;
    }


}
