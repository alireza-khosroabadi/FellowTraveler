package com.khosroabadi.fellowtraveler.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.khosroabadi.fellowtraveler.R;
import com.khosroabadi.fellowtraveler.server.Server;
import com.khosroabadi.fellowtraveler.service.GPSTracker;

public class ServerActivity extends AppCompatActivity {

    Server server;
    public TextView infoip, msg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
       // server = new Server(this ,  getIntent().getStringExtra(getString(R.string.Device_Name_Key)));
        infoip.setText(GPSTracker.getIpAddress()+":"+server.getPort());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.onDestroy();
    }
}
