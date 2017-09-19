package com.khosroabadi.fellowtraveler.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by khosroabadi on 9/13/2017.
 */

public class DeviceFacade {

    public static Boolean insertDevice(final JSONObject jsonObject , final Context context) throws JSONException {

        if (checkDevicePaired(jsonObject, context)){
            return  updateDevice(jsonObject, context);
        }else {
            ContentValues value = new ContentValues();

            try {
                value.put(DeviceContract.DeviceEntry.COLUMN_NAME_NAME, jsonObject.getString("Name"));

                value.put(DeviceContract.DeviceEntry.COLUMN_NAME_IP, jsonObject.getString("IP"));
                value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LAT, jsonObject.getString("LAT"));
                value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LANG, jsonObject.getString("LANG"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final Uri newUri = context.getContentResolver().insert(DeviceContract.DeviceEntry.DEVICE_CONTENT_URI, value);

            Long id = ContentUris.parseId(newUri);

            if (id == null) {
                return Boolean.FALSE;
            } else {
                return Boolean.TRUE;
            }
        }

    }


    public static Boolean updateDevice(JSONObject jsonObject , final Context context) throws JSONException {

        ContentValues value = new ContentValues();

        value.put(DeviceContract.DeviceEntry.COLUMN_NAME_NAME , jsonObject.getString("Name"));
        //value.put(DeviceContract.DeviceEntry.COLUMN_NAME_IP , jsonObject.getString("IP"));
        value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LAT , jsonObject.getString("LAT"));
        value.put(DeviceContract.DeviceEntry.COLUMN_NAME_LANG , jsonObject.getString("LANG"));

        String where = DeviceContract.DeviceEntry.COLUMN_NAME_IP + "=?";
        String[] selectionArgs = {jsonObject.getString("IP")};

        final int result = context.getContentResolver().update(DeviceContract.DeviceEntry.DEVICE_CONTENT_URI , value , where , selectionArgs);

                if (result >0) {
                    return true;
                }else{
                    return false;
                }



    }


    public static boolean checkDevicePaired(JSONObject jsonObject , Context context) throws JSONException {

        String[] projection = {
                DeviceContract.DeviceEntry.COLUMN_NAME_IP
        };
        String selection = DeviceContract.DeviceEntry.COLUMN_NAME_IP + "=?";
        String[] selectionArgs = {jsonObject.getString("IP")};

        Cursor cursor = context.getContentResolver().query(DeviceContract.DeviceEntry.DEVICE_CONTENT_URI , projection,selection,selectionArgs,null);

        if (cursor.moveToFirst() || cursor.getCount() >0)
            return true;
        return false;
    }


}
