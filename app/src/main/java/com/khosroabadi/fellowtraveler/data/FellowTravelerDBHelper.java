package com.khosroabadi.fellowtraveler.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by khosroabadi on 9/12/2017.
 */

public class FellowTravelerDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FellowTravelerDBHelper.db";

    public FellowTravelerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DeviceContract.DeviceEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DeviceContract.DeviceEntry.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
