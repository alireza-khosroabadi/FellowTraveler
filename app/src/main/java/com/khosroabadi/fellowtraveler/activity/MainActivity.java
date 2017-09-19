package com.khosroabadi.fellowtraveler.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.khosroabadi.fellowtraveler.R;

public class MainActivity extends AppCompatActivity {

    private Button goToClient;
    private Button goToServer;
    private Button goToMap;
    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToClient = (Button) findViewById(R.id.clientBtn);
        goToServer = (Button) findViewById(R.id.serverBtn);
        goToMap = (Button) findViewById(R.id.mapBtn);
        editTextName = (EditText) findViewById(R.id.devicename);


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) && (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION))) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        goToClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText() == null || editTextName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext() , getString(R.string.device_name_is_null) , Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(MainActivity.this , ClientActivity.class);
                intent.putExtra(getString(R.string.Device_Name_Key) , editTextName.getText().toString());
                startActivity(intent);
            }
        });


        goToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextName.getText() == null || editTextName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext() , getString(R.string.device_name_is_null) , Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(MainActivity.this , ServerMapActivity.class);
                intent.putExtra(getString(R.string.Device_Name_Key) , editTextName.getText().toString());
                startActivity(intent);
            }
        });

        goToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                if (editTextName.getText() == null || editTextName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext() , getString(R.string.device_name_is_null) , Toast.LENGTH_LONG).show();
                }*/
                Intent intent = new Intent(MainActivity.this , MapsActivity.class);
                intent.putExtra(getString(R.string.Device_Name_Key) , editTextName.getText().toString());
                startActivity(intent);
            }
        });
        }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    }
