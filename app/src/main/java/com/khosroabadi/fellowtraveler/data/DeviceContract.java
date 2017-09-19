package com.khosroabadi.fellowtraveler.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by khosroabadi on 9/12/2017.
 */

public final class DeviceContract {

    private DeviceContract(){};


    public static final String CONTENT_AUTHORITY = "com.khosroabadi.fellowtraveller";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_DEVICE = "device";


    public static final class DeviceEntry implements BaseColumns {

        public static final Uri DEVICE_CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI , PATH_DEVICE);

        public static final String TABLE_NAME = "device";
        public static final String COLUMN_NAME_IP = "ip";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LANG = "_lang";



        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DeviceEntry.TABLE_NAME + " (" +
                        DeviceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DeviceEntry.COLUMN_NAME_IP + " TEXT," +
                        DeviceEntry.COLUMN_NAME_NAME + " TEXT," +
                        DeviceEntry.COLUMN_NAME_LAT + " TEXT," +
                        DeviceEntry.COLUMN_NAME_LANG + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DeviceEntry.TABLE_NAME;

    }

}
