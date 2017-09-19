package com.khosroabadi.fellowtraveler.server;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.khosroabadi.fellowtraveler.R;
import com.khosroabadi.fellowtraveler.data.DeviceContract;
import com.khosroabadi.fellowtraveler.interfaces.IServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;

/**
 * Created by khosroabadi on 9/13/2017.
 */

public class ServerPairDevice implements IServer {

    @Override
    public JSONArray job(JSONObject object , Activity context) throws JSONException {

        if (newRequestedDevice(object , context)){

            insertDevice(object , context);

        }else {
            updateDevice(object , context);
        }

        return null;
    }



    private boolean newRequestedDevice(JSONObject jsonObject , Activity context) throws JSONException {

        String[] projection = {
                DeviceContract.DeviceEntry.COLUMN_NAME_IP
        };
        String selection = DeviceContract.DeviceEntry.COLUMN_NAME_IP + "=?";
        String[] selectionArgs = {jsonObject.getString("IP")};

        Cursor cursor = context.getContentResolver().query(DeviceContract.DeviceEntry.DEVICE_CONTENT_URI , projection,selection,selectionArgs,null);

        if (!(cursor.moveToFirst()) || cursor.getCount() ==0)
            return true;
        return false;
    }

    private void insertDevice(final JSONObject jsonObject , final Activity context) throws JSONException {

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

       final Dialog dialog = new Dialog(context);

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
                ContentValues value = new ContentValues();

                try {
                    value.put(DeviceContract.DeviceEntry.COLUMN_NAME_NAME , jsonObject.getString("Name"));

                value.put(DeviceContract.DeviceEntry.COLUMN_NAME_IP , jsonObject.getString("IP"));
                value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LAT , jsonObject.getString("LAT"));
                value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LANG , jsonObject.getString("LANG"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final Uri newUri = context.getContentResolver().insert(DeviceContract.DeviceEntry.DEVICE_CONTENT_URI , value);



                        if (newUri == null) {
                            Toast.makeText(context, "Faild", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

            }
        });
                dialog.show();
            }
        });


    }


    private void updateDevice(JSONObject jsonObject , final Activity context) throws JSONException {

        ContentValues value = new ContentValues();

        value.put(DeviceContract.DeviceEntry.COLUMN_NAME_NAME , jsonObject.getString("Name"));
        //value.put(DeviceContract.DeviceEntry.COLUMN_NAME_IP , jsonObject.getString("IP"));
        value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LAT , jsonObject.getString("LAT"));
        value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LANG , jsonObject.getString("LANG"));

        String where = DeviceContract.DeviceEntry.COLUMN_NAME_IP + "=?";
        String[] selectionArgs = {jsonObject.getString("IP")};

        final int result = context.getContentResolver().update(DeviceContract.DeviceEntry.DEVICE_CONTENT_URI , value , where , selectionArgs);

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result >0) {
                    Toast.makeText(context, "Faild", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
