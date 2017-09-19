package com.khosroabadi.fellowtraveler.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by khosroabadi on 9/12/2017.
 */

public class FellowTravelerProvider extends ContentProvider {

    private  FellowTravelerDBHelper dbHelper ;

    private static final int DEVICE = 100;
    private static final int DEVICE_ID = 101;


    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        sUriMatcher.addURI(DeviceContract.CONTENT_AUTHORITY, DeviceContract.PATH_DEVICE , DEVICE);
        sUriMatcher.addURI(DeviceContract.CONTENT_AUTHORITY, DeviceContract.PATH_DEVICE+"/#" , DEVICE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new FellowTravelerDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){

            case DEVICE :
                cursor = db.query(DeviceContract.DeviceEntry.TABLE_NAME ,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case DEVICE_ID:
                selection = DeviceContract.DeviceEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(DeviceContract.DeviceEntry.TABLE_NAME ,projection,selection,selectionArgs,null,null,sortOrder);

                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match){
            case DEVICE:
                Long id = db.insert(DeviceContract.DeviceEntry.TABLE_NAME ,null, contentValues);
                if(id== -1){
                    uri = null;
                }else {
                    uri = ContentUris.withAppendedId(uri, id);
                }
                break;
            default:
                throw new IllegalArgumentException("Cannot Insert unknown URI " + uri);
        }

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int id = 0;
        switch (match){
            case DEVICE:
                    id = db.update(DeviceContract.DeviceEntry.TABLE_NAME , contentValues ,selection ,selectionArgs );
                break;
            default:
                throw new IllegalArgumentException("Cannot Insert unknown URI " + uri);
        }

        return id;
    }
}
