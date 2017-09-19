package com.khosroabadi.fellowtraveler.server;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khosroabadi.fellowtraveler.R;
import com.khosroabadi.fellowtraveler.activity.ServerActivity;
import com.khosroabadi.fellowtraveler.activity.ServerMapActivity;
import com.khosroabadi.fellowtraveler.data.DeviceContract;
import com.khosroabadi.fellowtraveler.data.DeviceFacade;
import com.khosroabadi.fellowtraveler.factory.ServerFactory;
import com.khosroabadi.fellowtraveler.interfaces.IServer;
import com.khosroabadi.fellowtraveler.service.GPSTracker;
import com.khosroabadi.fellowtraveler.tools.ClientRequestType;
import com.khosroabadi.fellowtraveler.tools.VectorUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by khosroabadi on 9/12/2017.
 */

public class Server {

    ServerMapActivity activity;
    ServerSocket serverSocket;
    String message = "";
    private final String deviceName;
    final int socketServerPORT ;
    private final GoogleMap mMap;

    public Server(ServerMapActivity activity , String deviceName , GoogleMap googleMap) {
        this.activity = activity;
        this.deviceName = deviceName;
        this.socketServerPORT = Integer.parseInt(activity.getString(R.string.PORT));
        this.mMap = googleMap;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }

    public int getPort() {
        return socketServerPORT;
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread {

        int count = 0;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(socketServerPORT);

                while (true) {
                    final Socket socket = serverSocket.accept();
                    count++;
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    message  = br.readLine();
                    System.out.println("Message received from client is "+message);
                    final JSONObject jsonObject = new JSONObject(message);
                    jsonObject.put("IP" , socket.getInetAddress().toString());

                   // IServer serverWorker = ServerFactory.serverFactory(jsonObject.getString(activity.getString(R.string.server_request_type_key)));

                   // serverWorker.job(jsonObject , activity);
                    if (jsonObject.getString("RequestType").equals(ClientRequestType.PAIR.getRequestType())){
                        pairDevice(jsonObject,socket);
                    }
                  /* activity.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           final Dialog dialog = new Dialog(activity);

                           dialog.setContentView(R.layout.pair_dialog);
                           dialog.setTitle("ddddddd");

                           TextView deviceName = (TextView) dialog.findViewById(R.id.pair_request_dialog_device_name);
                           try {
                               deviceName.setText(jsonObject.getString("Name"));
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                           Button btnYes = (Button) dialog.findViewById(R.id.pair_dialog_btn_yes);
                           btnYes.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   pairDevice(jsonObject , dialog);

                                   try {
                                       LatLng position = new LatLng(Double.valueOf(jsonObject.getString("LAT")) , Double.valueOf(jsonObject.getString("LANG")));
                                       MarkerOptions markerOptions =new MarkerOptions().position(position).title(jsonObject.getString("Name"));
                                       markerOptions.icon(VectorUtils.bitmapDescriptorFromVector(activity , R.drawable.ic_car));
                                       mMap.addMarker(markerOptions).showInfoWindow();

                                       new Thread(new Runnable() {
                                           @Override
                                           public void run() {
                                               SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                                                       socket, count);
                                               socketServerReplyThread.run();
                                           }
                                       }).start();

                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                                   dialog.dismiss();

                               }
                           });
                           dialog.show();
                       }
                   });*/




                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private void pairDevice(final JSONObject jsonObject, final Socket socket){
/*        PairDeviceAsync pairDeviceAsync = new PairDeviceAsync(activity, new PairDeviceAsync.PairDeviceCallback() {
            @Override
            public void failure() {

            }

            @Override
            public void success() {
                try {
                    LatLng position = new LatLng(Double.valueOf(jsonObject.getString("LAT")) , Double.valueOf(jsonObject.getString("LANG")));
                    MarkerOptions markerOptions =new MarkerOptions().position(position).title(jsonObject.getString("NAME"));
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_car);
                    markerOptions.icon(icon);
                    mMap.addMarker(markerOptions).showInfoWindow();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        pairDeviceAsync.doInBackground(jsonObject);*/

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {


                final Dialog dialog = new Dialog(activity);

                dialog.setContentView(R.layout.pair_dialog);
                dialog.setTitle("ddddddd");

                TextView deviceName = (TextView) dialog.findViewById(R.id.pair_request_dialog_device_name);

                deviceName.setText(jsonObject.getString("Name"));


                Button btnYes = (Button) dialog.findViewById(R.id.pair_dialog_btn_yes);
                    Button btnNo = (Button) dialog.findViewById(R.id.pair_dialog_btn_no);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        try {
                            final Boolean result = DeviceFacade.insertDevice(jsonObject , activity);
                                jsonObject.put("Status" , result);
                            if (result) {
                                LatLng position = new LatLng(Double.valueOf(jsonObject.getString("LAT")), Double.valueOf(jsonObject.getString("LANG")));
                                MarkerOptions markerOptions = new MarkerOptions().position(position).title(jsonObject.getString("Name"));
                                markerOptions.icon(VectorUtils.bitmapDescriptorFromVector(activity, R.drawable.ic_car));
                                mMap.addMarker(markerOptions).showInfoWindow();
                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                                            socket , result);
                                    socketServerReplyThread.run();
                                }
                            }).start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    }
                });

                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(
                                    socket , false );
                            socketServerReplyThread.run();
                            dialog.dismiss();
                        }
                    });
                dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }



    private class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;
        private Boolean status;

        SocketServerReplyThread(Socket socket , Boolean status) {
            hostThreadSocket = socket;
            this.status = status;
        }

        @Override
        public void run() {
            OutputStream outputStream;
           // String msgReply = "Hello from IServer, you are #" + cnt;


            Looper.prepare();
            try {
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                JSONObject clinetJson = new JSONObject();
                if (status) {
                    JSONObject statusJson = new JSONObject();
                    statusJson.put("Status" , "success");
                    JSONArray locationList = getPairedDevice(hostThreadSocket.getInetAddress());
                    JSONObject serverLocation = new JSONObject();
                    GPSTracker gpsTracker = new GPSTracker(activity);
                    serverLocation.put(DeviceContract.DeviceEntry.COLUMN_NAME_NAME, deviceName);
                    serverLocation.put(DeviceContract.DeviceEntry.COLUMN_NAME_LANG, String.valueOf(gpsTracker.getLongitude()));
                    serverLocation.put(DeviceContract.DeviceEntry.COLUMN_NAME_LAT, String.valueOf(gpsTracker.getLatitude()));
                   // locationList.put(serverLocation);
                    clinetJson.put("status_json" , statusJson);
                    clinetJson.put("location_list",locationList);
                    clinetJson.put("server_location" , serverLocation);
                }else {
                    JSONObject statusJson = new JSONObject();
                    statusJson.put("Status" , "failed");
                    clinetJson.put("status_json", statusJson);
                }
                String resultStr = clinetJson.toString()+"\n";
                printStream.print(resultStr);
                printStream.close();

               Looper.loop();
                Looper.myLooper().quit();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message += "Something wrong! " + e.toString() + "\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }

            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                  //  activity.msg.setText(message);
                }
            });
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

  /*  public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip = "IServer running at : "
                                + inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }*/

}
